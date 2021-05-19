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
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.model.primitives.MobCharacterClass;
import mob.model.primitives.MobFalseClass;
import mob.model.primitives.MobFloatClass;
import mob.model.primitives.MobIntegerClass;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobSequenceClass;
import mob.model.primitives.MobStringClass;
import mob.model.primitives.MobSymbolClass;
import mob.model.primitives.MobTrueClass;
import mob.model.primitives.MobUnit;
import mob.model.primitives.MobUnitClass;

public class MobEnvironment {
	private Map<String, MobClass> classes;
		
	public MobEnvironment() {
		classes = new HashMap<>();
		MobMetaClass mobMetaClass = new MobMetaClass(null, null, this, null);
		mobMetaClass.setName("MetaClass");
		MobMetaClass mobMetaClassClass = new MobMetaClass(mobMetaClass, null, this, null);
		mobMetaClassClass.setClass(mobMetaClass);
		mobMetaClass.setClass(mobMetaClassClass);
		this.recordClass(mobMetaClass);
		
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
		this.recordClass(new MobFloatClass("Float", object, this, null));
		MobMetaClass floatClassClass = new MobMetaClass(this.getClassByName("Float"), objectClass, this, mobMetaClass);
		this.getClassByName("Float").setClass(floatClassClass);
		
		
		MobClass intClass = new MobObjectClass("Int", object, this, null);
		MobMetaClass intClassClass = new MobMetaClass(intClass, object.definition(), this, mobMetaClass);
		intClass.setClass(intClassClass);
		this.recordClass(intClass);	
		this.initInteger();
		
		this.recordClass(new MobIntegerClass("Integer", object, this, null));
		MobMetaClass integerClassClass = new MobMetaClass(this.getClassByName("Integer"), objectClass, this, mobMetaClass);
		this.getClassByName("Integer").setClass(integerClassClass);
		
		this.recordClass(new MobCharacterClass("Character", object, this, null));
		MobMetaClass characterClassClass = new MobMetaClass(this.getClassByName("Character"), objectClass, this, mobMetaClass);
		this.getClassByName("Character").setClass(characterClassClass);
		
		this.recordClass(new MobStringClass("String", object, this, null));
		MobMetaClass stringClassClass = new MobMetaClass(this.getClassByName("String"), objectClass, this, mobMetaClass);
		this.getClassByName("String").setClass(stringClassClass);
		
		this.recordClass(new MobSymbolClass("Symbol", object, this, null));
		MobMetaClass symbolClassClass = new MobMetaClass(this.getClassByName("Symbol"), objectClass, this, mobMetaClass);
		this.getClassByName("Symbol").setClass(symbolClassClass);		
		
		this.recordClass(new MobUnitClass("Unit", object, this, null));
		MobMetaClass unitClassClass = new MobMetaClass(this.getClassByName("Unit"), objectClass, this, mobMetaClass);
		this.getClassByName("Unit").setClass(unitClassClass);
		
		this.recordClass(new MobFalseClass("False", object, this, null));
		MobMetaClass falseClassClass = new MobMetaClass(this.getClassByName("False"), objectClass, this, mobMetaClass);
		this.getClassByName("False").setClass(falseClassClass);
		
		this.recordClass(new MobTrueClass("True", object, this, null));
		MobMetaClass trueClassClass = new MobMetaClass(this.getClassByName("True"), objectClass, this, mobMetaClass);
		this.getClassByName("True").setClass(trueClassClass);
		
		this.recordClass(new MobClass("UndefinedObject", object, this, null));
		MobMetaClass undefinedObjectClassClass = new MobMetaClass(this.getClassByName("UndefinedObject"), objectClass, this, mobMetaClass);
		this.getClassByName("UndefinedObject").setClass(undefinedObjectClassClass);
		
		this.recordClass(new MobSequenceClass("Sequence", object, this, null));
		MobMetaClass sequenceClassClass = new MobMetaClass(this.getClassByName("Sequence"), objectClass, this, mobMetaClass);
		this.getClassByName("Sequence").setClass(sequenceClassClass);
		
	}
	
	private void initInteger() {
		MobClass intCls = (MobClass) this.getClassByName("Int");
		intCls.addMethod(new MobMethod("+") {
			public void run(MobContext ctx, MobAstElement receiver) {
				Object arg1 = ((MobObject) ctx.pop()).instVarAt(0);
				Object r = ((MobObject) receiver).instVarAt(0);
				MobObject result = intCls.newInstance();
				if (arg1 instanceof Integer) {
					result.instVarAtPut(0, ((Integer) r) + ((Integer) arg1));
				} else {
					throw new Error("incompatible argument");
				}
				ctx.push(result);
			}
		});
		/*
		intCls.addMethod(new MobMethod("-") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(ctx.newInteger(r.rawValue()-arg.rawValue()));
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement(ctx.newFloat(r.rawValue()-arg.rawValue()));
					}
				}
			});
		intCls.addMethod(new MobMethod("*") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(ctx.newInteger(r.rawValue()*arg.rawValue()));
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement(ctx.newFloat(r.rawValue()*arg.rawValue()));
					}
				}
			});
		intCls.addMethod(new MobMethod("/") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(ctx.newInteger(r.rawValue()/arg.rawValue()));
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement(ctx.newFloat(r.rawValue()/arg.rawValue()));
					}
				}
			});
		intCls.addMethod(new MobMethod("negated") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement r = (MobInteger) receiver;
					ctx.returnElement(ctx.newInteger(((MobInteger)r).rawValue()*-1));
				}
			});
		intCls.addMethod(new MobMethod("<") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(r.rawValue() < arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement(r.rawValue() < arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					}
				}
			});
		intCls.addMethod(new MobMethod(">") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						ctx.newTrue();
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(r.rawValue() > arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement(r.rawValue() > arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					}
				}
			});
		intCls.addMethod(new MobMethod(">=") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(r.rawValue() >= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement(r.rawValue() >= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					}
				}
			});
		intCls.addMethod(new MobMethod("<=") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(r.rawValue() <= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement(r.rawValue() <= arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					}
				}
			});
		intCls.addMethod(new MobMethod("=") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(r.rawValue() == arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement((float)r.rawValue() == (float)arg.rawValue() ? ctx.newTrue():ctx.newFalse());
					}
				}
			});
		intCls.addMethod(new MobMethod("~=") {
				public void run(MobContext ctx, MobAstElement receiver) {
					MobAstElement arg1 = ctx.pop();
					MobInteger r = (MobInteger) receiver;
					if (arg1 instanceof MobInteger) {
						MobInteger arg = (MobInteger) arg1;
						ctx.returnElement(r.rawValue().equals(arg.rawValue()) ? ctx.newFalse():ctx.newTrue());
					} else {
						MobFloat arg = (MobFloat) arg1;
						ctx.returnElement((float)r.rawValue() == (float)arg.rawValue() ? ctx.newFalse():ctx.newTrue());
					}
				}
			});
*/
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
		return this.floatClass().newInstance(p);
	}

	public MobObject newInteger(Integer p) {
		return this.integerClass().newInstance(p);
	}

	public MobObject newCharacter(Character p) {
		return this.characterClass().newInstance(p);
	}

	public MobObject newString(String p) {
		return this.stringClass().newInstance(p);
	}

	public MobObject newSymbol(String p) {
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
