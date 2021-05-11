package mob.model.primitives;

import mob.ast.MobInterpretableVisitor;

public class MobCharacter extends MobPrimitive<Character> {

	public MobCharacter(MobClass def, Character mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitCharacter(this);
	}

}
