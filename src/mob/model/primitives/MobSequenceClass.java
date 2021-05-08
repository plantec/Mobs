package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobMethod;

public class MobSequenceClass extends MobClass {
	public MobSequenceClass(MobClass def) {
		super(def);
		this.addMethod(new MobMethod("add:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobAstElement obj = ctx.pop();
				seq.add(obj);
				ctx.push(seq);
			}
		});
		this.addMethod(new MobMethod("at:put:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobAstElement obj = ctx.pop();
				MobInteger pos = (MobInteger) ctx.pop();
				seq.set(pos.rawValue(), obj);
				ctx.push(seq);
			}
		});
		
		this.addMethod(new MobMethod("at:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobInteger arg = (MobInteger) ctx.pop();
				ctx.push(seq.get(arg.rawValue()));
			}
		});
	}

	public MobUnit newInstance() {
		return new MobUnit(this);
	}
}
