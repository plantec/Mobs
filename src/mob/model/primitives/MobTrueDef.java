package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobMethod;
import mob.model.MobUnit;
import mob.sinterpreter.MobContext;

public class MobTrueDef extends MobBoolDef {
	private static MobTrueDef trueDef = new MobTrueDef();
	private static MobTrue mtrue = new MobTrue(trueDef);

	public MobTrueDef() {
		this.addMethod(new MobMethod("ifTrue:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit trueArg = (MobUnit) ctx.pop();
				trueArg.contents().accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("ifFalse:") {
			public void run(MobContext ctx, MobEntity receiver) {
				ctx.pop();
				ctx.push(receiver);
			}
		});
		this.addMethod(new MobMethod("ifFalse:ifTrue:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobUnit trueArg = (MobUnit) ctx.pop();
				ctx.pop();
				trueArg.contents().accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("ifTrue:ifFalse:") {
			public void run(MobContext ctx, MobEntity receiver) {
				ctx.pop();
				MobUnit trueArg = (MobUnit) ctx.pop();
				trueArg.contents().accept(ctx.interpreter());
			}
		});

	}
	
	
	
	@Override
	public MobTrue newInstance() {
		return MobTrueDef.mtrue;
	}

}
