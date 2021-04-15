package mob.model.primitives;

public class MobSymbolDef extends MobPrimitiveDef<String> {

	@Override
	public MobSymbol newInstance(String mob) {
		return new MobSymbol(this, mob);
	}
}
