package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobFalseClass extends MobPrimitiveClass<Boolean> {

	public MobFalseClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	public void initializePrimitives() {
		super.initializePrimitives();

		this.addMethod(new MobMethod("ifFalse:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit falseArg = (MobUnit) ctx.pop();
				MobAstElement e = falseArg.code();
				e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});
		this.addMethod(new MobMethod("ifTrue:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				ctx.pop();
				ctx.returnElement(ctx.newNil());
			}
		});
		this.addMethod(new MobMethod("ifTrue:ifFalse:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit falseArg = (MobUnit) ctx.pop();
				ctx.pop();
				MobAstElement e = falseArg.code();
				e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});
		this.addMethod(new MobMethod("ifFalse:ifTrue:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				ctx.pop();
				MobUnit falseArg = (MobUnit) ctx.pop();
				MobAstElement e = falseArg.code();
				e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});

	}

	@Override
	public MobObject newInstance() {
		MobObject i = new MobObject(this);
		i.instVarAtPut(0, false);
		return i;
	}
	
	@Override
	public MobObject newInstance(Boolean mob) {
		throw new Error("Should not be sent");
	}

}
