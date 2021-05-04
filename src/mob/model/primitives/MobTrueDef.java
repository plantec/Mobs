package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobMethod;
import mob.sinterpreter.MobContext;

public class MobTrueDef extends MobBoolDef {
	private static MobTrueDef trueDef = new MobTrueDef();
	private static MobTrue mtrue = new MobTrue(trueDef);

	public MobTrueDef() {
		this.addMethod(new MobMethod("ifTrue") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
			}
		});

	}
	
	
	
	@Override
	public MobTrue newInstance() {
		return MobTrueDef.mtrue;
	}

}
