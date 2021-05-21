package mob.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mob.ast.MobAstElement;
import mob.ast.MobEntity;
import mob.ast.MobInterpretableVisitor;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobReturnExecuted;

public class MobObject extends MobEntity implements MobAstElement, MobMethodRunner {
	private MobClass definition;
	private Object[] primValues;
	private Map<String, MobObject> slotValues;

	public MobObject(MobClass definition) {
		this.definition = definition;
		this.primValues = new Object[0];
		this.slotValues = new HashMap<>();
	}

	public Map<String, MobObject> slotValues() {
		return this.slotValues;
	}

	public MobClass definition() {
		return this.definition;
	}

	public MobEnvironment environment() {
		return this.definition.environment();
	}

	public void setClass(MobClass definition) {
		this.definition = definition;
	}

	public boolean isKindOf(MobBehavior mobBehavior) {
		return this.definition() == mobBehavior || this.definition().inheritsFrom(mobBehavior);
	}

	public void setSlot(String name, MobObject val) {
		this.slotValues.put(name, val);
	}

	public MobObject getSlot(String name) {
		return this.slotValues.get(name);
	}

	public Object primValueAt(Integer pos) {
		this.checkPrimCapacity(pos + 1);
		return this.primValues[pos];
	}

	public Object primValue() {
		return this.primValueAt(0);
	}

	public Object [] allPrimValues() {
		return this.primValues;
	}

	public void setPrimValue(Object value) {
		this.primValueAtPut(0, value);
	}

	public void primValueAtPut(Integer pos, Object value) {
		this.checkPrimCapacity(pos + 1);
		this.primValues[pos] = value;
	}

	public int primCapacity() {
		return this.primValues.length;
	}

	public void addPrimValue(Object e) {
		this.checkPrimCapacity(this.primCapacity() + 1);
		this.primValues[this.primCapacity() - 1] = e;
	}

	public void checkPrimCapacity(int capacity) {
		if (this.primCapacity() < capacity)
			this.primValues = Arrays.copyOf(this.primValues, capacity);
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

	@Override
	public int hashCode() {
		if (this.definition == null || this.definition.name() == null)
			return this.slotValues.hashCode();
		return this.definition.name().hashCode();
	}
 
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MobObject))
			return false;
		MobObject other = (MobObject) o;
		return this.equals(other);
	}

	public boolean equals(MobObject o) {
		if (this.definition == null || o.definition == null)
			return false;
		return this.definition.receiverEquals(this, o);
	}
	
	public boolean receiverEquals(MobObject receiver, MobObject other) {
		return receiver==other;
	}

}
