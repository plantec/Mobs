package mob.model.primitives;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MobPrimitiveTest {

	@Test
	void testPrimitiveTypes() {
		MobIntegerDef n = new MobIntegerDef();
		MobInteger ii = n.newInstance(1);
		assertTrue(ii.rawValue() instanceof Integer);
		assertTrue(ii.rawValue() == 1);
		MobTrueDef td = new MobTrueDef();
		MobTrue t = td.newInstance();
		assertTrue(t.rawValue() instanceof Boolean);
		assertTrue(t.rawValue());
		MobFalseDef fd = new MobFalseDef();
		MobFalse f = fd.newInstance();
		assertFalse(f.rawValue());
		MobStringDef sd = new MobStringDef();
		MobString s = sd.newInstance("Hello");
		assertTrue(s.rawValue() instanceof String);
		assertTrue(s.rawValue().equals("Hello"));
		MobSymbolDef syd = new MobSymbolDef();
		MobSymbol sy = syd.newInstance("X");
		assertTrue(sy.rawValue() instanceof String);
		assertTrue(sy.rawValue().equals("X"));
	}
}
