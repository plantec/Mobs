package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobSequenceClass extends MobObjectClass {
	
	public MobSequenceClass(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}

	public void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("add:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobAstElement obj = ctx.pop();
				seq.add(obj);
			}
		});
		this.addMethod(new MobMethod("at:put:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobAstElement obj = ctx.pop();
				MobObject pos = (MobObject) ctx.pop();
				seq.set((Integer)pos.rawValue(), obj);
			}
		});
		
		this.addMethod(new MobMethod("at:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobObject pos = (MobObject) ctx.pop();
				ctx.returnElement(seq.get((Integer)pos.rawValue()));
			}
		});
	}

	public MobSequence newInstance() {
		return new MobSequence(this);
	}
}
