package mob.model.printer;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.model.MobEntity;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobTreeBuilder;

class MobPrinterTest {

	@Test
	void test1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobEntity> res;

		MobPrinter printer = new MobPrinter();
		res = builder.run("()");
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( )"));

		printer = new MobPrinter();
		res = builder.run("(X)");
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X )"));

		res = builder.run("('X)");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( 'X )"));

		res = builder.run("( 	X  )");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X )"));

		res = builder.run("(X Y)");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X Y )"));

		res = builder.run("( X ( Y ) Z )");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X ( Y ) Z )"));

		res = builder.run("( X ''( 'Y ) Z )");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X ''( 'Y ) Z )"));

		res = builder.run("((X = 1) ( Y = X ) Z)");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( ( X = 1 ) ( Y = X ) Z )"));
	}
	
	@Test
	void test2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobEntity> res;

		MobPrinter printer = new MobPrinter();
		res = builder.run("(decl X)");
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X )"));
		
		res = builder.run("(decl X := 99)");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := 99 )"));

		res = builder.run("(decl X := ())");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := ( ) )"));

		res = builder.run("(decl X := (99 + 10) )");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := ( 99 + 10 ) )"));

		res = builder.run("(decl X := (99 + +10) )");
		printer = new MobPrinter();
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := ( 99 + 10 ) )"));

	}

	@Test
	void test3() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobEntity> res;

		MobPrinter printer = new MobPrinter();
		res = builder.run("(1 + 1)");
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( 1 + 1 )"));
		
		printer = new MobPrinter();
		res = builder.run("(((1 + 1) + 3) negated) ");
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( ( ( 1 + 1 ) + 3 ) negated )"));
		
		printer = new MobPrinter();
		res = builder.run("( () add: 4 add: 6 add: (s size) ) ");
		for (MobEntity s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( ( ) add: 4 add: 6 add: ( s size ) )"));
	}


}
