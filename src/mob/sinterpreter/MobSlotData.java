package mob.sinterpreter;

import mob.ast.MobAstElement;
import mob.model.MobObject;

public class MobSlotData implements MobDataAccess {
	MobObject receiver;
	int pos;
	
	public MobSlotData(MobObject receiver, int pos) {
		this.receiver = receiver;
		this.pos = pos;
	}
	
	@Override
	public MobAstElement value() {
		return (MobAstElement) this.receiver.instVarAt(this.pos);
	}

	@Override
	public void setValue(MobAstElement mobExp) {
		this.receiver.instVarAtPut(this.pos, mobExp);
	}

}
