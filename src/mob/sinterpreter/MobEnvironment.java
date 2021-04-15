package mob.sinterpreter;

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

	public MobEnvironment() {
		this.falseDef = new MobFalseDef();
		this.trueDef = new MobTrueDef();
		this.floatDef = new MobFloatDef();
		this.integerDef = new MobIntegerDef();
		this.stringDef = new MobStringDef();
		this.symbolDef = new MobSymbolDef();
		this.nilDef = new MobNilDef();
	}

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

}
