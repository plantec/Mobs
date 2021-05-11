package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobFalseClass extends MobMetaClass {

	public MobFalseClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	public void initializePrimitives() {
		super.initializePrimitives();

		this.addMethod(new MobMethod("ifFalse:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit falseArg = (MobUnit) ctx.pop();
				for (MobAstElement e : falseArg.code())
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
				for (MobAstElement e : falseArg.code())
					e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});
		this.addMethod(new MobMethod("ifFalse:ifTrue:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				ctx.pop();
				MobUnit falseArg = (MobUnit) ctx.pop();
				for (MobAstElement e : falseArg.code())
					e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});

	}

	@Override
	public MobFalse newInstance() {
		return new MobFalse(this);
	}

}
