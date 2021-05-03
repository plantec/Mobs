package mob.model;

public abstract class MobMessageSend extends MobEntity {
	MobEntity receiver;

	public MobEntity receiver() {
		return this.receiver;
	}

	public void setReceiver(MobEntity receiver) {
		this.receiver = receiver;
	}

}
