package mob.model;

import mob.ast.MobAstElement;
import mob.model.primitives.MobString;
import mob.model.primitives.MobUnit;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;
import mob.sinterpreter.MobObjectMethod;

public class MobMetaClass extends MobClass {

	public MobMetaClass(String name, MobClass superclass, MobEnvironment environment, MobClass def) {
		super(name, superclass, environment, def);
	}
	
	protected void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("addMethod:named:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobString name = (MobString) ctx.pop();
				MobUnit code = (MobUnit) ctx.pop();
				MobClass self = (MobClass) receiver;
				self.addMethod(new MobObjectMethod(name.rawValue(), code));
			}
		});
		this.addMethod(new MobMethod("addSubclass:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobString name = (MobString) ctx.pop();
				MobClass self = (MobClass) receiver;
				if (self.definition() != self) {
					self.definition().addSubclass(new MobMetaClass(name.rawValue() + " class", self, ctx.environment(),
							ctx.environment().getClassByName("MetaClass")));
				}
				self.addSubclass(new MobClass(name.rawValue(), self, ctx.environment(),
						ctx.environment().getClassByName(name.rawValue() + " class")));
			}
		});
		this.addMethod(new MobMethod("addSlotNamed:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobString name = (MobString) ctx.pop();
				MobClass self = (MobClass) receiver;
				self.addSlot(name.rawValue());
			}
		});
		
		this.addMethod(new MobMethod("new") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobClass self = (MobClass) receiver;
				System.out.println("new : self = " + self);
				ctx.returnElement(self.newInstance());
			}
		});
	}

}
