package mob.model.primitives;

import mob.sinterpreter.MobEnvironment;

public class MobSymbolClass extends MobPrimitiveClass<String> {
	
	public MobSymbolClass(MobEnvironment environment, MobClass def) {
		super(environment, def);
	}

	@Override
	public MobSymbol newInstance(String mob) {
		return new MobSymbol(this, mob);
	}
}
