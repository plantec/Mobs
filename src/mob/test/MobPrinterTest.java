package mob.test;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.ast.MobAstElement;
import mob.model.primitives.MobUnit;
import mob.model.printer.MobPrinter;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobTreeBuilder;

class MobPrinterTest {

	@Test
	void test1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> res;

		MobPrinter printer = new MobPrinter();
		res = builder.run("()");
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( )"));

		printer = new MobPrinter();
		res = builder.run("(X)");
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X )"));

		res = builder.run("(`X)");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( `X )"));

		res = builder.run("(`(X) ``(Y))");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( `( X ) ``( Y ) )"));

		res = builder.run("( 	X  )");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X )"));

		res = builder.run("(X Y)");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X Y )"));

		res = builder.run("( X ( Y ) Z )");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X ( Y ) Z )"));

		res = builder.run("( X ``( `Y ) Z )");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X ``( `Y ) Z )"));

		res = builder.run("((X = 1) ( Y = X ) Z)");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( ( X = 1 ) ( Y = X ) Z )"));
	}
	
	@Test
	void test2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> res;

		MobPrinter printer = new MobPrinter();
		res = builder.run("(decl X)");
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X )"));
		
		res = builder.run("(decl X := 99)");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := 99 )"));

		res = builder.run("(decl X := ())");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := ( ) )"));

		res = builder.run("(decl X := (99 + 10) )");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := ( 99 + 10 ) )"));

		res = builder.run("(decl X := 99 + +10 )");
		printer = new MobPrinter();
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( decl X := ( 99 + 10 ) )"));

	}

	@Test
	void test3() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> res;

		MobPrinter printer = new MobPrinter();
		res = builder.run("(1 + 1)");
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( 1 + 1 )"));
		
		printer = new MobPrinter();
		res = builder.run("(((1 + 1) + 3) negated) ");
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( ( ( 1 + 1 ) + 3 ) negated )"));
		
		printer = new MobPrinter();
		res = builder.run("( (decl l := ()) (l add: 4 add: 6 add: (s size) ) (^ l) ) ");
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( ( decl l := ( ) ) ( l add: 4 add: 6 add: ( s size ) ) ( ^ l ) )"));
		printer = new MobPrinter();
		
		res = builder.run("( X := true ifTrue: [ 1 ] ifFalse: [ 2 ] ) ");
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("( X := ( true ifTrue: [ 1 ] ifFalse: [ 2 ] ) )"));
	}

	@Test
	void test4() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> res;

		MobPrinter printer = new MobPrinter();
		res = builder.run("[ a b c | (a + b) + c ]");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobUnit);
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("[ a b c | ( ( a + b ) + c ) ]"));
		
		printer = new MobPrinter();
		res = builder.run("[ a b c | (decl X) (X := (a + b) + c) (^ X) ]");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobUnit);
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("[ a b c | ( decl X )( X := ( ( a + b ) + c ) )( ^ X ) ]"));
		
		printer = new MobPrinter();
		res = builder.run("[ a b c | ^ (a + b) + c ]");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobUnit);
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("[ a b c | ( ^ ( ( a + b ) + c ) ) ]"));
		
		printer = new MobPrinter();
		res = builder.run("[ a b c | (^ (a + b) + c) ]");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobUnit);
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("[ a b c | ( ^ ( ( a + b ) + c ) ) ]"));
		
		printer = new MobPrinter();
		res = builder.run("[ a b c | ( (a + b) + c) ]");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobUnit);
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("[ a b c | ( ( a + b ) + c ) ]"));
		
		printer = new MobPrinter();
		res = builder.run("[]");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobUnit);
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("[ ]"));
		
		printer = new MobPrinter();
		res = builder.run("[ ^ ]");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobUnit);
		for (MobAstElement s : res) s.accept(printer);
		System.out.println(printer.result().toString());
		assertTrue(printer.result().toString().equals("[ ( ^ ) ]"));
	}

}
