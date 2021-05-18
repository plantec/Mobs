package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;
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
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				if (arg1.definition() instanceof MobIntegerClass) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()+(Integer)arg1.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()+arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				if (arg1.definition() instanceof MobIntegerClass) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()-(Integer)arg1.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()-arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				if (arg1.definition() instanceof MobIntegerClass) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()*(Integer)arg1.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()*arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				if (arg1.definition() instanceof MobIntegerClass) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()/(Integer)arg1.rawValue()));
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()/arg.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("negated") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject r = (MobObject) receiver;
				ctx.returnElement(ctx.newInteger((Integer)r.rawValue()*-1));
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
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				if (arg1.definition() instanceof MobIntegerClass) {
					ctx.newTrue();
					ctx.returnElement((Integer)r.rawValue() > (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement((Integer)r.rawValue() > arg.rawValue() ? ctx.newTrue():ctx.newFalse());
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
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				if (arg1.definition() instanceof MobIntegerClass) {
					ctx.returnElement(r.rawValue().equals(arg1.rawValue()) ? ctx.newTrue():ctx.newFalse());
				} else {
					MobFloat arg = (MobFloat) arg1;
					ctx.returnElement(r.rawValue().equals(arg.rawValue()) ? ctx.newTrue():ctx.newFalse());
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
	public MobObject newInstance(Integer mob) {
		MobObject i = new MobObject(this);
		i.instVarAtPut(0, mob);
		return i;
	}
}
