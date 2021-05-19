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
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()+(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()+(Float)arg1.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()-(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()-(Float)arg1.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()*(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()*(Float)arg1.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newInteger((Integer)r.rawValue()/(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Integer)r.rawValue()/(Float)arg1.rawValue()));
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
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Integer)r.rawValue() < (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.rawValue() < (Float)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Integer)r.rawValue() > (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.rawValue() > (Float)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Integer)r.rawValue() >= (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.rawValue() >= (Float)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("<=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Integer)r.rawValue() <= (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.rawValue() <= (Float)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(r.rawValue().equals(arg1.rawValue()) ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement(r.rawValue().equals(arg1.rawValue()) ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("~=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(r.rawValue().equals(arg1.rawValue()) ? ctx.newFalse():ctx.newTrue());
				} else {
					ctx.returnElement(r.rawValue().equals(arg1.rawValue()) ? ctx.newTrue():ctx.newFalse());
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
