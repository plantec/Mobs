package mob.model;

import java.util.Arrays;

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
		this.values = new Object [0];
	}
	
	protected void setDefinition(MobClass definition) {
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

	public void instVarAtPut(Integer pos, Object val) {
		this.checkCapacity(pos+1);
		this.values[pos] = val;
	}
	public Object instVarAt(Integer pos) {
		this.checkCapacity(pos+1);
		return this.values[pos];
	}
	
	public int valuesCapacity() {
		return this.values.length;
	}
	
	public void checkCapacity(int capacity) {
		if (this.valuesCapacity() < capacity)
			this.values = Arrays.copyOf(this.values, capacity);
	}
	
	public void run(MobContext ctx, String signature) {
		this.definition.run(ctx, this, signature);
	}
	
}
