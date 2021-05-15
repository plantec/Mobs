package mob.model;

import mob.ast.MobAstElement;
import mob.ast.MobEntity;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;

public class MobObject extends MobEntity implements MobAstElement {
	private MobEnvironment environment;
	private MobClass definition;

	public MobObject(MobEnvironment environment, MobClass definition) {
		this.environment = environment;
		this.definition = definition;
	}

	public MobObject(MobClass definition) {
		this(definition.environment(), definition);
	}

	public MobClass definition() {
		return this.definition; 
	}
	
	public MobEnvironment environment() {
		return this.environment;
	}
	
	public void setClass(MobClass definition) {
		this.definition = definition;
	}

	public void run(MobContext ctx, String signature) {
		this.definition.run(ctx, this, signature);
	}
	
}
