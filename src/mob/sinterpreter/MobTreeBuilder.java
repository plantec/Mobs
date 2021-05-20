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
		MobObject parameters = this.env.newSequence(children);
		stk.push(parameters);
		return true;
	}

	private int parametersEnd(ArrayList<MobAstElement> children) {
		for (int pos = 0; pos < children.size(); pos++) {
			MobAstElement c = children.get(pos);
			if (!(c instanceof MobObject))
				return -1;
			if (!(((MobObject) c).isKindOf(env.getClassByName("Symbol"))))
				return -1;
			if (((MobObject) c).rawValue().equals("|")) {
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
				MobObject s = (MobObject) children.get(i);
				unit.addParameter((String)s.rawValue());
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
		if (!(children.get(0) instanceof MobObject))
			return false;
		if (!((MobObject)children.get(0)).isKindOf(env.getClassByName("Symbol"))) 
			return false;
		if (!(((MobObject) children.get(0)).rawValue().equals("^")))
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
		if (!(children.get(1) instanceof MobObject))
			return false;
		if (!((MobObject)children.get(1)).isKindOf(env.getClassByName("Symbol"))) 
			return false;
		if (!(((MobObject) children.get(1)).rawValue().equals(":=")))
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
		if (!(children.get(0) instanceof MobObject))
			return false;
		if (!(children.get(1) instanceof MobObject))
			return false;
		if (!((MobObject)children.get(0)).isKindOf(env.getClassByName("Symbol"))) 
			return false;
		if (!((MobObject)children.get(1)).isKindOf(env.getClassByName("Symbol"))) 
			return false;
		String symb0 = (String) ((MobObject)children.get(0)).rawValue();
		String symb1 = (String) ((MobObject)children.get(1)).rawValue();
		if (!symb0.equals("var"))
			return false;
		MobVarDecl decl = new MobVarDecl();
		decl.setName(symb1);
		if (children.size() >= 4 && ((MobObject)children.get(2)).isKindOf(env.getClassByName("Symbol"))) {
			String symb2 = (String) ((MobObject)children.get(2)).rawValue();
			if (symb2.equals(":=")) {
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
	
	private int enclosedDeclEnd(ArrayList<MobAstElement> children) {
		for (int pos = 1; pos < children.size(); pos++) {
			if (!(children.get(pos) instanceof MobObject))
				return -1;
			MobObject c = (MobObject) children.get(pos);
			if (!(c).isKindOf(env.getClassByName("Symbol"))) {
				return -1;
			}
			if (c.rawValue().equals("|")) {
				return pos;
			}
		}
		return -1;
	}

	private int foundEnclosedDecl(ArrayList<MobAstElement> children, int quote) {
		if (children.size() < 2)
			return -1;
		if (!(children.get(0) instanceof MobObject))
			return -1;
		if (!((MobObject)children.get(0)).isKindOf(env.getClassByName("Symbol"))) 
			return -1;
		String symb0 = (String) ((MobObject)children.get(0)).rawValue();
		if (!symb0.equals("|"))
			return -1;		
		int end = this.enclosedDeclEnd(children);
		if (end == -1)
			return -1;
		int pos = 1;
		ArrayList<MobAstElement> subs = new ArrayList<>();
		while (pos < end) {
			String v = (String) ((MobObject)children.get(pos)).rawValue();
			MobVarDecl decl = new MobVarDecl();
			decl.setName(v);
			subs.add(decl);
		}
		MobObject sequence = this.env.newSequence(subs);
		stk.push(quoted(sequence, quote));
		return end;
	}

	private Boolean foundMessageSend(ArrayList<MobAstElement> children, int quote) {
		if (children.size() < 2)
			return false;
		if (!(children.get(1) instanceof MobObject))
			return false;
		if (!((MobObject)children.get(1)).isKindOf(env.getClassByName("Symbol"))) 
			return false;
		if (children.size() == 2) {
			MobUnaryMessage unary = new MobUnaryMessage();
			unary.setKeyword((String)(((MobObject) children.get(1)).rawValue()));
			unary.setReceiver(children.get(0));
			stk.push(quoted(unary, quote));
			return true;
		}
		if (children.size() == 3) {
			MobObject s = (MobObject) children.get(1);
			String op = (String)s.rawValue();
			if (op.charAt(op.length() - 1) == ':') {
				MobKeywordMessage keyword = new MobKeywordMessage();
				keyword.add((String)((MobObject) children.get(1)).rawValue(), children.get(2));
				keyword.setReceiver(children.get(0));
				stk.push(quoted(keyword, quote));
				return true;
			}
			MobBinaryMessage binary = new MobBinaryMessage();
			binary.setOperator(((String) ((MobObject)children.get(1)).rawValue()));
			binary.setArgument(children.get(2));
			binary.setReceiver(children.get(0));
			stk.push(quoted(binary, quote));
			return true;
		}
		if (children.size() > 3) {
			for (int i = 1; i < children.size(); i += 2) {
				String kw = ((String)((MobObject) children.get(i)).rawValue());
				if (kw.charAt(kw.length() - 1) != ':')
					return false;
			}
			MobKeywordMessage keyword = new MobKeywordMessage();
			for (int i = 1; i < children.size(); i += 2) {
				keyword.add((String)((MobObject) children.get(i)).rawValue(), children.get(i + 1));
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
		MobObject sequence = this.env.newSequence(children);
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
