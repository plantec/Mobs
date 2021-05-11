package mob.ast;

import mob.model.MobEntity;
import mob.model.primitives.MobObject;

public class MobAssign extends MobEntity implements MobAstElement {
	MobAstElement left;
	MobAstElement right;
	
	public MobAstElement left() {
		return this.left;
	}
	public void setLeft(MobObject left) {
		this.left = left;
	}
	public MobAstElement right() {
		return this.right;
	}
	public void setRight(MobAstElement mobEntity) {
		this.right = mobEntity;
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitAssign(this);
	}
	
}
