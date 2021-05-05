package mob.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class MobMethodTest {

	@Test
	void test() {
		MobMethod m = new MobMethod("+") {};
		assertTrue(m.selector().equals("+"));
		
		m = new MobMethod("xyz") {};
		assertTrue(m.selector().equals("xyz"));
		
		System.out.println(Arrays.toString("a b  c 	d			e".split("[ \t]+")));
		System.out.println(Arrays.toString("a;b;c;d".split("(?<=;)")));
		System.out.println(Arrays.toString("a;b;c;d".split("(?=;)")));
		System.out.println(Arrays.toString("a;b;c;d".split("((?<=;)|(?=;))")));
		
		m = new MobMethod("x:y:z:") {};
		System.out.println(m.selector());
		assertTrue(m.selector().equals("x:y:z:"));
		
	}

}
