package mob.sinterpreter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import mob.model.primitives.MobClass;
import mob.model.primitives.MobMetaClass;

class MobObjectTest {

	@Test
	void testObjectClass() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object )");
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		interpreter.run("( (Object definition) )");
		assertTrue(interpreter.result().get(0) instanceof MobMetaClass);
		interpreter.run("( (( Object definition) definition ) println )");
		assertTrue(interpreter.result().get(0) instanceof MobMetaClass);
	}

}
