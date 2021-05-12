package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;
import mob.model.MobClass;

public class MobString extends MobPrimitive<String> {

	public MobString(MobClass def, String mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitString(this);
		
	}

}
