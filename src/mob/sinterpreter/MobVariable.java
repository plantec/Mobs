package mob.sinterpreter;

import mob.ast.MobAstElement;

public class MobVariable implements MobDataAccess {
	private String name;
	private MobAstElement value;

	public MobVariable(String name, MobAstElement value) {
		this.value = value;
		this.name = name;
	}

	public MobVariable(String name) {
		this(name, null);
	}

	public MobVariable(MobAstElement value) {
		this(null, value);
	}

	public String name() {
		return this.name;
	}

	public MobAstElement value() {
		return this.value;
	}

	public void setValue(MobAstElement mobExp) {
		this.value = mobExp;
	}

	@Override
	public void pushInto(MobContext context) {
		context.push(this.value);
	}

}
