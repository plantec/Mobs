package mob.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mob.model.MobObject;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;

class MobPrimitiveTest {

	@Test
	void testPrimitiveTypes() {
		MobInteger n = new MobInteger(null,null, null, null);
		MobObject ii = n.newInstance();
		ii.primValueAtPut(0, 1);
		assertTrue(ii.primValue() instanceof Integer);
		assertTrue((Integer)ii.primValue() == 1);
		MobTrue td = new MobTrue(null,null, null, null);
		MobObject t = td.newInstance();
		assertTrue(t.primValue() instanceof Boolean);
		assertTrue((Boolean)t.primValue());
		MobFalse fd = new MobFalse(null,null, null, null);
		MobObject f = fd.newInstance();
		assertFalse((Boolean)f.primValue());
		MobString sd = new MobString(null,null, null, null);
		MobObject s = sd.newInstance();
		s.primValueAtPut(0, "Hello");
		assertTrue(s.primValue() instanceof String);
		assertTrue(s.primValue().equals("Hello"));
		MobSymbol syd = new MobSymbol(null,null,null, null);
		MobObject sy = syd.newInstance();
		sy.primValueAtPut(0, "X");
		assertTrue(sy.primValue() instanceof String);
		assertTrue(sy.primValue().equals("X"));
	}
}
