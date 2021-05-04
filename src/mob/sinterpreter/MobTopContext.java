package mob.sinterpreter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mob.model.MobEntity;
import mob.model.primitives.*;

public class MobTopContext extends MobContext {
	MobEnvironment env;
	private ArrayDeque<MobEntity> stk;
	private MobInterpreter interpreter;

	
	public MobTopContext(MobEnvironment env, MobInterpreter interpreter) {
		super(null);
		this.env = env;
		this.interpreter = interpreter;
		this.stk = new ArrayDeque<>();
	}
	
	public List<MobEntity> result() {
		List<MobEntity> result = new ArrayList<>();
		this.stk.forEach(s -> result.add(s));
		Collections.reverse(result);
		return  result;
	}
	
	public void clear() {
		super.clear();
		this.stk.clear();
	}
	
	public MobInterpreter interpreter() {
		return this.interpreter;
	}

	public void push(MobEntity exp) {
		this.stk.push(exp);
	}
	
	public MobEntity pop() {
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

	public MobSymbol newSymbol(String p) {
		return this.env.newSymbol(p);
	}

	public MobNil newNil() {
		return this.env.newNil();
	}


}
