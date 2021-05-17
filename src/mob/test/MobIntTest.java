package mob.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mob.ast.MobAstElement;
import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.model.primitives.MobInteger;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;
import mob.sinterpreter.MobMethod;

class MobIntTest {
	static MobEnvironment env = new MobEnvironment();
	static MobInterpreter interpreter = new MobInterpreter(env); 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		MobClass object = env.getClassByName("Object");
		MobClass intClass = new MobObjectClass("Int", object, env, null);
		MobMetaClass intClassClass = new MobMetaClass(intClass, object.definition(), env, object.definition().definition());
		intClass.setClass(intClassClass);
		env.recordClass(intClass);	
	}
	
	@Test
	void testIntCls()  {
		interpreter.run("( Int )");
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		MobClass intCls = (MobClass) interpreter.result().get(0);
		MobMetaClass intClsCls = (MobMetaClass) intCls.definition();
		assertTrue(intClsCls.soleInstance() == intCls);
	}
	
	@Test
	void testIntObj()  {
		MobClass intCls = env.getClassByName("Int");
		interpreter.run("( Int new )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		MobObject intObj = (MobObject) interpreter.result().get(0);
		assertTrue(intObj.definition() == intCls);
	}
	
	@Test
	void testIntClsMethod()  {
		MobClass intCls = env.getClassByName("Int");
		intCls.addMethod(new MobMethod("be:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobAstElement arg1 = ctx.pop();
				MobObject self = (MobObject) receiver;
				self.instVarAtPut(0,((MobInteger) arg1).rawValue());
				ctx.push(self);
			}
		});		
		interpreter.run("( (Int new) be: 5 )");
		assertTrue(interpreter.result().get(0) instanceof Object);
		MobObject obj = (MobObject) interpreter.result().get(0);
		assertTrue((int)obj.instVarAt(0) == 5);
	}


}
