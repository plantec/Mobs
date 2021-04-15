package mob.sinterpreter;

import mob.model.MobEntity;
import mob.model.MobExp;
import mob.model.primitives.MobNilDef;

public class MobVariable {
	private String name;
	private MobExp value;

	public MobVariable(String name, MobEntity value) {
		this.value = value;
		this.name = name;
	}

	public MobVariable(MobEntity value) {
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

	public MobExp value() {
		return this.value;
	}

	public void setValue(MobExp mobExp) {
		this.value = mobExp;
	}

}
