package mob.sinterpreter;

import java.util.List;

import mob.ast.MobAstElement;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFalseClass;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobFloatClass;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobIntegerClass;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobNilClass;
import mob.model.primitives.MobObjectClass;
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
	private MobFalseClass falseDef;
	private MobTrueClass trueDef;
	private MobFloatClass floatDef;
	private MobIntegerClass integerDef;
	private MobStringClass stringDef;
	private MobSymbolClass symbolDef;
	private MobNilClass nilDef;
	private MobUnitClass unitDef;
	private MobObjectClass objectDef;
	private MobObjectClass objectMetaDef;
	
	private MobNil nil_;
	private MobFalse false_;
	private MobTrue true_;
	
	public MobEnvironment() {
		this.objectMetaDef = new MobObjectClass(null);
		this.objectMetaDef.setDefinition(this.objectMetaDef);
		this.objectDef = new MobObjectClass(objectMetaDef);
		this.floatDef = new MobFloatClass(this.objectDef);
		this.integerDef = new MobIntegerClass(this.objectDef);
		this.stringDef = new MobStringClass(this.objectDef);
		this.symbolDef = new MobSymbolClass(this.objectDef);
		this.unitDef = new MobUnitClass(this.objectDef);
		this.nilDef = new MobNilClass(this.objectDef);
		this.falseDef = new MobFalseClass(this.objectDef);
		this.trueDef = new MobTrueClass(this.objectDef);

		this.nil_ = this.nilDef.newInstance();
		this.true_ = this.trueDef.newInstance();
		this.false_ = this.falseDef.newInstance();
	}
	
	public MobFalseClass falseDef() { return this.falseDef; }
	public MobTrueClass trueDef() { return this.trueDef; }
	public MobFloatClass floatDef() { return this.floatDef; }
	public MobIntegerClass integerDef() { return this.integerDef; }
	public MobStringClass stringDef() { return this.stringDef; }
	public MobSymbolClass symbolDef() { return this.symbolDef; }
	public MobNilClass nilDef() { return this.nilDef; }
	public MobUnitClass unitDef() { return this.unitDef; }

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
		return this.floatDef.newInstance(p);
	}

	public MobInteger newInteger(Integer p) {
		return this.integerDef.newInstance(p);
	}

	public MobString newString(String p) {
		return this.stringDef.newInstance(p);
	}

	public MobSymbol newSymbol(String p) {
		return this.symbolDef.newInstance(p);
	}

	public MobUnit newUnit() {
		return this.unitDef.newInstance();
	}
	
	public MobSequence newSequence(List<MobAstElement> contents) {
		MobSequence seq = new MobSequence(new MobSequenceClass(this.objectDef));
		seq.addAll(contents);
		return seq;
	}
}
