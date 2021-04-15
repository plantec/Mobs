package mob.model;
import stree.parser.SNode;

public class MobMethod {
	SNode definition;
	
	public MobMethod(SNode definition) {
		this.definition = definition;
	}
	
	public String name () {
		return definition.get(0).parsedString();
	}
}
