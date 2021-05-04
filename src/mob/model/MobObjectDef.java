package mob.model;
import java.util.HashMap;

import mob.sinterpreter.MobContext;

public class MobObjectDef {
	private HashMap<String, MobMethod> behavior;
	
	public MobObjectDef() {
		behavior = new HashMap<String, MobMethod>();
		this.addMethod(new MobMethod("println") {
			public void run(MobContext ctx, MobEntity receiver) {
				System.out.println(receiver.mobString());
			}
		});
	}
	
	public void addMethod (MobMethod definition) {
		behavior.put(definition.selector(), definition);
	}
	
	public MobMethod methodNamed(String name) {
		return behavior.get(name);
	}
	
	public MobObject newInstance() {
		return new MobObject(this);
	}
		
}
