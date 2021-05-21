package mob.model;

import mob.sinterpreter.MobEnvironment;


/*
 * Class represents the common behaviour of all classes. 
 * It provides a class name, compilation methods, method storage, and instance variables. 
 * It provides a concrete representation for class variable names and shared pool variables 
 * (addClassVarName:, addSharedPool:, initialize). 
 * Class knows how to create instances, so all metaclasses should inherit ultimately from Class.
 */
public class MobClass extends MobBehavior {
	private String name;

	public String name() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public MobClass(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(environment, superclass, def);
		this.setName(name);
	}
	
	public MobObject newInstance() {
		return new MobObject(this);
	}	

}
