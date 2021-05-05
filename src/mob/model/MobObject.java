package mob.model;

import java.util.List;

import mob.sinterpreter.MobContext;

public class MobObject extends MobEntity  {
	private MobObjectDef def;
	
	public MobObject(MobObjectDef def) {
		this.def = def;
	}
		
	public MobObjectDef def() {
		return this.def;
	}
		
	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitObject(this);
	}

	public void run (MobContext ctx, String signature) {
		MobMethod m = this.def.methodNamed(signature);
		m.run(ctx, this);
	}	
}
