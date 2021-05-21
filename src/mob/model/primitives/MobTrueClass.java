package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobTrueClass extends MobBooleanClass {

	public MobTrueClass(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}

	public void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("ifTrue:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit trueArg = (MobUnit) ctx.pop();
				MobAstElement e = trueArg.code();
				e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});
		this.addMethod(new MobMethod("ifFalse:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				ctx.pop();
				ctx.returnElement(ctx.newNil());
			}
		});
		this.addMethod(new MobMethod("ifFalse:ifTrue:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit trueArg = (MobUnit) ctx.pop();
				ctx.pop();
				MobAstElement e = trueArg.code();
				e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});
		this.addMethod(new MobMethod("ifTrue:ifFalse:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				ctx.pop();
				MobUnit trueArg = (MobUnit) ctx.pop();
				MobAstElement e = trueArg.code();
				e.accept(ctx.interpreter());
				ctx.returnElement(ctx.pop());
			}
		});

	}
		
	@Override
	public MobObject newInstance() {
		MobObject i = new MobObject(this);
		i.primValueAtPut(0, true);
		return i;
	}
}
