package mob.sinterpreter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import mob.ast.MobAssign;
import mob.ast.MobAstElement;
import mob.ast.MobBinaryMessage;
import mob.ast.MobKeywordMessage;
import mob.ast.MobQuoted;
import mob.ast.MobReturn;
import mob.ast.MobUnaryMessage;
import mob.ast.MobVarDecl;
import mob.model.MobObject;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobUnit;
import stree.parser.SNode;
import stree.parser.SParser;
import stree.parser.SVisitor;

public class MobTreeBuilder implements SVisitor {
	private Deque<MobAstElement> stk;
	MobEnvironment env;

	public MobTreeBuilder(MobEnvironment env) {
		this.env = env;
		this.stk = new ArrayDeque<>();
	}

	protected void reset() {
		this.stk.clear();
	}

	public List<MobAstElement> result() {
		List<MobAstElement> result = new ArrayList<>();
		this.stk.forEach(s -> result.add(s));
		Collections.reverse(result);
		return result;
	}

	public List<MobAstElement> run(List<SNode> sexps) {
		this.reset();
		for (SNode n : sexps) {
			n.accept(this);
		}
		return this.result();
	}

	public List<MobAstElement> run(String input) {
		SParser parser = new SParser();
		List<SNode> n = null;
		try {
			n = parser.parse(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.run(n);
	}

	MobAstElement quoted(MobAstElement e, int q) {
		if (q == 0)
			return e;
		return quoted(new MobQuoted(e), q - 1);
	}

	private Boolean foundParameters(SNode node, ArrayList<MobAstElement> children) {
		if (node.openTag() != '{')
			return false;
		MobSequence parameters = this.env.newSequence(children);
		stk.push(parameters);
		return true;
	}

	private int parametersEnd(ArrayList<MobAstElement> children) {
		for (int pos = 0; pos < children.size(); pos++) {
			MobAstElement c = children.get(pos);
			if (c.is("|")) {
				return pos;
			}
		}
		return -1;
	}
	
	private Boolean foundUnit(SNode node, ArrayList<MobAstElement> children) {
		List<MobAstElement> code = new ArrayList<>();
		if (node.openTag() != '[')
			return false;
		MobUnit unit = env.newUnit();
		if (children.size() == 0) {
			stk.push(quoted(unit, node.quote()));
			return true;
		}
		int start = 0;
		int mark = this.parametersEnd(children);
		if (mark > -1) {
			start = mark + 1;
			for (int i = 0; i < mark; i++) {
				MobSymbol s = (MobSymbol) children.get(i);
				unit.addParameter(s.rawValue());
			}	
		}
		ArrayList<MobAstElement> subs = new ArrayList<>();
		for (int i = start; i < children.size(); i++)
			subs.add(children.get(i));

		if (foundReturn(subs, 0))
			code.add(stk.pop());
		else if (foundAssign(subs, 0))
			code.add(stk.pop());
		else if (foundMessageSend(subs, 0))
			code.add(stk.pop());
		else {
			for (MobAstElement e : subs)
				code.add(e);
		}
		if (code.size() > 1) {
			unit.setCode(this.env.newSequence(code));
		} else if (code.size() > 0){
			unit.setCode(code.get(0));
		}
		stk.push(quoted(unit, node.quote()));
		return true;
	}

	private Boolean foundReturn(ArrayList<MobAstElement> children, int quote) {
		if (children.size() < 1)
			return false;
		if (!(children.get(0) instanceof MobSymbol))
			return false;
		if (!(((MobSymbol) children.get(0)).is("^")))
			return false;

		ArrayList<MobAstElement> subs = new ArrayList<>();
		for (int i = 1; i < children.size(); i++)
			subs.add(children.get(i));

		MobReturn ret = new MobReturn();
		if (foundMessageSend(subs, 0))
			ret.setReturned(stk.pop());
		else if (children.size() > 1)
			ret.setReturned(children.get(1));
		stk.push(quoted(ret, quote));
		return true;
	}

	private Boolean foundAssign(ArrayList<MobAstElement> children, int quote) {
		if (children.size() < 3)
			return false;
		if (!(children.get(1) instanceof MobSymbol))
			return false;
		if (!(((MobSymbol) children.get(1)).is(":=")))
			return false;
		MobAssign assign = new MobAssign();
		assign.setLeft((MobObject) children.get(0));

		ArrayList<MobAstElement> subs = new ArrayList<>();
		for (int i = 2; i < children.size(); i++)
			subs.add(children.get(i));
		if (foundMessageSend(subs, 0))
			assign.setRight(stk.pop());
		else if (foundAssign(subs, 0))
			assign.setRight(stk.pop());
		else
			assign.setRight(children.get(2));
		stk.push(quoted(assign, quote));
		return true;
	}

	private Boolean foundDecl(ArrayList<MobAstElement> children, int quote) {
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
		if (children.size() >= 4 && children.get(2) instanceof MobSymbol) {
			MobSymbol symb2 = (MobSymbol) children.get(2);
			if (symb2.is(":=")) {
				ArrayList<MobAstElement> subs = new ArrayList<>();
				for (int i = 3; i < children.size(); i++)
					subs.add(children.get(i));
				if (foundMessageSend(subs, 0))
					decl.setInitialValue(stk.pop());
				else if (foundAssign(subs, 0))
					decl.setInitialValue(stk.pop());
				else
					decl.setInitialValue(children.get(3));
			}
		}
		stk.push(quoted(decl, quote));
		return true;
	}

	private Boolean foundMessageSend(ArrayList<MobAstElement> children, int quote) {
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
				keyword.add(((MobSymbol) children.get(1)), children.get(2));
				keyword.setReceiver(children.get(0));
				stk.push(quoted(keyword, quote));
				return true;
			}
			MobBinaryMessage binary = new MobBinaryMessage();
			binary.setOperator(((MobSymbol) children.get(1)));
			binary.setArgument(children.get(2));
			binary.setReceiver(children.get(0));
			stk.push(quoted(binary, quote));
			return true;
		}
		if (children.size() > 3) {
			for (int i = 1; i < children.size(); i += 2) {
				String kw = ((MobSymbol) children.get(i)).rawValue();
				if (kw.charAt(kw.length() - 1) != ':')
					return false;
			}
			MobKeywordMessage keyword = new MobKeywordMessage();
			for (int i = 1; i < children.size(); i += 2) {
				keyword.add(((MobSymbol) children.get(i)), children.get(i + 1));
			}
			keyword.setReceiver(children.get(0));
			stk.push(quoted(keyword, quote));
			return true;
		}
		return false;
	}

	@Override
	public void visitNode(SNode node) {
		ArrayList<MobAstElement> children = new ArrayList<>();
		node.children().forEach(s -> {
			s.accept(this);
			children.add(stk.pop());
		});
		if (foundUnit(node, children))
			return;
		if (foundParameters(node, children))
			return;
		if (foundDecl(children, node.quote()))
			return;
		if (foundReturn(children, node.quote()))
			return;
		if (foundAssign(children, node.quote()))
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

		switch (contents.charAt(0)) {
		case '\'':
			exp = this.env.newString(contents.substring(1, contents.length() - 1));
			break;
		case '$':
			exp = this.env.newCharacter(contents.charAt(1));
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
