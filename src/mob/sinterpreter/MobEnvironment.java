package mob.sinterpreter;

import java.util.List;

import mob.model.MobEntity;
import mob.model.MobNullDef;
import mob.model.MobSequence;
import mob.model.MobUnit;
import mob.model.MobUnitDef;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFalseDef;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobFloatDef;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobIntegerDef;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobNilDef;
import mob.model.primitives.MobString;
import mob.model.primitives.MobStringDef;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobSymbolDef;
import mob.model.primitives.MobTrue;
import mob.model.primitives.MobTrueDef;

public class MobEnvironment {
	private MobFalseDef falseDef;
	private MobTrueDef trueDef;
	private MobFloatDef floatDef;
	private MobIntegerDef integerDef;
	private MobStringDef stringDef;
	private MobSymbolDef symbolDef;
	private MobNilDef nilDef;
	private MobUnitDef unitDef;

	public MobEnvironment() {
		this.falseDef = new MobFalseDef();
		this.trueDef = new MobTrueDef();
		this.floatDef = new MobFloatDef();
		this.integerDef = new MobIntegerDef();
		this.stringDef = new MobStringDef();
		this.symbolDef = new MobSymbolDef();
		this.nilDef = new MobNilDef();
		this.unitDef = new MobUnitDef();
	}
	
	public MobFalseDef falseDef() { return this.falseDef; }
	public MobTrueDef trueDef() { return this.trueDef; }
	public MobFloatDef floatDef() { return this.floatDef; }
	public MobIntegerDef integerDef() { return this.integerDef; }
	public MobStringDef stringDef() { return this.stringDef; }
	public MobSymbolDef symbolDef() { return this.symbolDef; }
	public MobNilDef nilDef() { return this.nilDef; }
	public MobUnitDef unitDef() { return this.unitDef; }

	public MobFalse newFalse() {
		return this.falseDef.newInstance();
	}

	public MobTrue newTrue() {
		return this.trueDef.newInstance();
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

	public MobNil newNil() {
		return this.nilDef.newInstance();
	}
	public MobUnit newUnit(MobEntity contents) {
		return this.unitDef.newInstance(this, contents);
	}
	
	public MobSequence newSequence(List<MobEntity> contents) {
		MobSequence seq = new MobSequence(new MobNullDef());
		seq.addAll(contents);
		return seq;
	}
}
