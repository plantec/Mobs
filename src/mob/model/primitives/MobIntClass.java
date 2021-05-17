package mob.model.primitives;

import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobEnvironment;

public class MobIntClass extends MobObjectClass {
	
	public MobIntClass(MobClass superclass, MobEnvironment environment) {
		super("Int", superclass, environment, null);
		MobClass objClass = environment.getClassByName("Object");
		MobClass metaClass = environment.getClassByName("MetaClass");
		MobMetaClass def = new MobMetaClass(this, objClass.definition(), environment, metaClass);
		this.setDefinition(def);
	}

}
