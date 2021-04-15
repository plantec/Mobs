package mob.model;
import java.util.HashMap;

import stree.parser.SNode;

public abstract class MobEntityDef {
	private HashMap<String, MobMethod> behavior;
	
	public MobEntityDef() {
		behavior = new HashMap<String, MobMethod>();
	}
	
	public void addMethod (MobMethod definition) {
		behavior.put(definition.name(), definition);
	}
	public MobMethod methodNamed(String name) {
		return behavior.get(name);
	}
	
	public abstract MobEntity newInstance();
	public void run(MobExp receiver, SNode node) {
		
	}
	
}
