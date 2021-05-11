package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobIntegerClass extends MobPrimitiveClass<Integer> {

	public MobIntegerClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}

	public void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("+") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newInteger(r.rawValue()+arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()+arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newInteger(r.rawValue()-arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()-arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newInteger(r.rawValue()*arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()*arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newInteger(r.rawValue()/arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()/arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("negated") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement r = (MobInteger) receiver;
				ctx.returnElement(ctx.newInteger(((MobInteger)r).rawValue()*-1));
			}
		});
		this.addMethod(new MobMethod("<") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue() < arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(r.rawValue() < arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue() > arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(r.rawValue() > arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue() >= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(r.rawValue() >= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("<=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue() <= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(r.rawValue() <= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue() == arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement((float)r.rawValue() == (float)arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("~=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobInteger r = (MobInteger) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue().equals(arg.rawValue()) ? ctx.newFalse():ctx.newTrue());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement((float)r.rawValue() == (float)arg.rawValue() ? ctx.newFalse():ctx.newTrue());
				}
			}
		});

	}

	@Override
	public MobInteger newInstance(Integer mob) {
		return new MobInteger(this, mob);
	}
}
