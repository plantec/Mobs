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
					ctx.returnElement(ctx.newFloat((Float)r.primValue()+(Integer)arg1.primValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.primValue()+(Float)arg1.primValue()));
				}
			}
		});
		this.addMethod(new MobMethod("-") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newFloat((Float)r.primValue()-(Integer)arg1.primValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.primValue()-(Float)arg1.primValue()));
				}
			}
		});
		this.addMethod(new MobMethod("*") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newFloat((Float)r.primValue()*(Integer)arg1.primValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.primValue()*(Float)arg1.primValue()));
				}
			}
		});
		this.addMethod(new MobMethod("/") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(ctx.newFloat((Float)r.primValue()/(Integer)arg1.primValue()));
				} else {
					ctx.returnElement(ctx.newFloat((Float)r.primValue()/(Float)arg1.primValue()));
				}
			}
		});
		
		this.addMethod(new MobMethod("negated") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject r = (MobObject) receiver;
				ctx.returnElement(ctx.newFloat((Float)r.primValue()*-1));
			}
		});
		
		this.addMethod(new MobMethod("<") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Float)r.primValue() < (Integer)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.primValue() < (Float)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Float)r.primValue() > (Integer)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.primValue() > (Float)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod(">=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Float)r.primValue() >= (Integer)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.primValue() >= (Float)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("<=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement((Float)r.primValue() <= (Integer)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement((Float)r.primValue() <= (Float)arg1.primValue() ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(r.primValue().equals(arg1.primValue()) ? ctx.newTrue():ctx.newFalse());
				} else {
					ctx.returnElement(r.primValue().equals(arg1.primValue()) ? ctx.newTrue():ctx.newFalse());
				}
			}
		});
		this.addMethod(new MobMethod("~=") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject r = (MobObject) receiver;
				MobClass type = ctx.environment().getClassByName("Integer");
				if (arg1.isKindOf(type)) {
					ctx.returnElement(r.primValue().equals(arg1.primValue()) ? ctx.newFalse():ctx.newTrue());
				} else {
					ctx.returnElement(r.primValue().equals(arg1.primValue()) ? ctx.newFalse():ctx.newTrue());
				}
			}
		});

	}
}
