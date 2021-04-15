package mob.sinterpreter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mob.model.MobAssign;
import mob.model.MobExp;
import mob.model.MobUnit;
import mob.model.MobUnitDef;
import mob.model.primitives.MobFalseDef;
import mob.model.primitives.MobFloatDef;
import mob.model.primitives.MobIntegerDef;
import mob.model.primitives.MobNilDef;
import mob.model.primitives.MobStringDef;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobSymbolDef;
import mob.model.primitives.MobTrueDef;
import stree.parser.SNode;
import stree.parser.SParser;
import stree.parser.SVisitor;

public class MobTreeBuilder implements SVisitor {
	private ArrayDeque<MobExp> stk;
	
	public MobTreeBuilder() {
		this.stk = new ArrayDeque<>();
	}
	
	protected void reset() {
		this.stk.clear();
	}

	public List<MobExp> result() {
		List<MobExp> result = new ArrayList<>();
		this.stk.forEach(s -> result.add(s));
		Collections.reverse(result);
		return  result;
	}

	public List<MobExp> run(List<SNode> sexps) {
		this.reset();
		for (SNode n : sexps) {
			n.accept(this);
		}
		return this.result();
	}

	public List<MobExp> run(String input) {
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
		MobUnit unit = new MobUnitDef().newInstance();
		unit.setQuote(node.quote());
		node.children().forEach(s-> { s.accept(this); unit.add(stk.pop()); });
		if (unit.size() == 3 && unit.get(1) instanceof MobSymbol) {
			MobSymbol symb = (MobSymbol) unit.get(1);
			if (symb.is(":=")) {
				MobAssign assign = new MobAssign();
				assign.addAll(unit.children());
				stk.push(assign);
				return;
			}
		} 
		stk.push(unit);
		
	}

	@Override
	public void visitLeaf(SNode node) {
		String contents = node.parsedString();
		MobExp exp;
		if (contents.equals("true"))
			exp = new MobTrueDef().newInstance();
		else if (contents.equals("false"))
			exp = new MobFalseDef().newInstance();
		else if (contents.equals("nil")) 
			exp = new MobNilDef().newInstance();
		else
			switch (contents.charAt(0)) {
			case '"':
				exp = new MobStringDef().newInstance(contents.substring(1, contents.length() - 1));
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
					exp = new MobIntegerDef().newInstance(i);
				} catch (NumberFormatException ex) {
					Float f = Float.parseFloat(contents);
					exp = new MobFloatDef().newInstance(f);
				}
				break;
			default:
				exp =  new MobSymbolDef().newInstance(contents);
			}
		exp.setQuote(node.quote());
		this.stk.push(exp);
	}

}
