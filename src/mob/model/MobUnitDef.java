package mob.model;

import mob.sinterpreter.MobContext;

public class MobUnitDef extends MobObjectDef {

	public MobUnitDef() {
		this.addMethod(new MobMethod("value") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				unit.contents().accept(ctx.interpreter());
			}
		});
	}

	public MobUnit newInstance() {
		return new MobUnit(this);
	}

	public MobUnit newInstance(MobEntity contents) {
		return new MobUnit(this, contents);
	}

}
