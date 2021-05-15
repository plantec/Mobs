package mob.model;

import mob.sinterpreter.MobEnvironment;
/*
 * ClassDescription is an abstract class that provides facilities needed by its two direct subclasses, Class and Metaclass. 
 * ClassDescription adds a number of facilities to the basis provided by Behavior: 
 * named instance variables, 
 * the categorization of methods into protocols, 
 * the notion of a name (abstract), 
 * the maintenance of change sets and the logging of changes, 
 * and most of the mechanisms needed for filing-out changes.
 */
public class MobClassDescription extends MobBehavior {
	private String name;

	public MobClassDescription(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(environment, superclass, def);
		this.name = name;
		this.initializePrimitives();
	}
	protected void initializePrimitives() {
		super.initializePrimitives();
	}

	public String name() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
