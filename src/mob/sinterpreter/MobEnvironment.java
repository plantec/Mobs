package mob.sinterpreter;

import java.util.List;

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
	private MobMetaClass mobMetaClass;
	
	private MobNil nil_;
	private MobFalse false_;
	private MobTrue true_;
	
	public MobEnvironment() {
		this.mobMetaClass = new MobMetaClass(this, null);
		this.mobMetaClass.setDefinition(this.mobMetaClass);
		this.mobClass = new MobClass(this, mobMetaClass);
		this.floatClass = new MobFloatClass(this, this.mobMetaClass);
		this.integerClass = new MobIntegerClass(this, this.mobMetaClass);
		this.characterClass = new MobCharacterClass(this, this.mobMetaClass);
		this.stringClass = new MobStringClass(this, this.mobMetaClass);
		this.symbolClass = new MobSymbolClass(this, this.mobMetaClass);
		this.unitClass = new MobUnitClass(this, this.mobMetaClass);
		this.nilClass = new MobNilClass(this, this.mobClass);
		this.falseClass = new MobFalseClass(this, this.mobClass);
		this.trueClass = new MobTrueClass(this, this.mobClass);

		this.nil_ = this.nilClass.newInstance();
		this.true_ = this.trueClass.newInstance();
		this.false_ = this.falseClass.newInstance();
	}
	
	public MobClass mobMetaClass() { return this.mobMetaClass; }
	public MobClass mobClass() { return this.mobClass; }
	public MobFalseClass falseClass() { return this.falseClass; }
	public MobTrueClass trueClass() { return this.trueClass; }
	public MobFloatClass floatClass() { return this.floatClass; }
	public MobIntegerClass integerClass() { return this.integerClass; }
	public MobCharacterClass characterClass() { return this.characterClass; }
	public MobStringClass stringClass() { return this.stringClass; }
	public MobSymbolClass symbolClass() { return this.symbolClass; }
	public MobNilClass nilClass() { return this.nilClass; }
	public MobUnitClass unitClass() { return this.unitClass; }

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
		return this.floatClass.newInstance(p);
	}

	public MobInteger newInteger(Integer p) {
		return this.integerClass.newInstance(p);
	}

	public MobCharacter newCharacter(Character p) {
		return this.characterClass.newInstance(p);
	}

	public MobString newString(String p) {
		return this.stringClass.newInstance(p);
	}

	public MobSymbol newSymbol(String p) {
		return this.symbolClass.newInstance(p);
	}

	public MobUnit newUnit() {
		return this.unitClass.newInstance();
	}
	
	public MobSequence newSequence(List<MobAstElement> contents) {
		MobSequence seq = new MobSequence(new MobSequenceClass(this, this.mobClass));
		seq.addAll(contents);
		return seq;
	}
}
