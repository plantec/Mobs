package mob.model;

public class MobAssign extends MobEntity {
	MobObject left;
	MobObject right;
	
	public MobObject left() {
		return this.left;
	}
	public void setLeft(MobObject left) {
		this.left = left;
		this.left.setParent(this);
	}
	public MobObject right() {
		return this.right;
	}
	public void setRight(MobObject right) {
		this.right = right;
		this.right.setParent(this);
	}
	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitAssign(this);
	}

}
