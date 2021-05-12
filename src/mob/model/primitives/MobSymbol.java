package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;
import mob.model.MobClass;

public class MobSymbol extends MobPrimitive<String> {

	public MobSymbol(MobClass def, String mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitSymbol(this);
	}
	
}
