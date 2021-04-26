package mob.model;
import java.util.HashMap;

import stree.parser.SNode;

public class MobObjectDef {
	private HashMap<String, MobMethod> behavior;
	
	public MobObjectDef() {
		behavior = new HashMap<String, MobMethod>();
	}
	
	public void addMethod (MobMethod definition) {
		behavior.put(definition.name(), definition);
	}
	public MobMethod methodNamed(String name) {
		return behavior.get(name);
	}
	
	public MobObject newInstance() {
		return new MobObject(this);
	}
	
	public void run(MobObject receiver, SNode node) {
		
	}
	
}
