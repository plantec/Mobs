package mob.model.primitives;

import java.util.List;

import mob.ast.MobAstElement;
import mob.model.MobEntity;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobVariable;

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
	
	public List<MobVariable> slots() {
		return null;
	}

	public void setDefinition(MobClass definition) {
		this.definition = definition;
	}

	public void run(MobContext ctx, String signature) {
		this.definition.run(ctx, this, signature);
	}

}
