package mob.model.primitives;

import mob.ast.MobAstElement;
import mob.model.MobEntity;
import mob.sinterpreter.MobContext;

public class MobObject extends MobEntity implements MobAstElement {
	private MobClass definition;

	public MobObject(MobClass definition) {
		this.definition = definition;
	}

	public MobClass definition() {
		return this.definition; 
	}
	
	public void setDefinition(MobClass definition) {
		this.definition = definition;
	}

	public void run(MobContext ctx, String signature) {
		this.definition.run(ctx, this, signature);
	}

}
