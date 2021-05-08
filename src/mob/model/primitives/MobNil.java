package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.ast.MobAstVisitor;

public class MobNil extends MobObject implements MobAstElement {

	public MobNil(MobClass def) {
		super(def);
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitNil(this);		
	}

}
