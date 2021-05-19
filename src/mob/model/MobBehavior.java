package mob.model;

import java.util.Arrays;
import java.util.HashMap;

import mob.ast.MobAstElement;
import mob.model.primitives.MobUnit;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;
import mob.sinterpreter.MobObjectMethod;

/*
 * Behavior provides the minimum state necessary for objects that have instances: 
 * this includes a superclass link, a method dictionary, and a description 
 * of the instances (i.e., representation and number). 
 * Behavior inherits from Object, so it, and all of its subclasses, can behave like objects.
 * Behavior is also the basic interface to the compiler. 
 * It provides methods for creating a method dictionary, compiling methods, 
 * creating instances (i.e., new, basicNew, new:, and basicNew:), 
 * manipulating the class hierarchy (i.e., superclass:, addSubclass:), 
 * accessing methods (i.e., selectors, allSelectors, compiledMethodAt:), 
 * accessing instances and variables (i.e., allInstances, instVarNames …), 
 * accessing the class hierarchy (i.e., superclass, subclasses) 
 * and querying (i.e., hasMethods, includesSelector, canUnderstand:, inheritsFrom:, isVariable). 
 */
public class MobBehavior extends MobObject {
	private HashMap<String, MobMethod> methodDict;
	private MobClass superclass;
	protected String[] slots;

	public MobBehavior(MobEnvironment environment, MobClass superclass, MobClass definition) {
		super(environment, definition);
		this.methodDict = new HashMap<>();
		this.superclass = superclass;
		this.initializeSlots();
	}

	protected void initializeSlots() {
		this.slots = new String[0];
	}

	protected void initializePrimitives() {
		this.addMethod(new MobMethod("println") {
			public void run(MobContext ctx, MobAstElement receiver) {
				System.out.println(receiver);
			}
		});
		this.addMethod(new MobMethod("class") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject self = (MobObject) receiver;
				MobClass selfDef = self.definition();
				ctx.returnElement(selfDef);
			}
		});
		this.addMethod(new MobMethod("println") {
			public void run(MobContext ctx, MobAstElement receiver) {
				System.out.println(receiver);
			}
		});
		this.addMethod(new MobMethod("addMethod:named:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject name = (MobObject) ctx.pop();
				MobUnit code = (MobUnit) ctx.pop();
				MobClass self = (MobClass) receiver;
				self.addMethod(new MobObjectMethod((String)name.rawValue(), code));
			}
		});
		this.addMethod(new MobMethod("addSubclassNamed:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject name = (MobObject) ctx.pop();
				MobClass self = (MobClass) receiver;
				MobClass newClass = new MobClass((String)name.rawValue(), ctx.environment(), self, null);
				MobMetaClass definition = new MobMetaClass(newClass, ctx.environment(), self, ctx.environment().getClassByName("MetaClass"));
				newClass.setDefinition(definition);
				self.addSubclass(newClass);
			}
		});
		this.addMethod(new MobMethod("addSlotNamed:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject name = (MobObject) ctx.pop();
				MobClass self = (MobClass) receiver;
				self.addSlot((String)name.rawValue());
			}
		});

		this.addMethod(new MobMethod("new") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobClass self = (MobClass) receiver;
				ctx.returnElement(self.newInstance());
			}
		});
		this.addMethod(new MobMethod("basicNew") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobClass self = (MobClass) receiver;
				ctx.returnElement(self.newInstance());
			}
		});

		this.addMethod(new MobMethod("instVarAt:put:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject v = (MobObject) ctx.pop();
				MobObject pos = (MobObject) ctx.pop();
				MobObject self = (MobObject) receiver;
				self.instVarAtPut((Integer)pos.rawValue(), v);
			}
		});
		this.addMethod(new MobMethod("size") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject self = (MobObject) receiver;
				ctx.returnElement(ctx.environment().newInteger(self.valuesCapacity()));
			}
		});
		this.addMethod(new MobMethod("prim_instVarAt:put:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject v = (MobObject) ctx.pop();
				MobObject pos = (MobObject) ctx.pop();
				MobObject self = (MobObject) receiver;
				self.instVarAtPut((Integer)pos.rawValue(), v.rawValue());
			}
		});
		this.addMethod(new MobMethod("instVarAt:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject pos = (MobObject) ctx.pop();
				MobObject self = (MobObject) receiver;
				ctx.returnElement((MobAstElement) self.instVarAt((Integer)pos.rawValue()));
			}
		});
	}

	public void addMethod(MobMethod definition) {
		this.methodDict.put(definition.selector(), definition);
	}

	public MobMethod methodNamed(String signature) {
		return this.methodDict.get(signature);
	}

	public MobMethod lookupMethodNamed(String signature) {
		MobMethod found = methodDict.get(signature);
		if (found != null) {
			return found;
		}
		if (this.superclass != null) {
			return this.superclass.lookupMethodNamed(signature);
		}
		return null;
	}

	public MobClass superclass() {
		return this.superclass;
	}

	public void setSuperclass(MobClass cls) {
		this.superclass = cls;
	}

	public void addSubclass(MobClass cls) {
		if (cls.name() == null)
			return;
		this.environment().recordClass(cls);
	}

	String name() {
		return null;
	}

	public String[] slots() {
		return this.slots;
	}

	public String[] inheritedSlots() {
		String[] is = this.superclass == null ? new String[0] : this.superclass().inheritedSlots();
		String[] r = new String[is.length + this.slots.length];
		int idx = 0;
		for (String s : is) {
			r[idx++] = s;
		}
		for (String s : this.slots) {
			r[idx++] = s;
		}
		return r;
	}

	public Integer positionOfSlot(String name) {
		String[] allSlots = this.inheritedSlots();
		Integer pos = -1;
		for (String s : allSlots) {
			pos++;
			if (s.equals(name))
				return pos;
		}
		return -1;
	}

	public void addSlot(String slotName) {
		if (this.positionOfSlot(slotName) > -1)
			throw new Error("A slot name '" + slotName + "' already exists in class " + this.name());
		this.slots = Arrays.copyOf(this.slots, this.slots.length + 1);
		this.slots[this.slots.length - 1] = slotName;
	}

	public int numberOfSlots() {
		return this.slots.length;
	}
	public int numberOfInheritedSlots() {
		int nb = this.numberOfSlots();
		if (this.superclass != null)
			nb+= this.superclass.numberOfInheritedSlots();
		return nb;
	}

	public void run(MobContext ctx, MobObject receiver, String signature, Boolean superflag) {
		MobMethod m = superflag ? this.superclass != null ? this.superclass.lookupMethodNamed(signature) : null :  this.lookupMethodNamed(signature);
		if (m == null)
			throw new Error(this + " does not understand '" + signature + "'");
		//System.out.println(receiver.definition().name() + " . "+ signature);
		m.run(ctx, receiver);
	}
}
