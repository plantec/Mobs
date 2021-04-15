package mob.sinterpreter;

import mob.model.primitives.*;

public class MobTopContext extends MobContext {
	MobEnvironment env;
	
	public MobTopContext(MobEnvironment env) {
		super(null);
		this.env = env;
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
