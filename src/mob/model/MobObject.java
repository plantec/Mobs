package mob.model;

import mob.sinterpreter.MobContext;

public abstract class MobObject extends MobEntity  {
	private MobObjectDef def;
	
	public MobObject(MobObjectDef def) {
		this.def = def;
	}
		
	public MobObjectDef def() {
		return this.def;
	}
		
	public void run (MobContext ctx, String signature) {
		MobMethod m = this.def.methodNamed(signature);
		if (m == null) {
			throw new Error(this.mobString() + " does not understand '" + signature + "'");
		}
		m.run(ctx, this);
	}	
}
