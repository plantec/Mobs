package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobMethod;
import mob.model.MobUnit;
import mob.sinterpreter.MobContext;

public class MobFalseDef extends MobBoolDef {
	private static MobFalseDef falseDef = new MobFalseDef();
	private static MobFalse mfalse = new MobFalse(falseDef);
	
	public MobFalseDef() {
		this.addMethod(new MobMethod("ifFalse:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit falseArg = (MobUnit) ctx.pop();
				for (MobEntity e : falseArg.code())
					e.accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("ifTrue:") {
			public void run(MobContext ctx, MobEntity receiver) {
				ctx.pop();
				ctx.push(receiver);
			}
		});
		this.addMethod(new MobMethod("ifTrue:ifFalse:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit falseArg = (MobUnit) ctx.pop();
				ctx.pop();
				for (MobEntity e : falseArg.code())
					e.accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("ifFalse:ifTrue:") {
			public void run(MobContext ctx, MobEntity receiver) {
				ctx.pop();
				MobUnit falseArg = (MobUnit) ctx.pop();
				for (MobEntity e : falseArg.code())
					e.accept(ctx.interpreter());
			}
		});

	}
	@Override
	public MobFalse newInstance() {
		return MobFalseDef.mfalse;
	}

}
