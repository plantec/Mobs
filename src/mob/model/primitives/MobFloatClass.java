package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobFloatClass extends MobPrimitiveClass<Float> {

	public MobFloatClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}
	
	public void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("+") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()+arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()+arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()-arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()-arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()*arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()*arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()/arg.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat(r.rawValue()/arg.rawValue()));
				}
			}
		});
		
		this.addMethod(new MobMethod("negated") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobFloat r = (MobFloat) receiver;
				ctx.returnElement(ctx.newFloat(r.rawValue()*-1));
			}
		});
		
		this.addMethod(new MobMethod("<") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
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
				MobFloat r = (MobFloat) receiver;
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
				MobFloat r = (MobFloat) receiver;
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
				MobFloat r = (MobFloat) receiver;
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
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue() == (float)arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(r.rawValue() == (float)arg.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("~=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobFloat r = (MobFloat) receiver;
				if (arg1 instanceof MobInteger) {
					MobInteger arg = (MobInteger) arg1;
					ctx.returnElement(r.rawValue() == (float)arg.rawValue() ? ctx.newFalse():ctx.newTrue());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(r.rawValue() == (float)arg.rawValue() ? ctx.newFalse():ctx.newTrue());
				}
			}
		});

	}
	@Override
	public MobFloat newInstance(Float mob) {
		return new MobFloat(this, mob);
	}
}
