package mob.sinterpreter;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class MobInterpreterMethodTest {

	@Test
	void testBinaryMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( ( 1 method: [ i | self + i ] named: \"plus:\" ) ((10 plus: 2 ) println) )");
		interpreter.run("( (((1 definition) definition) println) )");
	}

}
