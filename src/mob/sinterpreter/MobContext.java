package mob.sinterpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mob.ast.MobAstElement;
import mob.model.MobObject;
import mob.model.primitives.MobUnit;

public class MobContext {
	private MobContext parent;
	private HashMap<String, MobVariable> variables;
	private List<MobVariable> parameters;
	MobUnit unit;
	MobObject receiver;

	public MobContext(MobContext parent) {
		if (parent != null && parent == this.parent) throw new Error("Circular context");
		this.parent = parent;
		this.variables = new HashMap<>();
		this.parameters = new ArrayList<>();
	}

	public void setUnit(MobUnit unit) {
		this.unit = unit;
		List<String> pl = unit.parameters();
		for (int i = 0; i < pl.size(); i++) {
			this.parameters.add(new MobVariable(pl.get(i)));
		}
	}

	public void setReceiver(MobObject receiver) {
		this.receiver = receiver;
	}

	public void setParameterValue(int idx, MobAstElement value) {
		this.parameters.get(idx).setValue(value);
	}

	public MobContext parent() {
		return this.parent;
	}

	public void resetStack() {
		this.parent.resetStack();
	}
	
	public MobInterpreter interpreter() {
		return this.parent().interpreter();
	}

	public void setParent(MobContext parent) {
		this.parent = parent;
	}

	public Boolean containsVariableNamed(String name) {
		return this.variables.get(name) != null;
	}

	public Boolean addVariable(MobVariable var) {
		if (this.variables.get(var.name()) != null) {
			return false;
		}
		this.variables.put(var.name(), var);
		return true;
	}

	public MobVariable getVariableByName(String name) {
		MobVariable v = this.variables.get(name);
		if (v != null)
			return v;
		for (MobVariable p : this.parameters)
			if (p.name().equals(name))
				return p;
		return null;
	}

	public MobDataAccess lookupNamedData(String name) {
		MobVariable v = this.getVariableByName(name);
		if (v != null)
			return v;
		if (this.receiver != null) {
			if (name.equals("self") || name.equals("super")) {
				return new MobPseudoVariable(name, this.receiver);
			}
			int pos = this.positionOfSlot(name);
			if (pos > -1 ) {
				return new MobSlotData(this.receiver, name);
			}
		}
		if (this.parent == null)
			return null;
		return this.parent.lookupNamedData(name);
	}

	public int positionOfSlot(String name) {
		if (this.receiver == null) return -1;
		return this.receiver.definition().positionOfSlot(name);
	}

	public void clear() {
		this.variables.clear();
	}

	public List<MobAstElement> result() {
		return this.parent.result();
	}

	public void push(MobAstElement exp) {
		this.parent.push(exp);
	}

	public void returnElement(MobAstElement exp) {
		this.push(exp);
		throw new MobReturnExecuted();
	}

	public MobAstElement pop() {
		return this.parent.pop();
	}

	public MobEnvironment environment() {
		return this.parent.environment();
	}

	public MobObject newFalse() {
		return this.parent.newFalse();
	}

	public MobObject newTrue() {
		return this.parent.newTrue();
	}

	public MobObject newFloat(Float p) {
		return this.parent.newFloat(p);
	}

	public MobObject newInteger(Integer p) {
		return this.parent.newInteger(p);
	}

	public MobObject newString(String p) {
		return this.parent.newString(p);
	}

	public MobObject newCharacter(Character p) {
		return this.parent.newCharacter(p);
	}

	public MobObject newSymbol(String p) {
		return this.parent.newSymbol(p);
	}

	public MobObject newNil() {
		return this.parent.newNil();
	}

}
