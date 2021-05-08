package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.ast.MobAstVisitor;
import mob.ast.MobQuoted;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobMethod;

public class MobUnitClass extends MobClass {

	public MobUnitClass(MobClass def) {
		super(def);
		this.addMethod(new MobMethod("value") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit unit = (MobUnit) receiver;
				ctx.interpreter().pushContext();
				for (MobAstElement e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		
		this.addMethod(new MobMethod("value:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobAstElement arg = ctx.pop();
				if (!unit.hasParameters()) 
					throw new Error("0 intended formal parameters but 1 arguments actually passed");
				if ((unit.parameters().size() != 1) ) {
					throw new Error(unit.parameters().size() + " intended formal parameters but 1 arguments actually passed");
				}
				ctx.interpreter().pushContext(unit);
				ctx.interpreter().topContext().setParameterValue(0, arg);
				for (MobAstElement e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		
		this.addMethod(new MobMethod("value:value:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobAstElement arg2 = ctx.pop();
				MobAstElement arg1 = ctx.pop();
				if (!unit.hasParameters()) 
					throw new Error("0 intended formal parameters but 2 arguments actually passed");
				if ((unit.parameters().size() != 2) ) {
					throw new Error(unit.parameters().size() + " intended formal parameters but 2 arguments actually passed");
				}
				ctx.interpreter().pushContext(unit);
				ctx.interpreter().topContext().setParameterValue(0, arg1);
				ctx.interpreter().topContext().setParameterValue(1, arg2);
				for (MobAstElement e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		this.addMethod(new MobMethod("values:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobAstElement arg = ctx.pop();
				MobQuoted quo = (MobQuoted) arg;
				MobSequence seq = (MobSequence) quo.entity();
				ctx.interpreter().pushContext(unit);
				for (int i = 0; i < seq.size(); i++)
					ctx.interpreter().topContext().setParameterValue(i, seq.get(i));
				for (MobAstElement e : unit.code())
					e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
			}
		});
		
	}

	public MobUnit newInstance() {
		return new MobUnit(this);
	}
}
