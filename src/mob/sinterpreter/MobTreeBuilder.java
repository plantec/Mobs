package mob.sinterpreter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mob.model.MobAssign;
import mob.model.MobBinaryMessageSend;
import mob.model.MobEntity;
import mob.model.MobKeywordMessageSend;
import mob.model.MobMessageSend;
import mob.model.MobObject;
import mob.model.MobObjectDef;
import mob.model.MobUnaryMessageSend;
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

	@Override
	public void visitNode(SNode node) {
		ArrayList<MobEntity> children = new ArrayList<>();
		node.children().forEach(s -> {
			s.accept(this);
			children.add(stk.pop());
		});
		if (children.size() == 3) {
			if (children.get(1) instanceof MobSymbol) {
				MobSymbol symb = (MobSymbol) children.get(1);
				if (symb.is(":=")) {
					MobAssign assign = new MobAssign();
					assign.setLeft((MobObject) children.get(0));
					assign.setRight(children.get(2));
					stk.push(assign);
					return;
				}
			}
		}
		if (children.size() >= 2 && children.get(0) instanceof MobSymbol && children.get(1) instanceof MobSymbol) {
			MobSymbol symb0 = (MobSymbol) children.get(0);
			MobSymbol symb1 = (MobSymbol) children.get(1);
			if (symb0.is("decl")) {
				MobVarDecl decl = new MobVarDecl();
				decl.setName(symb1.rawValue());
				if (children.size() == 4 && children.get(2) instanceof MobSymbol) {
					MobSymbol symb2 = (MobSymbol) children.get(2);
					if (symb2.is(":=")) {
						decl.setInitialValue((MobObject) children.get(3));
					}
				}
				stk.push(decl);
				return;
			}
		}
		if (children.size() >= 2) {
		MobMessageSend send;
		if (children.size() == 2) {
			MobUnaryMessageSend unary = new MobUnaryMessageSend();
			send = unary;
			unary.setKeyword(((MobSymbol) children.get(1)).rawValue());
		} else if (children.size() == 3) {
			MobSymbol s = (MobSymbol) children.get(1);
			String op = s.rawValue();
			if (op.charAt(op.length() - 1) == ':') {
				MobKeywordMessageSend keyword = new MobKeywordMessageSend();
				send = keyword;
				keyword.keywords().add(((MobSymbol) children.get(1)).rawValue());
				keyword.args().add(children.get(2));
			} else {
				MobBinaryMessageSend binary = new MobBinaryMessageSend();
				send = binary;
				binary.setOperator(((MobSymbol) children.get(1)).rawValue());
				binary.setArgument(children.get(2));
			}
		} else if (children.size() > 3) {
			MobKeywordMessageSend keyword = new MobKeywordMessageSend();
			send = keyword;
			for (int i = 0; i < children.size(); i += 2) {
				keyword.keywords().add(((MobSymbol) children.get(i)).rawValue());
				keyword.args().add(children.get(i + 1));
			}
		}
		send.setQuote(node.quote());
		send.setReceiver(children.get(0));
		stk.push(send);
		} else 
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
		exp.setQuote(node.quote());
		this.stk.push(exp);
	}

}
