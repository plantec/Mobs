package mob.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.ast.MobAstElement;
import mob.model.MobObject;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;

class MobInterpreterSimpleExpTest {

	@Test
	void test() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> res;

		res = interpreter.run("nil");
		assertTrue(res.get(0) instanceof MobObject);
		
		res = interpreter.run("10");
		assertTrue(res.get(0) instanceof MobObject);
		assertTrue((Integer)((MobObject) res.get(0)).rawValue() == 10);
		
		res = interpreter.run("-10");
		assertTrue(res.get(0) instanceof MobObject);
		assertTrue((Integer)((MobObject) res.get(0)).rawValue() == -10);
		
		res = interpreter.run("+10");
		assertTrue(res.get(0) instanceof MobObject);
		assertTrue((Integer)((MobObject) res.get(0)).rawValue() == 10);
		
		res = interpreter.run("10.0");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("Float")));
		assertTrue(((MobObject)res.get(0)).rawValue().equals((float)10.0));
		
		res = interpreter.run("-10.5");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("Float")));
		assertTrue(((MobObject)res.get(0)).rawValue().equals((float)-10.5));
		
		res = interpreter.run("1.40e-45f");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("Float")));
		
		res = interpreter.run("$a");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("Character")));
		assertTrue( ((MobObject) res.get(0)).rawValue().equals('a'));

		res = interpreter.run("$$");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("Character")));
		assertTrue( ((MobObject) res.get(0)).rawValue().equals('$'));
		
		res = interpreter.run("'Hello world'");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("String")));
		assertTrue( ((MobObject) res.get(0)).rawValue().equals("Hello world"));
		
		res = interpreter.run("''");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("String")));
		assertTrue( ((MobObject) res.get(0)).rawValue().equals(""));
		
		res = interpreter.run("true");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("True")));
		assertTrue( (((Boolean)((MobObject) res.get(0)).rawValue())));
		
		res = interpreter.run("false");
		assertTrue(((MobObject)res.get(0)).isKindOf(env.getClassByName("False")));
		assertFalse( (((Boolean)((MobObject) res.get(0)).rawValue())));
		
		res = interpreter.run("(var monday) (monday)");
		assertTrue(res.get(0) instanceof MobObject);
	}
}
