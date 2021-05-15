package mob.sinterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mob.ast.MobAstElement;
import mob.model.MobBehaviorClass;
import mob.model.MobClass;
import mob.model.MobClassClass;
import mob.model.MobClassDescriptionClass;
import mob.model.MobMetaClass;
import mob.model.MobObjectClass;
import mob.model.primitives.MobCharacter;
import mob.model.primitives.MobCharacterClass;
import mob.model.primitives.MobFalseClass;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobFloatClass;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobIntegerClass;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobSequenceClass;
import mob.model.primitives.MobString;
import mob.model.primitives.MobStringClass;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobSymbolClass;
import mob.model.primitives.MobTrueClass;
import mob.model.primitives.MobUnit;
import mob.model.primitives.MobUnitClass;

public class MobEnvironment {
	private Map<String, MobClass> classes;
		
	public MobEnvironment() {
		classes = new HashMap<>();
		MobMetaClass mobMetaClassClass = new MobMetaClass(null, null, this, null);
		MobMetaClass mobMetaClass = new MobMetaClass("MetaClass", null, this, mobMetaClassClass);
		this.recordClass(mobMetaClass);
		mobMetaClassClass.setClass(mobMetaClass);
		
		MobClass objectClass = new MobObjectClass(null, null, this, mobMetaClass);
		MobClass object = new MobClass("Object", null, this, objectClass);
		this.recordClass(object);
		
		MobBehaviorClass behaviorClass = new MobBehaviorClass(null, objectClass, this, mobMetaClass);
		MobClass behavior = new MobClass("Behavior", object, this, behaviorClass);
		this.recordClass(behavior);
		
		MobClassDescriptionClass classDescriptionClass = new MobClassDescriptionClass(null, behaviorClass, this, mobMetaClass);
		MobClass classDescription = new MobClass("ClassDescription", behavior, this, classDescriptionClass);
		this.recordClass(classDescription);
		
		mobMetaClassClass.setSuperclass(classDescriptionClass);
		mobMetaClass.setSuperclass(classDescription);
		
		MobClassClass mobClassClass = new MobClassClass(null, classDescriptionClass, this, mobMetaClass);
		MobClass mobClass = new MobClass("Class", classDescription, this, mobClassClass);
		this.recordClass(mobClass);
		
		objectClass.setSuperclass(mobClass);
		
		this.recordClass(new MobFloatClass("Float", object, this, mobClass));
		this.recordClass(new MobIntegerClass("Integer", object, this, mobClass));
		this.recordClass(new MobCharacterClass("Character", object, this, mobClass));
		this.recordClass(new MobStringClass("String", object, this, mobClass));
		this.recordClass(new MobSymbolClass("Symbol", object, this, mobClass));
		this.recordClass(new MobUnitClass("Unit", object, this, mobClass));
		this.recordClass(new MobFalseClass("False", object, this, mobClass));
		this.recordClass(new MobTrueClass("True", object, this, mobClass));
		this.recordClass(new MobClass("UndefinedObject", object, this, mobClass));
		this.recordClass(new MobSequenceClass("Sequence", object, this, mobClass));
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

	
	public MobFloat newFloat(Float p) {
		return this.floatClass().newInstance(p);
	}

	public MobInteger newInteger(Integer p) {
		return this.integerClass().newInstance(p);
	}

	public MobCharacter newCharacter(Character p) {
		return this.characterClass().newInstance(p);
	}

	public MobString newString(String p) {
		return this.stringClass().newInstance(p);
	}

	public MobSymbol newSymbol(String p) {
		return this.symbolClass().newInstance(p);
	}

	public MobUnit newUnit() {
		return this.unitClass().newInstance();
	}
	
	public MobSequence newSequence(List<MobAstElement> contents) {
		MobSequence seq = this.sequenceClass().newInstance();
		seq.addAll(contents);
		return seq;
	}
}
