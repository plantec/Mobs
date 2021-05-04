package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobMethod;
import mob.sinterpreter.MobContext;

public class MobFloatDef extends MobPrimitiveDef<Float> {

	public MobFloatDef() {
		this.addMethod(new MobMethod("+") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newFloat(r.rawValue()+arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()+arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newFloat(r.rawValue()-arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()-arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newFloat(r.rawValue()*arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()*arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newFloat(r.rawValue()/arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()/arg.rawValue()));
				}
			}
		});
		
		this.addMethod(new MobMethod("negated") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobFloat r = (MobFloat) receiver;
				ctx.push(ctx.newFloat(r.rawValue()*-1));
			}
		});
	}
	@Override
	public MobFloat newInstance(Float mob) {
		return new MobFloat(this, mob);
	}

}
