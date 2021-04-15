package mob.model.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

import mob.model.MobAssign;
import mob.model.MobExp;
import mob.model.MobUnit;
import mob.model.MobVisitor;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;

interface Doer {
	public void execute();
}

public class MobPrinter implements MobVisitor {

	protected OutputStream stream;
	Boolean withIndentation;
	int level;
	int indentSize;

	public MobPrinter() {
		this(new ByteArrayOutputStream());
	}

	public MobPrinter(OutputStream stream) {
		this.stream = stream;
		this.level = 0;
		this.withIndentation = false;
		this.indentSize = 2;
	}

	public void withIndentation(Boolean withIndentation) {
		this.withIndentation = withIndentation;
	}

	public OutputStream result() {
		return this.stream;
	}
	
	protected void forEachSepBy(List<? extends MobExp> l, Consumer<MobExp> cons, Doer d) {
		int count = 0;
		for (MobExp e : l) {
			cons.accept(e);
			count++;
			if (count < l.size()) {
				d.execute();
			}
		}
	}

	protected void indent() {
		try {
			for (int i = 0; i < this.indentSize; i++) {
				this.stream.write((int) ' ');
			}
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	protected void space() {
		try {
			this.stream.write((int) ' ');
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	protected void write(char c) {
		try {
			this.stream.write(c);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	protected void write(String s) {
		try {
			this.stream.write(s.getBytes());
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	protected void flushQuote(int q) {
		for (int i = 0; i < q; i++)
			this.write('\'');
	}

	@Override
	public void visitTrue(MobTrue mobTrue) {
		MobVisitor.super.visitTrue(mobTrue);
		this.flushQuote(mobTrue.quote());
		this.write("true");
	}

	@Override
	public void visitFalse(MobFalse mobFalse) {
		MobVisitor.super.visitFalse(mobFalse);
		this.flushQuote(mobFalse.quote());
		this.write("false");
	}

	@Override
	public void visitFloat(MobFloat mobFloat) {
		MobVisitor.super.visitFloat(mobFloat);
		this.flushQuote(mobFloat.quote());
		this.write(mobFloat.rawValue().toString());

	}

	@Override
	public void visitInteger(MobInteger mobInteger) {
		MobVisitor.super.visitInteger(mobInteger);
		this.flushQuote(mobInteger.quote());
		this.write(mobInteger.rawValue().toString());
	}

	@Override
	public void visitNil(MobNil mobNil) {
		MobVisitor.super.visitNil(mobNil);
		this.flushQuote(mobNil.quote());
		this.write("nil");
	}

	@Override
	public void visitString(MobString mobString) {
		MobVisitor.super.visitString(mobString);
		this.flushQuote(mobString.quote());
		this.write(mobString.rawValue());
	}

	@Override
	public void visitUnit(MobUnit mobUnit) {
		MobVisitor.super.visitUnit(mobUnit);
		if (this.withIndentation && this.level > 0) {
			this.write('\n');
			for (int i = 0; i < level; i++) {
				this.indent();
			}
		}
		this.flushQuote(mobUnit.quote());
		this.write('(');
		this.write(' ');
		if (mobUnit.hasChildren()) {			
			this.level++;
			this.forEachSepBy(mobUnit.children(), s -> s.accept(this), this::space);
			this.level--;
		}
		this.write(' ');
		this.write(')');
	}

	@Override
	public void visitSymbol(MobSymbol mobSymbol) {
		MobVisitor.super.visitSymbol(mobSymbol);
		this.flushQuote(mobSymbol.quote());
		this.write(mobSymbol.rawValue());
	}

	@Override
	public void visitAssign(MobAssign mobOperation) {
		MobVisitor.super.visitAssign(mobOperation);
		this.forEachSepBy(mobOperation.children(), s -> s.accept(this), this::space);
	}

}
