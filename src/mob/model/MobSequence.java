package mob.model;

public class MobSequence extends MobObject {

	public MobSequence(MobObjectDef def) {
		super(def);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitSequance(this);
		
	}

}
