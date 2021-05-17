package mob.sinterpreter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.primitives.*;

public class MobTopContext extends MobContext {
	MobEnvironment env;
	private ArrayDeque<MobAstElement> stk;
	private MobInterpreter interpreter;

	
	public MobTopContext(MobEnvironment env, MobInterpreter interpreter) {
		super(null);
		this.env = env;
		this.addVariable(new MobVariable("true", env.getClassByName("True").newInstance()));
		this.addVariable(new MobVariable("false", env.getClassByName("False").newInstance()));
		this.addVariable(new MobVariable("nil", env.getClassByName("UndefinedObject").newInstance()));
		this.interpreter = interpreter;
		this.stk = new ArrayDeque<>();
	}
	
	public List<MobAstElement> result() {
		List<MobAstElement> result = new ArrayList<>();
		this.stk.forEach(s -> result.add(s));
		Collections.reverse(result);
		return  result;
	}
	
	public void resetStack() {
		this.stk.clear();
	}

	@Override
	public MobObject newNil() {
		return (MobObject) this.getVariableByName("nil").value();
	}

	@Override
	public MobFalse newFalse() {
		return (MobFalse) this.getVariableByName("false").value();
	}

	@Override
	public MobTrue newTrue() {
		return (MobTrue) this.getVariableByName("true").value();
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

	public MobVariable lookupVariableByName(String name) {
		MobClass v = this.environment().getClassByName(name);
		if (v != null)
			return new MobVariable(name, v);
		return super.lookupVariableByName(name);
	}


}
