package mob.model;

import java.util.ArrayList;
import java.util.List;

import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;

public class MobUnitDef extends MobObjectDef {

	public MobUnitDef() {
		this.addMethod(new MobMethod("value") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				unit.contents().accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("value:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobEntity arg = ctx.pop();
				List<MobEntity> argList = new ArrayList<>();
				argList.add(arg);
				if (! (unit.arguments().size() != argList.size()) ) {
					throw new Error(unit.arguments().size() + " intended formal arguments but "+ argList.size() +" arguments actually passed");
				}
				unit.accept(ctx.interpreter());
			}
		});
	}

	public MobUnit newInstance() {
		return new MobUnit(this);
	}

	public MobUnit newInstance(MobEnvironment env, MobEntity contents) {	
		return contents.asUnit(env);
	}

}
