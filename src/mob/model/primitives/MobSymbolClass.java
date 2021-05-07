package mob.model.primitives;

public class MobSymbolClass extends MobPrimitiveClass<String> {
	
	public MobSymbolClass(MobObjectClass def) {
		super(def);
	}

	@Override
	public MobSymbol newInstance(String mob) {
		return new MobSymbol(this, mob);
	}
}
