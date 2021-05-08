package mob.sinterpreter;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class MobInterpreterMethodTest {

	@Test
	void testBinaryMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( ( Integer addMethod: [ i | self + i ] named: 'plus:' ) )");
		interpreter.run("((10 plus: 2 ) println) ");
		interpreter.run("( (Integer definition) println )");
		interpreter.run("( ((1 definition) ) println )");
	}
	@Test
	void testWhileTrue() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( ( Unit addMethod: [ u | (self value) ifTrue: [(u value) (self whileTrue: u) ] ] named: 'whileTrue:'))");
		interpreter.run("( (decl x := 4) ([ x > 0 ] whileTrue: [ (x println) (x := x - 1) ]  ) )");
	}

}
