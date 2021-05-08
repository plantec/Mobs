package mob.model.primitives;

import mob.ast.MobAstVisitor;

public class MobCharacter extends MobPrimitive<Character> {

	public MobCharacter(MobClass def, Character mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitCharacter(this);
	}

}
