package mob.sinterpreter;

import mob.model.MobEntity;

public class MobVariable {
	private String name;
	private MobEntity value;

	public MobVariable(String name, MobEntity value) {
		this.value = value;
		this.name = name;
	}

	public MobVariable(String name) {
		this(name, null);
	}

	public MobVariable(MobEntity value) {
		this(null, value);
	}

	public String name() {
		return this.name;
	}

	public MobEntity value() {
		return this.value;
	}

	public void setValue(MobEntity mobExp) {
		this.value = mobExp;
	}

}
