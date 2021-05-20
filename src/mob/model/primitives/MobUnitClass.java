package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.ast.MobQuoted;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobUnitClass extends MobObjectClass {

	public MobUnitClass(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}

	public void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("value") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unit.code();
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
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
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				newCtx.setParameterValue(0, arg);
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unit.code();
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
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
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				newCtx.setParameterValue(0, arg1);
				newCtx.setParameterValue(1, arg2);
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unit.code();
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
			}
		});
		this.addMethod(new MobMethod("values:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit unit = (MobUnit) receiver;
				MobAstElement arg = ctx.pop();
				MobQuoted quo = (MobQuoted) arg;
				MobObject seq = (MobObject) quo.entity();
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				for (int i = 0; i < seq.size(); i++)
					newCtx.setParameterValue(i, (MobAstElement) seq.instVarAt(i));
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unit.code();
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
			}
		});
		
	}

	public MobUnit newInstance() {
		return new MobUnit(this);
	}
}
