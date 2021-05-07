package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobEntity;
import mob.sinterpreter.MobContext;

public class MobObject extends MobEntity implements MobAstElement {
	private MobObjectClass definition;

	public MobObject(MobObjectClass definition) {
		this.definition = definition;
	}

	public MobObjectClass definition() {
		return this.definition; 
	}
	
	public void setDefinition(MobObjectClass definition) {
		this.definition = definition;
	}

	public void run(MobContext ctx, String signature) {
		this.definition.run(ctx, this, signature);
	}

}
