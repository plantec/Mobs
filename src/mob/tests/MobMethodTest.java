package mob.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import mob.ast.MobAstElement;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobMethod;

class MobMethodTest {

	@Test
	void test() {
		MobMethod m = new MobMethod("+") {
			@Override
			public void run(MobContext ctx, MobAstElement mobObject) {
			}
		};
		assertTrue(m.selector().toString().equals("+"));

		m = new MobMethod("xyz") {
			@Override
			public void run(MobContext ctx, MobAstElement mobObject) {
				// TODO Auto-generated method stub

			}
		};
		assertTrue(m.selector().toString().equals("xyz"));

		System.out.println(Arrays.toString("a b  c 	d			e".split("[ \t]+")));
		System.out.println(Arrays.toString("a;b;c;d".split("(?<=;)")));
		System.out.println(Arrays.toString("a;b;c;d".split("(?=;)")));
		System.out.println(Arrays.toString("a;b;c;d".split("((?<=;)|(?=;))")));

		m = new MobMethod("x:y:z:") {
			@Override
			public void run(MobContext ctx, MobAstElement mobObject) {
				// TODO Auto-generated method stub

			}
		};
		System.out.println(m.selector());
		assertTrue(m.selector().toString().equals("x:y:z:"));

	}

}
