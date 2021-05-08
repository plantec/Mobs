package mob.model.primitives;

import mob.ast.MobAstVisitor;

public class MobSymbol extends MobPrimitive<String> {

	public MobSymbol(MobClass def, String mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitSymbol(this);
	}
	
}
