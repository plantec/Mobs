package mob.sinterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mob.ast.MobAstElement;
import mob.model.primitives.MobCharacter;
import mob.model.primitives.MobCharacterClass;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFalseClass;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobFloatClass;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobIntegerClass;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobNilClass;
import mob.model.primitives.MobClass;
import mob.model.primitives.MobMetaClass;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobSequenceClass;
import mob.model.primitives.MobString;
import mob.model.primitives.MobStringClass;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobSymbolClass;
import mob.model.primitives.MobTrue;
import mob.model.primitives.MobTrueClass;
import mob.model.primitives.MobUnit;
import mob.model.primitives.MobUnitClass;

public class MobEnvironment {
	private MobFalseClass falseClass;
	private MobTrueClass trueClass;
	private MobFloatClass floatClass;
	private MobIntegerClass integerClass;
	private MobCharacterClass characterClass;
	private MobStringClass stringClass;
	private MobSymbolClass symbolClass;
	private MobNilClass nilClass;
	private MobUnitClass unitClass;
	private MobClass mobClass;
	
	private Map<String, MobClass> classes;
	
	private MobNil nil_;
	private MobFalse false_;
	private MobTrue true_;
	
	public MobEnvironment() {
		classes = new HashMap<>();
		MobMetaClass mobMetaClass = new MobMetaClass("MetaClass", null, this, null);
		mobMetaClass.setDefinition(mobMetaClass);
		this.recordClass(mobMetaClass);
		
		this.recordClass(new MobClass("Class", null, this, mobMetaClass));
		this.recordClass(new MobFloatClass("Float", null, this, mobMetaClass));
		this.recordClass(new MobIntegerClass("Integer", null, this, mobMetaClass));
		this.recordClass(new MobCharacterClass("Character", null, this, mobMetaClass));
		this.recordClass(new MobStringClass("String", null, this, mobMetaClass));
		this.recordClass(new MobSymbolClass("Symbol", null, this, mobMetaClass));
		this.recordClass(new MobUnitClass("Unit", null, this, mobMetaClass));
		this.recordClass(new MobNilClass("Nil", null, this, mobMetaClass));
		this.recordClass(new MobFalseClass("False", null, this, mobMetaClass));
		this.recordClass(new MobTrueClass("True", null, this, mobMetaClass));

		this.nil_ = (MobNil) this.getClassByName("Nil").newInstance();
		this.true_ = (MobTrue) this.getClassByName("True").newInstance();
		this.false_ = (MobFalse) this.getClassByName("False").newInstance();
	}
	
	public void recordClass(MobClass clazz) {
		this.classes.put(clazz.name(), clazz);
	}
	public MobClass getClassByName(String name) {
		return this.classes.get(name);
	}

	public MobClass mobMetaClass() { return this.getClassByName("MetaClass"); }
	public MobClass mobClass() { return this.getClassByName("Class"); }
	public MobFalseClass falseClass() { return (MobFalseClass) this.getClassByName("False"); }
	public MobTrueClass trueClass() { return (MobTrueClass) this.getClassByName("True"); }
	public MobFloatClass floatClass() { return (MobFloatClass) this.getClassByName("Float"); }
	public MobIntegerClass integerClass() { return (MobIntegerClass) this.getClassByName("Integer"); }
	public MobCharacterClass characterClass() { return (MobCharacterClass) this.getClassByName("Character"); }
	public MobStringClass stringClass() { return (MobStringClass) this.getClassByName("String"); }
	public MobSymbolClass symbolClass() { return (MobSymbolClass) this.getClassByName("Symbol"); }
	public MobNilClass nilClass() { return (MobNilClass) this.getClassByName("Nil"); }
	public MobUnitClass unitClass() { return (MobUnitClass) this.getClassByName("Unit"); }

	public MobNil newNil() {
		return this.nil_;
	}
	public MobFalse newFalse() {
		return this.false_;
	}

	public MobTrue newTrue() {
		return this.true_;
	}

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
		MobSequence seq = new MobSequence(new MobSequenceClass("Sequence", mobClass, this, this.mobClass()));
		seq.addAll(contents);
		return seq;
	}
}
