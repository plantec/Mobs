package mob.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mob.model.MobObject;
import mob.model.primitives.MobFalseClass;
import mob.model.primitives.MobIntegerClass;
import mob.model.primitives.MobStringClass;
import mob.model.primitives.MobSymbolClass;
import mob.model.primitives.MobTrueClass;

class MobPrimitiveTest {

	@Test
	void testPrimitiveTypes() {
		MobIntegerClass n = new MobIntegerClass(null,null, null, null);
		MobObject ii = n.newInstance();
		ii.primValueAtPut(0, 1);
		assertTrue(ii.primValue() instanceof Integer);
		assertTrue((Integer)ii.primValue() == 1);
		MobTrueClass td = new MobTrueClass(null,null, null, null);
		MobObject t = td.newInstance();
		assertTrue(t.primValue() instanceof Boolean);
		assertTrue((Boolean)t.primValue());
		MobFalseClass fd = new MobFalseClass(null,null, null, null);
		MobObject f = fd.newInstance();
		assertFalse((Boolean)f.primValue());
		MobStringClass sd = new MobStringClass(null,null, null, null);
		MobObject s = sd.newInstance();
		s.primValueAtPut(0, "Hello");
		assertTrue(s.primValue() instanceof String);
		assertTrue(s.primValue().equals("Hello"));
		MobSymbolClass syd = new MobSymbolClass(null,null,null, null);
		MobObject sy = syd.newInstance();
		sy.primValueAtPut(0, "X");
		assertTrue(sy.primValue() instanceof String);
		assertTrue(sy.primValue().equals("X"));
	}
}
