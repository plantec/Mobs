package mob.sinterpreter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mob.model.MobAssign;
import mob.model.MobBinaryMessage;
import mob.model.MobEntity;
import mob.model.MobKeywordMessage;
import mob.model.MobObject;
import mob.model.MobReturn;
import mob.model.MobSequence;
import mob.model.MobUnaryMessage;
import mob.model.MobVarDecl;
import mob.model.primitives.MobSymbol;
import stree.parser.SNode;
import stree.parser.SParser;
import stree.parser.SVisitor;

public class MobTreeBuilder implements SVisitor {
	private ArrayDeque<MobEntity> stk;
	MobEnvironment env;

	public MobTreeBuilder(MobEnvironment env) {
		this.env = env;
		this.stk = new ArrayDeque<>();
	}

	protected void reset() {
		this.stk.clear();
	}

	public List<MobEntity> result() {
		List<MobEntity> result = new ArrayList<>();
		this.stk.forEach(s -> result.add(s));
		Collections.reverse(result);
		return result;
	}

	public List<MobEntity> run(List<SNode> sexps) {
		this.reset();
		for (SNode n : sexps) {
			n.accept(this);
		}
		return this.result();
	}

	public List<MobEntity> run(String input) {
		SParser parser = new SParser();
		List<SNode> n = null;
		try {
			n = parser.parse(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.run(n);
	}
	
	MobEntity quoted(MobEntity e, int q) {
		if (q == 0)
			return e;
		if (e instanceof MobSequence) {
			MobSequence seq = (MobSequence) e;
			System.out.println(seq.children());
		}
		return quoted(env.newUnit(e), q - 1);
	}

	private Boolean foundReturn(ArrayList<MobEntity> children, int quote) {
		if (children.size() != 2)
			return false;
		if (!(children.get(0) instanceof MobSymbol))
			return false;
		if (!(((MobSymbol) children.get(0)).is("^")))
			return false;
		MobReturn ret = new MobReturn();
		ret.setReturned(children.get(1));
		stk.push(quoted(ret, quote));
		return true;
	}

	private Boolean foundAssign(ArrayList<MobEntity> children, int quote) {
		if (children.size() != 3)
			return false;
		if (!(children.get(1) instanceof MobSymbol))
			return false;
		if (!(((MobSymbol) children.get(1)).is(":=")))
			return false;
		MobAssign assign = new MobAssign();
		assign.setLeft((MobObject) children.get(0));
		assign.setRight(children.get(2));
		stk.push(quoted(assign, quote));
		return true;
	}

	private Boolean foundDecl(ArrayList<MobEntity> children, int quote) {
		if (children.size() < 2)
			return false;
		if (!(children.get(0) instanceof MobSymbol))
			return false;
		if (!(children.get(1) instanceof MobSymbol))
			return false;
		MobSymbol symb0 = (MobSymbol) children.get(0);
		MobSymbol symb1 = (MobSymbol) children.get(1);
		if (!symb0.is("decl"))
			return false;
		MobVarDecl decl = new MobVarDecl();
		decl.setName(symb1.rawValue());
		if (children.size() == 4 && children.get(2) instanceof MobSymbol) {
			MobSymbol symb2 = (MobSymbol) children.get(2);
			if (symb2.is(":=")) {
				decl.setInitialValue(children.get(3));
			}
		}
		stk.push(quoted(decl, quote));
		return true;
	}
	
	private Boolean foundMessageSend(ArrayList<MobEntity> children, int quote) {
		if (children.size() < 2)
			return false;
		if (!(children.get(1) instanceof MobSymbol))
			return false;
		if (children.size() == 2) {
			MobUnaryMessage unary = new MobUnaryMessage();
			unary.setKeyword(((MobSymbol) children.get(1)).rawValue());
			unary.setReceiver(children.get(0));
			stk.push(quoted(unary, quote));
			return true;
		}
		if (children.size() == 3) {
			MobSymbol s = (MobSymbol) children.get(1);
			String op = s.rawValue();
			if (op.charAt(op.length() - 1) == ':') {
				MobKeywordMessage keyword = new MobKeywordMessage();
				keyword.add(((MobSymbol) children.get(1)).rawValue(), children.get(2));
				keyword.setReceiver(children.get(0));
				stk.push(quoted(keyword, quote));
				return true;
			}
			MobBinaryMessage binary = new MobBinaryMessage();
			binary.setOperator(((MobSymbol) children.get(1)).rawValue());
			binary.setArgument(children.get(2));
			binary.setReceiver(children.get(0));
			stk.push(quoted(binary, quote));
			return true;
		}
		if (children.size() > 3) {
			for (int i = 1; i < children.size(); i += 2) {
				String kw = ((MobSymbol) children.get(i)).rawValue();
				if (kw.charAt(kw.length() - 1) != ':') return false;
			}
			MobKeywordMessage keyword = new MobKeywordMessage();
			for (int i = 1; i < children.size(); i += 2) {
				keyword.add(((MobSymbol) children.get(i)).rawValue(), children.get(i + 1));
			}
			keyword.setReceiver(children.get(0));
			stk.push(quoted(keyword, quote));
			return true;
		}
		return false;
	}

	@Override
	public void visitNode(SNode node) {
		ArrayList<MobEntity> children = new ArrayList<>();
		node.children().forEach(s -> {
			s.accept(this);
			children.add(stk.pop());
		});
		if (foundReturn(children, node.quote()))
			return;
		if (foundAssign(children, node.quote()))
			return;
		if (foundDecl(children, node.quote()))
			return;
		if (foundMessageSend(children, node.quote()))
			return;
		MobSequence sequence = this.env.newSequence(children);
		stk.push(quoted(sequence, node.quote()));
	}

	@Override
	public void visitLeaf(SNode node) {
		String contents = node.parsedString();
		MobObject exp;
		if (contents.equals("true"))
			exp = this.env.newTrue();
		else if (contents.equals("false"))
			exp = this.env.newFalse();
		else if (contents.equals("nil"))
			exp = this.env.newNil();
		else
			switch (contents.charAt(0)) {
			case '"':
				exp = this.env.newString(contents.substring(1, contents.length() - 1));
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '+':
			case '-':
				try {
					Integer i = Integer.parseInt(contents);
					exp = this.env.newInteger(i);
				} catch (NumberFormatException ex) {
					try {
						Float f = Float.parseFloat(contents);
						exp = this.env.newFloat(f);
					} catch (NumberFormatException ex2) {
						exp = this.env.newSymbol(contents);
					}
				}
				break;
			default:
				exp = this.env.newSymbol(contents);
			}
		this.stk.push(quoted(exp, node.quote()));
	}
}
