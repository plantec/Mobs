package mob.sinterpreter;

import static org.junit.Assert.assertTrue;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;

class MobInterpreterStatementTest {

	@Test
	void testAssign1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		Object res;

		res = interpreter.run("( X := nil )");
		assertTrue(res instanceof MobVariable);
		MobVariable var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobNil);

		res = interpreter.run("( X := 1 )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);

		res = interpreter.run("( X := (1) )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);

		res = interpreter.run("( X := ((1)) )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);

		res = interpreter.run("( X := (Y := 1) )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);
		//assertTrue(interpreter.context.getVariableByName("X") != null);
		//MobVariable x = (MobVariable) interpreter.context.getVariableByName("X");
		//assertTrue(x != null);
		//MobInteger p = (MobInteger) x.value();
		//assertTrue(p.rawValue() == 1);
	}

	@Test
	void testAssign2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		Object res;
		MobVariable var;

		res = interpreter.run("( (X := 1) (Y := X) ) ");
		System.out.println(res);
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("Y"));
	}

}
