package mob.sinterpreter;

import mob.ast.MobAstElement;
import mob.model.MobObject;
import mob.model.primitives.MobUnit;

public class MobObjectMethod extends MobMethod {
	MobUnit code;

	public MobObjectMethod(String signature, MobUnit code) {
		super(signature);
		this.code = code;
	}

	public void run(MobContext ctx, MobAstElement receiver) {
		MobContext newCtx = new MobContext(ctx.interpreter().topContext());
		newCtx.setReceiver((MobObject) receiver);
		newCtx.setUnit(this.code);		
		for (int i = code.parameters().size() - 1; i >= 0; i--)
			newCtx.setParameterValue(i, ctx.pop());
		ctx.interpreter().pushContext(newCtx);
		MobAstElement e = this.code.code();
		ctx.interpreter().accept(e);
		ctx.interpreter().popContext();
	}

}
