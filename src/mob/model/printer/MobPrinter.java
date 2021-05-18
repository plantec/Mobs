package mob.model.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

import mob.ast.MobAssign;
import mob.ast.MobAstElement;
import mob.ast.MobInterpretableVisitor;
import mob.ast.MobBinaryMessage;
import mob.ast.MobEntity;
import mob.ast.MobKeywordMessage;
import mob.ast.MobQuoted;
import mob.ast.MobReturn;
import mob.ast.MobUnaryMessage;
import mob.ast.MobVarDecl;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;
import mob.model.primitives.MobUnit;
import mob.sinterpreter.MobReturnExecuted;

interface Doer {
	public void execute();
}

public class MobPrinter implements MobInterpretableVisitor {

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

	protected void forEachSepBy(List<? extends MobEntity> l, Consumer<MobEntity> cons, Doer d) {
		int count = 0;
		for (MobEntity e : l) {
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

	@Override
	public void visitTrue(MobTrue mobTrue) {
		MobInterpretableVisitor.super.visitTrue(mobTrue);
		this.write("true");
	}

	@Override
	public void visitFalse(MobFalse mobFalse) {
		MobInterpretableVisitor.super.visitFalse(mobFalse);
		this.write("false");
	}

	@Override
	public void visitFloat(MobFloat mobFloat) {
		MobInterpretableVisitor.super.visitFloat(mobFloat);
		this.write(mobFloat.rawValue().toString());

	}

	@Override
	public void visitInteger(MobInteger mobInteger) {
		MobInterpretableVisitor.super.visitInteger(mobInteger);
		this.write(mobInteger.rawValue().toString());
	}

	@Override
	public void visitString(MobString mobString) {
		MobInterpretableVisitor.super.visitString(mobString);
		this.write(mobString.rawValue());
	}

	@Override
	public void visitSymbol(MobSymbol mobSymbol) {
		MobInterpretableVisitor.super.visitSymbol(mobSymbol);
		this.write(mobSymbol.rawValue());
	}

	@Override
	public void visitAssign(MobAssign mobAssign) {
		MobInterpretableVisitor.super.visitAssign(mobAssign);
		this.write('(');
		this.write(' ');
		mobAssign.left().accept(this);
		this.write(' ');
		this.write(":=");
		this.write(' ');
		mobAssign.right().accept(this);
		this.write(' ');
		this.write(')');
	}

	@Override
	public void visitVarDecl(MobVarDecl mobVarDecl) {
		MobInterpretableVisitor.super.visitVarDecl(mobVarDecl);
		this.write('(');
		this.write(' ');
		this.write("var");
		this.write(' ');
		this.write(mobVarDecl.name());
		this.write(' ');
		if (mobVarDecl.initialValue() != null) {
			this.write(":=");
			this.write(' ');
			mobVarDecl.initialValue().accept(this);
			this.write(' ');
		}
		this.write(')');
	}

	@Override
	public void visitReturn(MobReturn mobReturn) {
		try {
			MobInterpretableVisitor.super.visitReturn(mobReturn);
		} catch (MobReturnExecuted e) {
			e.printStackTrace();
		}
		this.write('(');
		this.write(' ');
		this.write("^");
		if (mobReturn.returned() != null) {
			this.write(' ');
			mobReturn.returned().accept(this);
		}
		this.write(' ');
		this.write(')');
	}

	@Override
	public void visitUnaryMessage(MobUnaryMessage mobUnaryMessage) {
		MobInterpretableVisitor.super.visitUnaryMessage(mobUnaryMessage);
		MobAstElement receiver = mobUnaryMessage.receiver();
		this.write('(');
		this.write(' ');
		receiver.accept(this);
		this.write(' ');
		this.write(mobUnaryMessage.keyword());
		this.write(' ');
		this.write(')');
	}

	@Override
	public void visitBinaryMessage(MobBinaryMessage mobBinaryMessage) {
		MobInterpretableVisitor.super.visitBinaryMessage(mobBinaryMessage);
		MobAstElement receiver = mobBinaryMessage.receiver();
		MobAstElement arg = mobBinaryMessage.argument();
		this.write('(');
		this.write(' ');
		receiver.accept(this);
		this.write(' ');
		this.write(mobBinaryMessage.operator().rawValue());
		this.write(' ');
		arg.accept(this);
		this.write(' ');
		this.write(')');
	}

	@Override
	public void visitKeywordMessage(MobKeywordMessage mobKeywordMessage) {
		MobInterpretableVisitor.super.visitKeywordMessage(mobKeywordMessage);
		MobAstElement receiver = mobKeywordMessage.receiver();
		this.write('(');
		this.write(' ');
		receiver.accept(this);
		this.write(' ');
		int idx = 0;
		for (MobSymbol kw : mobKeywordMessage.keywords()) {
			this.write(kw.rawValue());
			this.write(' ');
			mobKeywordMessage.arguments()[idx++].accept(this);
			if (idx < mobKeywordMessage.keywords().length) {
				this.write(' ');
			}
		}
		this.write(' ');
		this.write(')');
	}

	@Override
	public void visitSequence(MobSequence mobSequence) {
		MobInterpretableVisitor.super.visitSequence(mobSequence);
		this.write('(');
		this.write(' ');
		for (MobAstElement e : mobSequence.children()) {
			e.accept(this);
			this.write(' ');
		}
		this.write(')');
	}

	@Override
	public void visitUnit(MobUnit mobUnit) {
		MobInterpretableVisitor.super.visitUnit(mobUnit);
		this.write('[');
		if (mobUnit.hasParameters()) {
			this.write(" ");
			for (String p : mobUnit.parameters()) {
				this.write(p);
				this.write(' ');
			}
			this.write('|');
		}
		MobAstElement code = mobUnit.code();
		if (code != null) {
			this.write(' ');
			if (code instanceof MobSequence ) {
				MobSequence seq = (MobSequence) code;
				for (MobAstElement e : seq.children())
					e.accept(this);
			} else {
				code.accept(this);
			}
		}
		this.write(" ]");
	}

	@Override
	public void visitQuoted(MobQuoted mobQuoted) {
		MobInterpretableVisitor.super.visitQuoted(mobQuoted);
		this.write('`');
		mobQuoted.entity().accept(this);
	}

}
