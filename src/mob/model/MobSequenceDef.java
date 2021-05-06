package mob.model;

import mob.model.primitives.MobInteger;
import mob.sinterpreter.MobContext;

public class MobSequenceDef extends MobObjectDef {
	public MobSequenceDef() {
		this.addMethod(new MobMethod("add:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobEntity obj = ctx.pop();
				seq.add(obj);
				ctx.push(seq);
			}
		});
		this.addMethod(new MobMethod("at:put:") {
			public void run(MobContext ctx, MobEntity receiver) {
				MobSequence seq = (MobSequence) receiver;
				MobEntity obj = ctx.pop();
				MobInteger pos = (MobInteger) ctx.pop();
				seq.set(pos.rawValue(), obj);
				ctx.push(seq);
			}
		});
		
		this.addMethod(new MobMethod("at:") {
			public void run(MobContext ctx, MobEntity receiver) {
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
