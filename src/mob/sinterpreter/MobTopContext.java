package mob.sinterpreter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mob.ast.MobAstElement;
import mob.model.primitives.*;

public class MobTopContext extends MobContext {
	MobEnvironment env;
	private ArrayDeque<MobAstElement> stk;
	private MobInterpreter interpreter;

	
	public MobTopContext(MobEnvironment env, MobInterpreter interpreter) {
		super(null);
		this.env = env;
		this.addVariable(new MobVariable("true", this.newTrue()));
		this.addVariable(new MobVariable("false", this.newFalse()));
		this.addVariable(new MobVariable("nil", this.newNil()));
		this.addVariable(new MobVariable("String", this.env.stringClass()));
		this.addVariable(new MobVariable("Character", this.env.characterClass()));
		this.addVariable(new MobVariable("Integer", this.env.integerClass()));
		this.addVariable(new MobVariable("Float", this.env.floatClass()));
		this.addVariable(new MobVariable("Unit", this.env.unitClass()));
		this.addVariable(new MobVariable("Object", this.env.mobClass()));
		this.interpreter = interpreter;
		this.stk = new ArrayDeque<>();
	}
	
	public List<MobAstElement> result() {
		List<MobAstElement> result = new ArrayList<>();
		this.stk.forEach(s -> result.add(s));
		Collections.reverse(result);
		return  result;
	}
	
	public MobInterpreter interpreter() {
		return this.interpreter;
	}

	public void push(MobAstElement exp) {
		this.stk.push(exp);
	}
	
	public MobAstElement pop() {
		return this.stk.pop();
	}

	public MobEnvironment environment() {
		return this.env;
	}

	public MobFalse newFalse() {
		return this.env.newFalse();
	}

	public MobTrue newTrue() {
		return this.env.newTrue();
	}

	public MobFloat newFloat(Float p) {
		return this.env.newFloat(p);
	}

	public MobInteger newInteger(Integer p) {
		return this.env.newInteger(p);
	}

	public MobString newString(String p) {
		return this.env.newString(p);
	}

	public MobCharacter newCharacter(Character p) {
		return this.env.newCharacter(p);
	}

	public MobSymbol newSymbol(String p) {
		return this.env.newSymbol(p);
	}

	public MobNil newNil() {
		return this.env.newNil();
	}


}
