package mob.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;
import mob.sinterpreter.MobMethod;
import mob.sinterpreter.MobReturnExecuted;

class MobIntTest {
	static MobEnvironment env = new MobEnvironment();
	static MobInterpreter interpreter = new MobInterpreter(env); 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		MobClass object = env.getClassByName("Object");
		MobClass intClass = new MobClass("MyInt", env, object, null);
		MobMetaClass intClassClass = new MobMetaClass(intClass, env, object.definition(), object.definition().definition());
		intClass.setClass(intClassClass);
		env.recordClass(intClass);	
	}
	
	@Test
	void testMyIntCls()  {
		interpreter.run("( MyInt )");
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		MobClass intCls = (MobClass) interpreter.result().get(0);
		MobMetaClass intClsCls = (MobMetaClass) intCls.definition();
		assertTrue(intClsCls.soleInstance() == intCls);
	}
	
	@Test
	void testMyIntObj()  {
		MobClass intCls = env.getClassByName("MyInt");
		interpreter.run("( MyInt new )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		MobObject intObj = (MobObject) interpreter.result().get(0);
		assertTrue(intObj.definition() == intCls);
	}
	
	@Test
	void testMyIntMethod()  {
		MobClass intCls = env.getClassByName("MyInt");
		/*
		 * (method (MyInt be:)
		 * 	(var arg := thiscontext pop)
		 * 	(self primInstVarAt: 0 put: arg)
		 * 	(thiscontext push: self)))  
		 */
		intCls.addMethod(new MobMethod("be:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject arg1 = (MobObject) ctx.pop();
				MobObject self = (MobObject) receiver;
				self.primValueAtPut(0,arg1.primValue());
			}
		});		
		interpreter.run("( (MyInt new) be: 5 )");
		assertTrue(interpreter.result().get(0) instanceof Object);
		MobObject obj = (MobObject) interpreter.result().get(0);
		assertTrue(obj.definition().name().equals("MyInt"));
		assertTrue((int)obj.primValue() == 5);
	}

	@Test
	void testMyIntNew()  {
		MobClass intCls = env.getClassByName("MyInt");
		/*
		 * (method ((MyInt class) new)
		 * 	(var res := super new)
		 * 	(res primInstVarAt: 0 put: 0)
		 * 	(thiscontext return: res)))  
		 */
		intCls.definition().addMethod(new MobMethod("new") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobClass self = (MobClass) receiver;
				MobObject res = self.newInstance();
				res.primValueAtPut(0,0);
				ctx.returnElement(res);
			}
		});		
		interpreter.run("(MyInt new)");
		assertTrue(interpreter.result().get(0) instanceof Object);
		MobObject obj = (MobObject) interpreter.result().get(0);
		assertTrue(obj.definition().name().equals("MyInt"));
		assertTrue((int)obj.primValue() == 0);
	}

		
	@Test
	void testIntPlus()  {
		MobClass intCls = env.getClassByName("Integer");
		MobObject intReceiver = intCls.newInstance();
		intReceiver.primValueAtPut(0, (Integer)5);
		MobObject intArg = intCls.newInstance();
		intArg.primValueAtPut(0, (Integer)5);
		
		MobMethod plus = intReceiver.definition().methodNamed("+");
		assertTrue(plus != null);
		interpreter.topContext().push(intArg);
		try {
			plus.run(interpreter.topContext(), intReceiver);
			assertTrue(false);
		} catch (MobReturnExecuted e) {
			assertTrue(true);
			MobObject intRes = (MobObject) interpreter.topContext().pop();
			assertTrue(intRes.primValue() instanceof Integer);
			Integer i = (Integer) intRes.primValue();
			assertTrue(i == 10);
		}
	}
}
