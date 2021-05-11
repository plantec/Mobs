package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.ast.MobInterpretableVisitor;

public class MobNil extends MobObject implements MobAstElement {

	public MobNil(MobClass def) {
		super(def);
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitNil(this);		
	}

}
