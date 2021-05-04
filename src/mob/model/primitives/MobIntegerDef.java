package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobMethod;
import mob.sinterpreter.MobContext;

public class MobIntegerDef extends MobPrimitiveDef<Integer> {

	public MobIntegerDef() {
		this.addMethod(new MobMethod("+") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newInteger(r.rawValue()+arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()+arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newInteger(r.rawValue()-arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()-arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newInteger(r.rawValue()*arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()*arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(ctx.newInteger(r.rawValue()/arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(ctx.newFloat(r.rawValue()/arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("negated") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobInteger r = (MobInteger) receiver;
				ctx.push(ctx.newInteger(r.rawValue()*-1));
			}
		});
		this.addMethod(new MobMethod("<") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue() < arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(r.rawValue() < arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue() > arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(r.rawValue() > arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">=") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue() >= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(r.rawValue() >= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("<=") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue() <= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push(r.rawValue() <= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("=") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue().equals(arg.rawValue()) ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.push(ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("~=") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobEntity arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue().equals(arg.rawValue()) ? ctx.newFalse():ctx.newTrue());
				} else {
					ctx.push(ctx.newFalse());
				}
			}
		});

	}

	@Override
	public MobInteger newInstance(Integer mob) {
		return new MobInteger(this, mob);
	}

}
