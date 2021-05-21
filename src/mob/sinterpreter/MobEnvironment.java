package mob.sinterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobObject;
import mob.model.primitives.MobCharacter;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;
import mob.model.primitives.MobUnit;

public class MobEnvironment {
	private Map<String, MobClass> classes;
		
	public MobEnvironment() {
		this.classes = new HashMap<>();
		MobMetaClass mobMetaClass = new MobMetaClass(null, this, null, null);
		MobMetaClass mobMetaClassClass = new MobMetaClass(mobMetaClass, this, null, mobMetaClass);
		mobMetaClass.setClass(mobMetaClassClass);
		this.recordClass(mobMetaClass);
		
		MobClass object = new MobClass("Object", this, null, null);
		MobClass objectClass = new MobMetaClass(object, this, null, mobMetaClass);
		object.setClass(objectClass);
		this.recordClass(object);
		
		MobClass behavior = new MobClass("Behavior", this, object, null);
		MobMetaClass behaviorClass = new MobMetaClass(behavior, this, objectClass, mobMetaClass);
		behavior.setClass(behaviorClass);
		this.recordClass(behavior);
				
		mobMetaClassClass.setSuperclass(behaviorClass);
		mobMetaClass.setSuperclass(behavior);
		
		MobClass mobClass = new MobClass("Class", this, behavior, null);
		MobMetaClass mobClassClass = new MobMetaClass(mobClass, this, behaviorClass, mobMetaClass);
		mobClass.setClass(mobClassClass);
		this.recordClass(mobClass);
		
		objectClass.setSuperclass(mobClass);
		
		this.recordClass(new MobFloat("Float", this, object, null));
		MobMetaClass floatClassClass = new MobMetaClass(this.getClassByName("Float"), this, objectClass, mobMetaClass);
		this.getClassByName("Float").setClass(floatClassClass);
		
		this.recordClass(new MobInteger("Integer", this, object, null));
		MobMetaClass integerClassClass = new MobMetaClass(this.getClassByName("Integer"), this, objectClass, mobMetaClass);
		this.getClassByName("Integer").setClass(integerClassClass);
		
		this.recordClass(new MobCharacter("Character", this, object, null));
		MobMetaClass characterClassClass = new MobMetaClass(this.getClassByName("Character"), this, objectClass, mobMetaClass);
		this.getClassByName("Character").setClass(characterClassClass);
		
		this.recordClass(new MobString("String", this, object, null));
		MobMetaClass stringClassClass = new MobMetaClass(this.getClassByName("String"), this, objectClass, mobMetaClass);
		this.getClassByName("String").setClass(stringClassClass);
		
		this.recordClass(new MobSymbol("Symbol", this, object, null));
		MobMetaClass symbolClassClass = new MobMetaClass(this.getClassByName("Symbol"), this, objectClass, mobMetaClass);
		this.getClassByName("Symbol").setClass(symbolClassClass);		
		
		this.recordClass(new MobUnit("Unit", this, object, null));
		MobMetaClass unitClassClass = new MobMetaClass(this.getClassByName("Unit"), this, objectClass, mobMetaClass);
		this.getClassByName("Unit").setClass(unitClassClass);
		
		this.recordClass(new MobFalse("Boolean", this, object, null));
		MobMetaClass booleanClassClass = new MobMetaClass(this.getClassByName("Boolean"), this, objectClass, mobMetaClass);
		this.getClassByName("Boolean").setClass(booleanClassClass);
		
		this.recordClass(new MobFalse("False", this, this.getClassByName("Boolean"), null));
		MobMetaClass falseClassClass = new MobMetaClass(this.getClassByName("False"), this, objectClass, mobMetaClass);
		this.getClassByName("False").setClass(falseClassClass);
		
		this.recordClass(new MobTrue("True", this, this.getClassByName("Boolean"), null));
		MobMetaClass trueClassClass = new MobMetaClass(this.getClassByName("True"), this, objectClass, mobMetaClass);
		this.getClassByName("True").setClass(trueClassClass);
		
		this.recordClass(new MobClass("UndefinedObject", this, object, null));
		MobMetaClass undefinedObjectClassClass = new MobMetaClass(this.getClassByName("UndefinedObject"), this, objectClass, mobMetaClass);
		this.getClassByName("UndefinedObject").setClass(undefinedObjectClassClass);
		
		this.recordClass(new MobSequence("Sequence", this, object, null));
		MobMetaClass sequenceClassClass = new MobMetaClass(this.getClassByName("Sequence"), this, objectClass, mobMetaClass);
		this.getClassByName("Sequence").setClass(sequenceClassClass);
	}
	
	
	public void recordClass(MobClass clazz) {
		this.classes.put(clazz.name(), clazz);
	}
	
	public MobClass getClassByName(String name) {
		return this.classes.get(name);
	}

	private MobFloat floatClass() { return (MobFloat) this.getClassByName("Float"); }
	private MobInteger integerClass() { return (MobInteger) this.getClassByName("Integer"); }
	private MobCharacter characterClass() { return (MobCharacter) this.getClassByName("Character"); }
	private MobString stringClass() { return (MobString) this.getClassByName("String"); }
	private MobSymbol symbolClass() { return (MobSymbol) this.getClassByName("Symbol"); }
	private MobUnit unitClass() { return (MobUnit) this.getClassByName("Unit"); }
	private MobSequence sequenceClass() { return (MobSequence) this.getClassByName("Sequence"); }

	
	public MobObject newFloat(Float p) {
		MobObject o = this.floatClass().newInstance();
		o.primValueAtPut(0, p);
		return o;
	}

	public MobObject newInteger(Integer p) {
		MobObject o = this.integerClass().newInstance();
		o.primValueAtPut(0, p);
		return o;
	}

	public MobObject newCharacter(Character p) {
		MobObject o = this.characterClass().newInstance();
		o.primValueAtPut(0, p);
		return o;
	}

	public MobObject newString(String p) {
		MobObject o = this.stringClass().newInstance();
		o.primValueAtPut(0, p);
		return o;
	}

	public MobObject newSymbol(String p) {
		MobObject o = this.symbolClass().newInstance();
		o.primValueAtPut(0, p);
		return o;
	}

	public MobObject newUnit() {
		MobObject unit = this.unitClass().newInstance();
		MobUnit unitCls = (MobUnit) unit.definition();
		unitCls.initParameters(unit);
		return unit;
	}
	
	public MobObject newSequence(List<MobAstElement> contents) {
		MobObject seq = this.sequenceClass().newInstance();
		for (MobAstElement e : contents) {
			seq.addPrimValue((Object)e);
		}
		return seq;
	}
}
