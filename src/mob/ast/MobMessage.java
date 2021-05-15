package mob.ast;

public abstract class MobMessage extends MobEntity implements MobAstElement {
	MobAstElement receiver;

	public MobAstElement receiver() {
		return this.receiver;
	}

	public void setReceiver(MobAstElement receiver) {
		this.receiver = receiver;
	}
	
}
