package mob.sinterpreter;

import mob.model.MobObject;
import mob.model.primitives.MobNilDef;

public class MobVariable {
	private String name;
	private MobObject value;

	public MobVariable(String name, MobObject value) {
		this.value = value;
		this.name = name;
	}

	public MobVariable(MobObject value) {
		this(null, value);
	}

	public MobVariable(String name) {
		this(name, new MobNilDef().newInstance());
	}

	public MobVariable() {
		this(null, new MobNilDef().newInstance());
	}

	public String name() {
		return this.name;
	}

	public MobObject value() {
		return this.value;
	}

	public void setValue(MobObject mobExp) {
		this.value = mobExp;
	}

}
