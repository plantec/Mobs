package mob.model.primitives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;
import mob.sinterpreter.MobObjectMethod;
import mob.sinterpreter.MobVariable;

public class MobClass extends MobObject {
	private String name;
	private HashMap<String, MobMethod> methodDict;
	private List<MobVariable> slots;
	private MobClass superclass;
	

	public MobClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(environment, def);
		this.methodDict = new HashMap<>();
		this.slots = new ArrayList<>();
		this.name = name;
		this.superclass = superclass;
		this.initializePrimitives();
	}
	
	protected void initializePrimitives () {
		this.addMethod(new MobMethod("println") {
			public void run(MobContext ctx, MobAstElement receiver) {
				System.out.println(receiver);
			}
		});
		this.addMethod(new MobMethod("addMethod:named:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobString name = (MobString) ctx.pop();
				MobUnit code = (MobUnit) ctx.pop();
				MobClass self = (MobClass) receiver;
				self.addMethod(new MobObjectMethod(name.rawValue(), code));
			}
		});
		this.addMethod(new MobMethod("addSlotNamed:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobString name = (MobString) ctx.pop();
				MobClass self = (MobClass) receiver;
				self.addSlot(new MobVariable(name.rawValue()));
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

	public void addMethod(MobMethod definition) {
		this.methodDict.put(definition.selector(), definition);
	}

	public MobMethod methodNamed(String signature) {
		return this.methodDict.get(signature);
	}
	
	public List<MobVariable> slots() {
		return this.slots;
	}

	public void addSlot(MobVariable var) {
		if (this.slotNamed(var.name()) != null)
			throw new Error("A slot name '" + var.name() + "' already exists in class " + this.name());
		this.slots.add(var);
	}
	
	public void setSlotValue(String name, MobAstElement value) {
		MobVariable slot = this.slotNamed(name);
		if (slot == null)
			throw new Error("Undeclared slot name '" + name);
		slot.setValue(value);
	}

	public MobAstElement slotValue(String name) {
		MobVariable slot = this.slotNamed(name);
		if (slot == null)
			throw new Error("Undeclared slot name '" + name);
		return slot.value();
	}

	public String name() {
		return this.name;
	}

	public MobVariable slotNamed(String name) {
		for (MobVariable v : this.slots) {
			if (v.name().equals(v.name()))
				return v;
		}
		return null;
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
			throw new Error(receiver.toString() + " does not understand '" + signature + "'");
		m.run(ctx, receiver);
	}

}
