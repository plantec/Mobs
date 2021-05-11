package mob.model.primitives;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MobPrimitiveTest {

	@Test
	void testPrimitiveTypes() {
		MobIntegerClass n = new MobIntegerClass(null,null, null, null);
		MobInteger ii = n.newInstance(1);
		assertTrue(ii.rawValue() instanceof Integer);
		assertTrue(ii.rawValue() == 1);
		MobTrueClass td = new MobTrueClass(null,null, null, null);
		MobTrue t = td.newInstance();
		assertTrue(t.rawValue() instanceof Boolean);
		assertTrue(t.rawValue());
		MobFalseClass fd = new MobFalseClass(null,null, null, null);
		MobFalse f = fd.newInstance();
		assertFalse(f.rawValue());
		MobStringClass sd = new MobStringClass(null,null, null, null);
		MobString s = sd.newInstance("Hello");
		assertTrue(s.rawValue() instanceof String);
		assertTrue(s.rawValue().equals("Hello"));
		MobSymbolClass syd = new MobSymbolClass(null,null,null, null);
		MobSymbol sy = syd.newInstance("X");
		assertTrue(sy.rawValue() instanceof String);
		assertTrue(sy.rawValue().equals("X"));
	}
}
