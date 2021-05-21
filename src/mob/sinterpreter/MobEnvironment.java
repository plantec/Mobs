package mob.sinterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.model.primitives.MobCharacterClass;
import mob.model.primitives.MobFalseClass;
import mob.model.primitives.MobFloatClass;
import mob.model.primitives.MobIntegerClass;
import mob.model.primitives.MobSequenceClass;
import mob.model.primitives.MobStringClass;
import mob.model.primitives.MobSymbolClass;
import mob.model.primitives.MobTrueClass;
import mob.model.primitives.MobUnitClass;

public class MobEnvironment {
	private Map<String, MobClass> classes;
		
	public MobEnvironment() {
		classes = new HashMap<>();
		MobClass mobMetaClass = new MobClass("MetaClass", this, null, null);
		MobMetaClass mobMetaClassClass = new MobMetaClass(mobMetaClass, this, null, mobMetaClass);
		mobMetaClassClass.setClass(mobMetaClass);
		mobMetaClass.setClass(mobMetaClassClass);
		this.recordClass(mobMetaClass);
		
		MobClass objectClass = new MobObjectClass(null, this, null, mobMetaClass);
		MobClass object = new MobClass("Object", this, null, objectClass);
		this.recordClass(object);
		
		MobMetaClass behaviorClass = new MobMetaClass(null, this, objectClass, mobMetaClass);
		MobClass behavior = new MobClass("Behavior", this, object, behaviorClass);
		this.recordClass(behavior);
				
		mobMetaClassClass.setSuperclass(behaviorClass);
		mobMetaClass.setSuperclass(behavior);
		
		MobMetaClass mobClassClass = new MobMetaClass(null, this, behaviorClass, mobMetaClass);
		MobClass mobClass = new MobClass("Class", this, behavior, mobClassClass);
		this.recordClass(mobClass);
		
		objectClass.setSuperclass(mobClass);
		this.recordClass(new MobFloatClass("Float", this, object, null));
		MobMetaClass floatClassClass = new MobMetaClass(this.getClassByName("Float"), this, objectClass, mobMetaClass);
		this.getClassByName("Float").setClass(floatClassClass);
		
		this.recordClass(new MobIntegerClass("Integer", this, object, null));
		MobMetaClass integerClassClass = new MobMetaClass(this.getClassByName("Integer"), this, objectClass, mobMetaClass);
		this.getClassByName("Integer").setClass(integerClassClass);
		
		this.recordClass(new MobCharacterClass("Character", this, object, null));
		MobMetaClass characterClassClass = new MobMetaClass(this.getClassByName("Character"), this, objectClass, mobMetaClass);
		this.getClassByName("Character").setClass(characterClassClass);
		
		this.recordClass(new MobStringClass("String", this, object, null));
		MobMetaClass stringClassClass = new MobMetaClass(this.getClassByName("String"), this, objectClass, mobMetaClass);
		this.getClassByName("String").setClass(stringClassClass);
		
		this.recordClass(new MobSymbolClass("Symbol", this, object, null));
		MobMetaClass symbolClassClass = new MobMetaClass(this.getClassByName("Symbol"), this, objectClass, mobMetaClass);
		this.getClassByName("Symbol").setClass(symbolClassClass);		
		
		this.recordClass(new MobUnitClass("Unit", this, object, null));
		MobMetaClass unitClassClass = new MobMetaClass(this.getClassByName("Unit"), this, objectClass, mobMetaClass);
		this.getClassByName("Unit").setClass(unitClassClass);
		
		this.recordClass(new MobFalseClass("False", this, object, null));
		MobMetaClass falseClassClass = new MobMetaClass(this.getClassByName("False"), this, objectClass, mobMetaClass);
		this.getClassByName("False").setClass(falseClassClass);
		
		this.recordClass(new MobTrueClass("True", this, object, null));
		MobMetaClass trueClassClass = new MobMetaClass(this.getClassByName("True"), this, objectClass, mobMetaClass);
		this.getClassByName("True").setClass(trueClassClass);
		
		this.recordClass(new MobClass("UndefinedObject", this, object, null));
		MobMetaClass undefinedObjectClassClass = new MobMetaClass(this.getClassByName("UndefinedObject"), this, objectClass, mobMetaClass);
		this.getClassByName("UndefinedObject").setClass(undefinedObjectClassClass);
		
		this.recordClass(new MobSequenceClass("Sequence", this, object, null));
		MobMetaClass sequenceClassClass = new MobMetaClass(this.getClassByName("Sequence"), this, objectClass, mobMetaClass);
		this.getClassByName("Sequence").setClass(sequenceClassClass);
	}
	
	
	public void recordClass(MobClass clazz) {
		this.classes.put(clazz.name(), clazz);
	}
	
	public MobClass getClassByName(String name) {
		return this.classes.get(name);
	}

	private MobFloatClass floatClass() { return (MobFloatClass) this.getClassByName("Float"); }
	private MobIntegerClass integerClass() { return (MobIntegerClass) this.getClassByName("Integer"); }
	private MobCharacterClass characterClass() { return (MobCharacterClass) this.getClassByName("Character"); }
	private MobStringClass stringClass() { return (MobStringClass) this.getClassByName("String"); }
	private MobSymbolClass symbolClass() { return (MobSymbolClass) this.getClassByName("Symbol"); }
	private MobUnitClass unitClass() { return (MobUnitClass) this.getClassByName("Unit"); }
	private MobSequenceClass sequenceClass() { return (MobSequenceClass) this.getClassByName("Sequence"); }

	
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
		MobUnitClass unitCls = (MobUnitClass) unit.definition();
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
