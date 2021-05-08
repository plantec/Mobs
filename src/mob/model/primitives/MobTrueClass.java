package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobMethod;

public class MobTrueClass extends MobBooleanClass {

	public MobTrueClass(MobClass def) {
		super(def);
		this.addMethod(new MobMethod("ifTrue:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit trueArg = (MobUnit) ctx.pop();
				for (MobAstElement e : trueArg.code())
					e.accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("ifFalse:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				ctx.pop();
				ctx.push(receiver);
			}
		});
		this.addMethod(new MobMethod("ifFalse:ifTrue:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobUnit trueArg = (MobUnit) ctx.pop();
				ctx.pop();
				for (MobAstElement e : trueArg.code())
					e.accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("ifTrue:ifFalse:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				ctx.pop();
				MobUnit trueArg = (MobUnit) ctx.pop();
				for (MobAstElement e : trueArg.code())
					e.accept(ctx.interpreter());
			}
		});

	}
		
	@Override
	public MobTrue newInstance() {
		return new MobTrue(this);
	}
}
