package mob.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import mob.model.primitives.MobInteger;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;

class MobObjectSlotsTest {

	@Test
	void testSlotAccessing() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( (Object addSubclassNamed: 'MyObject') )");
		interpreter.run("( (MyObject addSubclassNamed: 'MyObject2') )");
		interpreter.run("( (MyObject addSlotNamed: 'x') )");
		interpreter.run("( (decl o ) )");
		interpreter.run("( (o := MyObject new) )");
		interpreter.run("( (o instVarAt: 0 put: 9) )");
		interpreter.run("( (o instVarAt: 0) )");
		System.out.println(interpreter.result());
		assertTrue(interpreter.result().get(0) instanceof MobInteger);
		MobInteger i = (MobInteger) interpreter.result().get(0);
		assertTrue(i.rawValue() == 9);
	}

}
