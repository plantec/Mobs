package mob.model.primitives;

import mob.ast.MobAstVisitor;

public class MobFloat extends MobPrimitive<Float> {

	public MobFloat(MobObjectClass def, Float mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitFloat(this);		
	}

}
