package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobFloatClass extends MobObjectClass {

	public MobFloatClass(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}
	
	public void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("+") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()+(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()+(Float)arg1.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()-(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()-(Float)arg1.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()*(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()*(Float)arg1.rawValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()/(Integer)arg1.rawValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.rawValue()/(Float)arg1.rawValue()));
				}
			}
		});
		
		this.addMethod(new MobMethod("negated") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject r = (MobObject) receiver;
				ctx.returnElement(ctx.newFloat((Float)r.rawValue()*-1));
			}
		});
		
		this.addMethod(new MobMethod("<") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Float)r.rawValue() < (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
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
					ctx.returnElement((Float)r.rawValue() > (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
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
					ctx.returnElement((Float)r.rawValue() >= (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
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
					ctx.returnElement((Float)r.rawValue() <= (Integer)arg1.rawValue() ? ctx.newTrue():ctx.newFalse());
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
					ctx.returnElement(r.rawValue().equals(arg1.rawValue()) ? ctx.newFalse():ctx.newTrue());
				}
			}
		});

	}
}
