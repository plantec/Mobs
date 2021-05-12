package mob.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;

public class MobClass extends MobObject {
	private String name;
	private HashMap<String, MobMethod> methodDict;
	private List<String> slots;
	private MobClass superclass;

	public MobClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(environment, def);
		this.methodDict = new HashMap<>();
		this.slots = new ArrayList<>();
		this.name = name;
		this.superclass = superclass;
		this.initializePrimitives();
	}

	protected void initializePrimitives() {
		this.addMethod(new MobMethod("println") {
			public void run(MobContext ctx, MobAstElement receiver) {
				System.out.println(receiver);
			}
		});
		this.addMethod(new MobMethod("definition") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject self = (MobObject) receiver;
				MobClass selfDef = self.definition();
				ctx.returnElement(selfDef);
			}
		});
	}

	public MobClass superclass() {
		return this.superclass;
	}
	
	public void setSuperclass(MobClass cls) {
		this.superclass = cls;
	}

	public void addSubclass(MobClass cls) {
		this.environment().recordClass(cls);
	}

	public void addMethod(MobMethod definition) {
		this.methodDict.put(definition.selector(), definition);
	}

	public MobMethod methodNamed(String signature) {
		return this.methodDict.get(signature);
	}

	public List<String> slots() {
		return this.slots;
	}
	public List<String> inheritedSlots() {
		List<String> s = new ArrayList<>();
		if (this.superclass != null) {
			s.addAll(this.superclass.inheritedSlots());
		}
		s.addAll(this.slots());
		return s;
	}

	public void addSlot(String slotName) {
		if (this.slots.contains(slotName))
			throw new Error("A slot name '" + slotName + "' already exists in class " + this.name());
		this.slots.add(slotName);
	}

	public String name() {
		return this.name;
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

	public MobObject newInstance() {
		return new MobObject(this);
	}

	public void run(MobContext ctx, MobObject receiver, String signature) {
		MobMethod m = this.lookupMethodNamed(signature);
		if (m == null)
			throw new Error(receiver.definition().name() + " does not understand '" + signature + "'");
		m.run(ctx, receiver);
	}

	public void run(MobContext ctx, MobProtoObject receiver, String signature) {
		MobMethod m = this.lookupMethodNamed(signature);
		if (m == null)
			throw new Error(receiver.toString() + " does not understand '" + signature + "'");
		m.run(ctx, receiver);
	}

}
