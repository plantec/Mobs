package mob.model.primitives;

import mob.model.MobClass;
import mob.sinterpreter.MobEnvironment;

public class MobSymbolClass extends MobPrimitiveClass<String> {
	
	public MobSymbolClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	@Override
	public MobSymbol newInstance(String mob) {
		return new MobSymbol(this, mob);
	}
}
