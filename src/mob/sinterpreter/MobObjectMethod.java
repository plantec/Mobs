package mob.sinterpreter;

import mob.ast.MobAstElement;
import mob.model.MobObject;
import mob.model.primitives.MobUnit;

public class MobObjectMethod extends MobMethod {
	MobObject code;

	public MobObjectMethod(String signature, MobObject code) {
		super(signature);
		this.code = code;
	}

	public void run(MobContext ctx, MobAstElement receiver) {
		MobContext newCtx = new MobContext(ctx.interpreter().topContext());
		newCtx.setReceiver((MobObject) receiver);
		newCtx.setUnit(this.code);
		MobUnit unitCls = (MobUnit) this.code.definition();
		for (int i = unitCls.formalParameters(this.code).size() - 1; i >= 0; i--)
			newCtx.setParameterValue(i, ctx.pop());
		ctx.interpreter().pushContext(newCtx);
		MobAstElement e = unitCls.code(this.code);
		if (e != null)
			ctx.interpreter().accept(e);
		ctx.interpreter().popContext();
	}

}
