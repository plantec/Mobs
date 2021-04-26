package mob.sinterpreter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.model.MobAssign;
import mob.model.MobObject;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;

class MobInterpreterSimpleExpTest {

	@Test
	void test() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobObject> res;

		res = interpreter.run("nil");
		assertTrue(res.get(0) instanceof MobNil);
		
		res = interpreter.run("10");
		assertTrue(res.get(0) instanceof MobInteger);
		assertTrue(((MobInteger) res.get(0)).rawValue() == 10);
		
		res = interpreter.run("-10");
		assertTrue(res.get(0) instanceof MobInteger);
		assertTrue(((MobInteger) res.get(0)).rawValue() == -10);
		
		res = interpreter.run("+10");
		assertTrue(res.get(0) instanceof MobInteger);
		assertTrue(((MobInteger) res.get(0)).rawValue() == 10);
		
		res = interpreter.run("10.0");
		assertTrue(res.get(0) instanceof MobFloat);
		assertTrue(((MobFloat) res.get(0)).rawValue() == 10.0);
		
		res = interpreter.run("-10.5");
		assertTrue(res.get(0) instanceof MobFloat);
		assertTrue(((MobFloat) res.get(0)).rawValue() == -10.5);
		
		res = interpreter.run("1.40e-45f");
		assertTrue(res.get(0) instanceof MobFloat);
		
		res = interpreter.run("\"Hello world\"");
		assertTrue(res.get(0) instanceof MobString);
		assertTrue( ((MobString) res.get(0)).rawValue().equals("Hello world"));
		
		res = interpreter.run("\"\"");
		assertTrue(res.get(0) instanceof MobString);
		assertTrue( ((MobString) res.get(0)).rawValue().equals(""));
		
		res = interpreter.run("true");
		assertTrue(res.get(0) instanceof MobTrue);
		assertTrue( ((MobTrue) res.get(0)).rawValue());
		
		res = interpreter.run("false");
		assertTrue(res.get(0) instanceof MobFalse);
		assertFalse( ((MobFalse) res.get(0)).rawValue());
		
		res = interpreter.run("def");
		assertTrue(res.get(0) instanceof MobSymbol);
		assertTrue(((MobSymbol) res.get(0)).rawValue().equals("def"));
		
		res = interpreter.run("X := nil");
		assertTrue(res.size() == 3);
		assertTrue(res.get(0) instanceof MobSymbol);
		assertTrue(res.get(0).parent() == null);
		assertTrue(res.get(1) instanceof MobSymbol);
		assertTrue(res.get(1).parent() == null);
		assertTrue(res.get(2) instanceof MobNil);
		assertTrue(res.get(2).parent() == null);
	}
}
