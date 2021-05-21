package mob.model.primitives;

import java.util.ArrayList;
import java.util.List;

import mob.ast.MobAstElement;
import mob.ast.MobQuoted;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.MobObjectClass;
import mob.sinterpreter.MobContext;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobMethod;
import mob.sinterpreter.MobVariable;

public class MobUnit extends MobObjectClass {

	public MobUnit(String name, MobEnvironment environment, MobClass superclass, MobClass def) {
		super(name, environment, superclass, def);
	}

	public void initializePrimitives() {
		super.initializePrimitives();
		this.addMethod(new MobMethod("value") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject unit = (MobObject) receiver;
				MobUnit unitCls = (MobUnit) unit.definition();
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unitCls.code(unit);
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
			}
		});
		
		this.addMethod(new MobMethod("value:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject unit = (MobObject) receiver;
				MobUnit unitCls = (MobUnit) unit.definition();
				MobAstElement arg = ctx.pop();
				if (!unitCls.hasParameters(unit)) 
					throw new Error("0 intended formal parameters but 1 arguments actually passed");
				if ((unitCls.formalParameters(unit).size() != 1) ) {
					throw new Error(unitCls.formalParameters(unit).size() + " intended formal parameters but 1 arguments actually passed");
				}
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				newCtx.setParameterValue(0, arg);
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unitCls.code(unit);
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
			}
		});
		
		this.addMethod(new MobMethod("value:value:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject unit = (MobObject) receiver;
				MobUnit unitCls = (MobUnit) unit.definition();
				MobAstElement arg2 = ctx.pop();
				MobAstElement arg1 = ctx.pop();
				if (!unitCls.hasParameters(unit)) 
					throw new Error("0 intended formal parameters but 2 arguments actually passed");
				if ((unitCls.formalParameters(unit).size() != 2) ) {
					throw new Error(unitCls.formalParameters(unit).size() + " intended formal parameters but 2 arguments actually passed");
				}
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				newCtx.setParameterValue(0, arg1);
				newCtx.setParameterValue(1, arg2);
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unitCls.code(unit);
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
			}
		});
		this.addMethod(new MobMethod("values:") {
			public void run(MobContext ctx, MobAstElement receiver) {
				MobObject unit = (MobObject) receiver;
				MobUnit unitCls = (MobUnit) unit.definition();
				MobAstElement arg = ctx.pop();
				MobQuoted quo = (MobQuoted) arg;
				MobObject seq = (MobObject) quo.entity();
				MobContext newCtx = new MobContext(ctx.interpreter().topContext());
				newCtx.setUnit(unit);
				for (int i = 0; i < seq.allPrimValues().length; i++)
					newCtx.setParameterValue(i, (MobAstElement) seq.primValueAt(i));
				ctx.interpreter().pushContext(newCtx);
				MobAstElement e = unitCls.code(unit);
				e.accept(ctx.interpreter());
				ctx.interpreter().popContext();
				ctx.returnElement(ctx.pop());
			}
		});
		
	}
	
	@SuppressWarnings("unchecked")
	public List<String> formalParameters(MobObject unit) {
		return (List<String>) unit.primValueAt(0);
	}

	public void initParameters(MobObject unit) {
		unit.primValueAtPut(0, new ArrayList<String>());
	}
	
	public void addParameter(MobObject unit, String name) {
		this.formalParameters(unit).add(name);
	}
	
	public MobAstElement code(MobObject unit) {
		return (MobAstElement) unit.primValueAt(1);
	}
	
	public void setCode(MobObject unit, MobAstElement code) {
		unit.primValueAtPut(1, code);
	}
	
	public Boolean hasParameters(MobObject unit) {
		return !this.formalParameters(unit).isEmpty();
	}
	
	public void placeAsUnitInContext(MobObject unit, MobContext ctx) {
		List<String> formalParameters = this.formalParameters(unit);
		List<MobVariable> pl = ctx.parameters();
		for (String pname : formalParameters) {
			pl.add(new MobVariable(pname));
		}
	}
}
