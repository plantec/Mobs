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
		ii.instVarAtPut(0, 1);
		assertTrue(ii.rawValue() instanceof Integer);
		assertTrue((Integer)ii.rawValue() == 1);
		MobTrueClass td = new MobTrueClass(null,null, null, null);
		MobObject t = td.newInstance();
		assertTrue(t.rawValue() instanceof Boolean);
		assertTrue((Boolean)t.rawValue());
		MobFalseClass fd = new MobFalseClass(null,null, null, null);
		MobObject f = fd.newInstance();
		assertFalse((Boolean)f.rawValue());
		MobStringClass sd = new MobStringClass(null,null, null, null);
		MobObject s = sd.newInstance();
		s.instVarAtPut(0, "Hello");
		assertTrue(s.rawValue() instanceof String);
		assertTrue(s.rawValue().equals("Hello"));
		MobSymbolClass syd = new MobSymbolClass(null,null,null, null);
		MobObject sy = syd.newInstance();
		sy.instVarAtPut(0, "X");
		assertTrue(sy.rawValue() instanceof String);
		assertTrue(sy.rawValue().equals("X"));
	}
}
