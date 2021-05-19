package mob.model;

import java.util.Arrays;

import mob.ast.MobAstElement;
import mob.ast.MobEntity;
import mob.ast.MobInterpretableVisitor;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobReturnExecuted;

public class MobObject extends MobEntity implements MobAstElement, MobMethodRunner {
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
	
	public boolean isKindOf(MobClass type) {
		return this.definition() == type;
	}

	public void instVarAtPut(Integer pos, Object val) {
		this.checkCapacity(pos+1);
		this.values[pos] = val;
	}
	
	public Object instVarAt(Integer pos) {
		this.checkCapacity(pos+1);
		return this.values[pos];
	}
	
	public Object rawValue() {
		return this.instVarAt(0);
	}
	
	public Object [] values() {
		return this.values;
	}
	
	public Object rawValueAt(int pos) {
		return this.instVarAt(pos);
	}
	
	public int valuesCapacity() {
		return this.values.length;
	}
	
	public void checkCapacity(int capacity) {
		if (this.valuesCapacity() < capacity)
			this.values = Arrays.copyOf(this.values, capacity);
	}
	
	public void runFromObject(MobContext ctx, String signature, Boolean superflag) {
		try {
			this.definition.run(ctx, this, signature, superflag);
			ctx.push(this);
		} catch (MobReturnExecuted e1) {
			// nothing to do : the returned value is already on top of the stack
		}				
	}
	
	public void lookupAndRun(MobContext ctx, String signature) {
		this.runFromObject(ctx, signature, false);
	}
	
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitObject(this);
	}
	
}
