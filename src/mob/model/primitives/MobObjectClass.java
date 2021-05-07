package mob.model.primitives;

import java.util.HashMap;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobMethod;
import mob.sinterpreter.MobObjectMethod;

public class MobObjectClass extends MobObject {
	private HashMap<String, MobMethod> methodDict;

	public MobObjectClass(MobObjectClass def) {
		super(def);
		methodDict = new HashMap<String, MobMethod>();
		this.addMethod(new MobMethod("println") {
			public void run(MobContext ctx, MobAstElement receiver) {
				System.out.println(receiver);
				ctx.push(receiver);
			}
		});
		this.addMethod(new MobMethod("method:named:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobString name = (MobString) ctx.pop();
				MobUnit code = (MobUnit) ctx.pop();
				MobObject self = (MobObject) receiver;
				MobObjectClass selfDef = self.definition();
				selfDef.addMethod(new MobObjectMethod(name.rawValue(), code));
				ctx.push(receiver);
			}
		});
		this.addMethod(new MobMethod("definition") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject self = (MobObject) receiver;
				MobObjectClass selfDef = self.definition();
				ctx.push(selfDef);
			}
		});
	}

	public void addMethod(MobMethod definition) {
		methodDict.put(definition.selector(), definition);
	}

	public MobMethod methodNamed(String signature) {
		return methodDict.get(signature);
	}

	public MobObject newInstance() {
		return new MobObject(this);
	}
	
	public void run(MobContext ctx, MobObject receiver, String signature) {
		MobMethod m = this.methodNamed(signature);
		if (m == null)
			throw new Error(receiver.toString() + " does not understand '" + signature + "'");
		m.run(ctx, receiver);
	}

}
