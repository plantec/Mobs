package mob.model.primitives;

import stree.parser.SNode;

public class MobIntegerDef extends MobPrimitiveDef<Integer> {

	private void timesRepeat(MobInteger receiver, SNode args) {
		Integer mob = (Integer)receiver.rawValue();
		for (int i = 0; i < mob; i++ ) {
			System.out.print(i + " ");
		}
	}

	@Override
	public MobInteger newInstance(Integer mob) {
		return new MobInteger(this, mob);
	}

}
