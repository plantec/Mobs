package mob.model;

import java.lang.reflect.InvocationTargetException;

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
	private MobObject thisClass;

	public MobMetaClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
		//this.newInstance();
	}

	protected void initializePrimitives() {
		super.initializePrimitives();
	}
	
	public MobObject newInstance() {
		if (thisClass != this) {
			try {
				thisClass = this.getClass().getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return thisClass;
		}
		throw new Error("A Metaclass should only have one instance!");
	}
	
	public MobClass soleInstance() {
		return (MobClass) thisClass;
	}


}
