package mob.model;

import java.util.List;

public class MobAssign extends MobEntity {
	MobObject left;
	MobEntity right;
	
	public MobObject left() {
		return this.left;
	}
	public void setLeft(MobObject left) {
		this.left = left;
		this.left.setParent(this);
	}
	public MobEntity right() {
		return this.right;
	}
	public void setRight(MobEntity mobEntity) {
		this.right = mobEntity;
		this.right.setParent(this);
	}
	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitAssign(this);
	}
	
}
