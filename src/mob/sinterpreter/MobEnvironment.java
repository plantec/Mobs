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
		MobMetaClass mobMetaClass = new MobMetaClass(null, this, null, null);
		mobMetaClass.setName("MetaClass");
		MobMetaClass mobMetaClassClass = new MobMetaClass(mobMetaClass, this, null, null);
		mobMetaClassClass.setClass(mobMetaClass);
		mobMetaClass.setClass(mobMetaClassClass);
		this.recordClass(mobMetaClass);
		
		MobClass objectClass = new MobObjectClass(null, this, null, mobMetaClass);
		MobClass object = new MobClass("Object", this, null, objectClass);
		this.recordClass(object);
		
		MobBehaviorClass behaviorClass = new MobBehaviorClass(null, this, objectClass, mobMetaClass);
		MobClass behavior = new MobClass("Behavior", this, object, behaviorClass);
		this.recordClass(behavior);
		
		MobClassDescriptionClass classDescriptionClass = new MobClassDescriptionClass(null, this, behaviorClass, mobMetaClass);
		MobClass classDescription = new MobClass("ClassDescription", this, behavior, classDescriptionClass);
		this.recordClass(classDescription);
		
		mobMetaClassClass.setSuperclass(classDescriptionClass);
		mobMetaClass.setSuperclass(classDescription);
		
		MobClassClass mobClassClass = new MobClassClass(null, this, classDescriptionClass, mobMetaClass);
		MobClass mobClass = new MobClass("Class", this, classDescription, mobClassClass);
		this.recordClass(mobClass);
		
		objectClass.setSuperclass(mobClass);
		this.recordClass(new MobFloatClass("Float", this, object, null));
		MobMetaClass floatClassClass = new MobMetaClass(this.getClassByName("Float"), this, objectClass, mobMetaClass);
		this.getClassByName("Float").setClass(floatClassClass);
		
		
		MobClass intClass = new MobObjectClass("Int", this, object, null);
		MobMetaClass intClassClass = new MobMetaClass(intClass, this, object.definition(), mobMetaClass);
		intClass.setClass(intClassClass);
		this.recordClass(intClass);	
		this.initInteger();
		
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
		MobObject o = this.floatClass().newInstance();
		o.instVarAtPut(0, p);
		return o;
	}

	public MobObject newInteger(Integer p) {
		MobObject o = this.integerClass().newInstance();
		o.instVarAtPut(0, p);
		return o;
	}

	public MobObject newCharacter(Character p) {
		MobObject o = this.characterClass().newInstance();
		o.instVarAtPut(0, p);
		return o;
	}

	public MobObject newString(String p) {
		MobObject o = this.stringClass().newInstance();
		o.instVarAtPut(0, p);
		return o;
	}

	public MobObject newSymbol(String p) {
		MobObject o = this.symbolClass().newInstance();
		o.instVarAtPut(0, p);
		return o;
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
