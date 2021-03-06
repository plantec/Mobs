package mob.test;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;

class MobInterpreterMethodTest {

	@Test
	void testBinaryMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( ( Integer addMethod: [ i | ^ self + i ] named: 'plus:' ) )");
		interpreter.run("((10 plus: 2 ) println) ");
		interpreter.run("( (Integer class) println )");
		interpreter.run("( ((1 class) ) println )");
	}
	@Test
	void testWhileTrue() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( ( Unit addMethod: [ uu | (self value) ifTrue: [(uu value) (self whileTrue: uu) ] ] named: 'whileTrue:'))");
		interpreter.run("( (var x := 4) ([ x > 0 ] whileTrue: [ (x println) (x := x - 1) ]  ) )");
	}

}
