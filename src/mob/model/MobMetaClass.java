package mob.model;

import mob.sinterpreter.MobEnvironment;

/*
 * Metaclasses are objects too; they are instances of the class Metaclass. 
 * The instances of class Metaclass are the anonymous metaclasses, each of which has exactly one instance, which is a class.
 * Metaclass represents common metaclass behaviour. 
 * It provides methods for instance creation (subclassOf:) creating initialized instances of the metaclass’s sole instance, 
 * initialization of class variables, metaclass instance, method compilation, and class information (inheritance links, 
 * instance variables, etc.).
 */
public class MobMetaClass extends MobClass {
	private MobClass thisclass;

	public MobMetaClass(MobClass thisclass, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(null, environment, superclass, def);
		this.thisclass = thisclass;
	}
	
	protected void initializePrimitives() {
		super.initializePrimitives();
	}
	
	public MobClass newInstance() {
		throw new Error("A Metaclass should only have one instance!");
	}
	
	public MobClass soleInstance() {
		return (MobClass) thisclass;
	}
	
	public void setSoleInstance(MobClass thisclass) {
		this.thisclass = thisclass;
	}
	
	public String name() {
		if (thisclass == null) return "MetaClass";
		return this.thisclass.name() + " class";
	}
		
}
