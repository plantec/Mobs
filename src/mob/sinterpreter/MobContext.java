package mob.sinterpreter;

import java.util.HashMap;

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
		if (this.parent != null)
			return this.parent.lookupVariableByName(name);
		else
			return null;
	}
	
	public void clear() {
		this.variables.clear();
	}

}
