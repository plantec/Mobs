package mob.model.primitives;

public class MobSymbolClass extends MobPrimitiveClass<String> {
	
	public MobSymbolClass(MobClass def) {
		super(def);
	}

	@Override
	public MobSymbol newInstance(String mob) {
		return new MobSymbol(this, mob);
	}
}
