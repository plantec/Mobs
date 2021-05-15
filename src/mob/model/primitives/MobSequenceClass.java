package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobSequenceClass extends MobObjectClass {
	
	public MobSequenceClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
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
				MobInteger pos = (MobInteger) ctx.pop();
				seq.set(pos.rawValue(), obj);
			}
		});
		
		this.addMethod(new MobMethod("at:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobInteger arg = (MobInteger) ctx.pop();
				ctx.returnElement(seq.get(arg.rawValue()));
			}
		});
	}

	public MobSequence newInstance() {
		return new MobSequence(this);
	}
}
