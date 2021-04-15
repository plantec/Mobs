package mob.sinterpreter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mob.model.MobAssign;
import mob.model.MobExp;
import mob.model.MobUnit;
import mob.model.MobVisitor;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;
import stree.parser.SNode;
import stree.parser.SParser;

public class MobInterpreter implements MobVisitor {	
	private ArrayDeque<MobExp> stk;
	private MobContext context;

	public MobInterpreter(MobEnvironment env) {
		this.context = new MobTopContext(env);
		this.stk = new ArrayDeque<>();
	}

	public List<MobExp> result() {
		List<MobExp> result = new ArrayList<>();
		this.stk.forEach(s -> result.add(s));
		Collections.reverse(result);
		return  result;
	}

	public List<MobExp> run(List<SNode> sexps) {
		this.stk.clear();
		List<MobExp> progs = new MobTreeBuilder(this.context.environment()).run(sexps);
		progs.forEach(e->e.accept(this));
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
	
	private void pushContext() {
		this.context = new MobContext(this.context);
	}
	private void popContext() {
		this.context = this.context.parent();
	}
	
	private void push(MobExp exp) {
		this.stk.push(exp);
	}
	
	private MobExp pop() {
		return this.stk.pop();
	}
	
	@Override
	public void visitTrue(MobTrue mobTrue) {
		MobVisitor.super.visitTrue(mobTrue);
		this.push(mobTrue);
	}

	@Override
	public void visitFalse(MobFalse mobFalse) {
		MobVisitor.super.visitFalse(mobFalse);
		this.push(mobFalse);
	}

	@Override
	public void visitFloat(MobFloat mobFloat) {
		MobVisitor.super.visitFloat(mobFloat);
		this.push(mobFloat);
	}

	@Override
	public void visitInteger(MobInteger mobInteger) {
		MobVisitor.super.visitInteger(mobInteger);
		this.push(mobInteger);
	}

	@Override
	public void visitSymbol(MobSymbol mobSymbol) {
		MobVisitor.super.visitSymbol(mobSymbol);
		this.push(mobSymbol);
	}

	@Override
	public void visitAssign(MobAssign mobAssign) {
		MobVisitor.super.visitAssign(mobAssign);
		this.push(mobAssign);
	}
	
	@Override
	public void visitNil(MobNil mobNil) {
		MobVisitor.super.visitNil(mobNil);
		this.push(mobNil);
	}

	@Override
	public void visitString(MobString mobString) {
		MobVisitor.super.visitString(mobString);
		this.push(mobString);
	}

	@Override
	public void visitUnit(MobUnit mobUnit) {
		MobVisitor.super.visitUnit(mobUnit);
	}
	
}
