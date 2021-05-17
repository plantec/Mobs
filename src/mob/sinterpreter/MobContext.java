package mob.sinterpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.primitives.MobCharacter;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;
import mob.model.primitives.MobUnit;

public class MobContext {
	private MobContext parent;
	private HashMap<String, MobVariable> variables;
	private List<MobVariable> parameters;
	MobUnit unit;
	MobObject receiver;

	public MobContext(MobContext parent) {
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
		this.addVariable(new MobVariable("self", this.receiver));
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
			
			int pos = this.positionOfSlot(name);
			if (pos > -1 ) {
				return new MobSlotData(this.receiver, pos);
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

	public MobFalse newFalse() {
		return this.parent.newFalse();
	}

	public MobTrue newTrue() {
		return this.parent.newTrue();
	}

	public MobFloat newFloat(Float p) {
		return this.parent.newFloat(p);
	}

	public MobInteger newInteger(Integer p) {
		return this.parent.newInteger(p);
	}

	public MobString newString(String p) {
		return this.parent.newString(p);
	}

	public MobCharacter newCharacter(Character p) {
		return this.parent.newCharacter(p);
	}

	public MobSymbol newSymbol(String p) {
		return this.parent.newSymbol(p);
	}

	public MobObject newNil() {
		return this.parent.newNil();
	}

}
