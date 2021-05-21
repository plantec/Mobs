package mob.sinterpreter;

import mob.ast.MobAstElement;
import mob.model.MobObject;

public class MobSlotData implements MobDataAccess {
	MobObject receiver;
	String name;
	
	public MobSlotData(MobObject receiver, String name) {
		this.receiver = receiver;
		this.name = name;
	}
	
	@Override
	public MobAstElement value() {
		return (MobAstElement) this.receiver.getSlot(this.name);
	}

	@Override
	public void setValue(MobAstElement mobExp) {
		this.receiver.setSlot(this.name, (MobObject)mobExp);
	}

	@Override
	public void pushInto(MobContext context) {
		context.push(this.value());
	}

}
