package mob.model;

import java.util.ArrayList;
import java.util.List;

import mob.sinterpreter.MobContext;

public class MobUnitDef extends MobObjectDef {

	public MobUnitDef() {
		this.addMethod(new MobMethod("value") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				for (MobEntity e : unit.code())
					e.accept(ctx.interpreter());
			}
		});
		
		this.addMethod(new MobMethod("value:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobEntity arg = ctx.pop();
				List<MobEntity> argList = new ArrayList<>();
				argList.add(arg);
				if (!unit.hasParameters()) 
					throw new Error("0 intended formal parameters but "+ argList.size() +" arguments actually passed");
				if (! (unit.plist().size() != argList.size()) ) {
					throw new Error(unit.plist().size() + " intended formal parameters but "+ argList.size() +" arguments actually passed");
				}
				unit.accept(ctx.interpreter());
			}
		});
	}

	public MobUnit newInstance() {
		return new MobUnit(this);
	}

}
