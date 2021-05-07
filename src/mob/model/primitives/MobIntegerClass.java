package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobMethod;

public class MobIntegerClass extends MobPrimitiveClass<Integer> {

	public MobIntegerClass(MobObjectClass def) {
		super(def);
		this.addMethod(new MobMethod("+") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement r = (MobInteger) receiver;
				ctx.push(ctx.newInteger(((MobInteger)r).rawValue()*-1));
			}
		});
		this.addMethod(new MobMethod("<") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
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
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue() == arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push((float)r.rawValue() == (float)arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("~=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.push(r.rawValue().equals(arg.rawValue()) ? ctx.newFalse():ctx.newTrue());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.push((float)r.rawValue() == (float)arg.rawValue() ? ctx.newFalse():ctx.newTrue());
				}
			}
		});

	}

	@Override
	public MobInteger newInstance(Integer mob) {
		return new MobInteger(this, mob);
	}
}
