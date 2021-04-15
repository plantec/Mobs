package mob.sinterpreter;

import java.util.HashMap;

import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;

public class MobContext {
	private MobContext parent;
	private HashMap<String, MobVariable> variables;
	
	public MobContext(MobContext parent) {
		this.parent = parent;
		this.variables = new HashMap<>();
	}

	public MobContext parent() {
		return this.parent;
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
		return this.variables.get(name);
	}

	public MobVariable lookupVariableByName(String name) {
		MobVariable v = this.getVariableByName(name);
		if (v != null)
			return v;
		return this.parent.lookupVariableByName(name);
	}
	
	public void clear() {
		this.variables.clear();
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

	public MobSymbol newSymbol(String p) {
		return this.parent.newSymbol(p);
	}

	public MobNil newNil() {
		return this.parent.newNil();
	}


}
