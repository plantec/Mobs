package mob.sinterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobProtoObject;
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
		MobMetaClass mobMetaClass = new MobMetaClass("MetaClass", null, this, null);
		mobMetaClass.setDefinition(mobMetaClass);
		this.recordClass(mobMetaClass);
		
		MobMetaClass protoObjectClass = new MobMetaClass("ProtoObject class", null, this, mobMetaClass);
		MobProtoObject protoObject = new MobProtoObject("ProtoObject", null, this, protoObjectClass);
		this.recordClass(protoObject);
		
		mobMetaClass.setSuperclass(protoObject);
		
		protoObject.addSubclass(new MobClass("Class", protoObject, this, mobMetaClass));
		
		protoObjectClass.addSubclass(new MobMetaClass("Object class", protoObjectClass, this, mobMetaClass));
		MobClass objClass = this.getClassByName("Object class");
		protoObject.addSubclass(new MobClass("Object", protoObject, this, objClass));
		
		MobClass obj = this.getClassByName("Object");

		this.recordClass(new MobFloatClass("Float", obj, this, mobMetaClass));
		this.recordClass(new MobIntegerClass("Integer", obj, this, mobMetaClass));
		this.recordClass(new MobCharacterClass("Character", obj, this, mobMetaClass));
		this.recordClass(new MobStringClass("String", obj, this, mobMetaClass));
		this.recordClass(new MobSymbolClass("Symbol", obj, this, mobMetaClass));
		this.recordClass(new MobUnitClass("Unit", obj, this, mobMetaClass));
		this.recordClass(new MobFalseClass("False", obj, this, mobMetaClass));
		this.recordClass(new MobTrueClass("True", obj, this, mobMetaClass));
		
		objClass.addSubclass(new MobMetaClass("UndefinedObject class", objClass, this, mobMetaClass));
		obj.addSubclass(new MobClass("UndefinedObject", obj, this, this.getClassByName("UndefinedObject class")));
	}
	
	public void recordClass(MobClass clazz) {
		this.classes.put(clazz.name(), clazz);
	}
	public MobClass getClassByName(String name) {
		return this.classes.get(name);
	}


	private MobClass mobClass() { return this.getClassByName("Class"); }
	private MobFloatClass floatClass() { return (MobFloatClass) this.getClassByName("Float"); }
	private MobIntegerClass integerClass() { return (MobIntegerClass) this.getClassByName("Integer"); }
	private MobCharacterClass characterClass() { return (MobCharacterClass) this.getClassByName("Character"); }
	private MobStringClass stringClass() { return (MobStringClass) this.getClassByName("String"); }
	private MobSymbolClass symbolClass() { return (MobSymbolClass) this.getClassByName("Symbol"); }
	private MobUnitClass unitClass() { return (MobUnitClass) this.getClassByName("Unit"); }

	
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
		MobSequence seq = new MobSequence(new MobSequenceClass("Sequence", this.mobClass(), this, this.mobClass()));
		seq.addAll(contents);
		return seq;
	}
}
