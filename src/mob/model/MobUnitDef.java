package mob.model;

import java.util.ArrayList;
import java.util.List;

import mob.sinterpreter.MobContext;

public class MobUnitDef extends MobObjectDef {

	public MobUnitDef() {
		this.addMethod(new MobMethod("value") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				ctx.interpreter().pushContext();
				for (MobEntity e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		
		this.addMethod(new MobMethod("value:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobEntity arg = ctx.pop();
				if (!unit.hasParameters()) 
					throw new Error("0 intended formal parameters but 1 arguments actually passed");
				if ((unit.plist().size() != 1) ) {
					throw new Error(unit.plist().size() + " intended formal parameters but 1 arguments actually passed");
				}
				ctx.interpreter().pushContext(unit);
				ctx.interpreter().topContext().setParameterValue(0, arg);
				for (MobEntity e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		
		this.addMethod(new MobMethod("value:value:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobEntity arg2 = ctx.pop();
				MobEntity arg1 = ctx.pop();
				if (!unit.hasParameters()) 
					throw new Error("0 intended formal parameters but 2 arguments actually passed");
				if ((unit.plist().size() != 2) ) {
					throw new Error(unit.plist().size() + " intended formal parameters but 2 arguments actually passed");
				}
				ctx.interpreter().pushContext(unit);
				ctx.interpreter().topContext().setParameterValue(0, arg1);
				ctx.interpreter().topContext().setParameterValue(1, arg2);
				for (MobEntity e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		this.addMethod(new MobMethod("values:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobEntity arg = ctx.pop();
				MobQuoted quo = (MobQuoted) arg;
				MobSequence seq = (MobSequence) quo.entity();
				ctx.interpreter().pushContext(unit);
				for (int i = 0; i < seq.size(); i++)
					ctx.interpreter().topContext().setParameterValue(i, seq.get(i));
				for (MobEntity e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		
	}

	public MobUnit newInstance() {
		return new MobUnit(this);
	}

}
