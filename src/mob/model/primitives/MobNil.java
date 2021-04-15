package mob.model.primitives;

import mob.model.MobEntity;
import mob.model.MobEntityDef;
import mob.model.MobVisitor;

public class MobNil extends MobEntity {

	public MobNil(MobEntityDef def) {
		super(def);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitNil(this);
		
	}

}
