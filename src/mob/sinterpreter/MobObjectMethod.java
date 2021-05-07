package mob.sinterpreter;

import mob.ast.MobAstElement;
import mob.model.primitives.MobUnit;

public class MobObjectMethod extends MobMethod {
	MobUnit code;

	public MobObjectMethod(String signature, MobUnit code) {
		super(signature);
		this.code = code;
	}

	public void run(MobContext ctx, MobAstElement receiver) {
		ctx.interpreter().pushContext(this.code);
		for (int i = code.parameters().size() - 1; i >= 0; i--)
			ctx.interpreter().topContext().setParameterValue(i, ctx.pop());
		ctx.interpreter().topContext().addVariable(new MobVariable("self", receiver));
		for (MobAstElement e : this.code.code())
			e.accept(ctx.interpreter());
		ctx.interpreter().popContext();
	}

}
