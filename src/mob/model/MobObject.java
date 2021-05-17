package mob.model;

import mob.ast.MobAstElement;
import mob.ast.MobEntity;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;

public class MobObject extends MobEntity implements MobAstElement {
	private MobEnvironment environment;
	private MobClass definition;
	private Object [] values;

	public MobObject(MobEnvironment environment, MobClass definition) {
		this.environment = environment;
		this.definition = definition;
		if (this.definition != null)
			this.values = new Object [this.definition.numberOfSlots()];
		else 
			this.values = new Object [0];
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

	public void instVarAtPut(Integer pos, Object val) {
		this.values[pos] = val;
	}
	public Object instVarAt(Integer pos) {
		return this.values[pos];
	}
	
	public void run(MobContext ctx, String signature) {
		this.definition.run(ctx, this, signature);
	}
	
}
