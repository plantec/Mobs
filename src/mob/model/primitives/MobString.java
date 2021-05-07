package mob.model.primitives;

import mob.ast.MobAstVisitor;

public class MobString extends MobPrimitive<String> {

	public MobString(MobObjectClass def, String mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitString(this);
		
	}

}
