package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobMethod;
import mob.sinterpreter.MobContext;

public class MobTrueDef extends MobBoolDef {
	private static MobTrueDef trueDef = new MobTrueDef();
	private static MobTrue mtrue = new MobTrue(trueDef);

	public MobTrueDef() {
		this.addMethod(new MobMethod("ifTrue: trueAction") {
			public void run(MobContext ctx, MobEntity receiver) {
				System.out.println("ok");
				ctx.pop().accept(ctx.interpreter());
			}
		});
		this.addMethod(new MobMethod("ifFalse: falseAction") {
			public void run(MobContext ctx, MobEntity receiver) {
				ctx.pop();
			}
		});
		this.addMethod(new MobMethod("ifTrue: trueAction ifFalse: falseAction") {
			public void run(MobContext ctx, MobEntity receiver) {
				ctx.pop();
				ctx.pop().accept(ctx.interpreter());
			}
		});

	}
	
	
	
	@Override
	public MobTrue newInstance() {
		return MobTrueDef.mtrue;
	}

}
