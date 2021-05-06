package mob.model.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

import mob.model.MobAssign;
import mob.model.MobBinaryMessage;
import mob.model.MobEntity;
import mob.model.MobKeywordMessage;
import mob.model.MobParameterList;
import mob.model.MobQuoted;
import mob.model.MobReturn;
import mob.model.MobSequence;
import mob.model.MobUnaryMessage;
import mob.model.MobUnit;
import mob.model.MobVarDecl;
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
		MobVisitor.super.visitTrue(mobTrue);
		this.write("true");
	}

	@Override
	public void visitFalse(MobFalse mobFalse) {
		MobVisitor.super.visitFalse(mobFalse);
		this.write("false");
	}

	@Override
	public void visitFloat(MobFloat mobFloat) {
		MobVisitor.super.visitFloat(mobFloat);
		this.write(mobFloat.rawValue().toString());

	}

	@Override
	public void visitInteger(MobInteger mobInteger) {
		MobVisitor.super.visitInteger(mobInteger);
		this.write(mobInteger.rawValue().toString());
	}

	@Override
	public void visitNil(MobNil mobNil) {
		MobVisitor.super.visitNil(mobNil);
		this.write("nil");
	}

	@Override
	public void visitString(MobString mobString) {
		MobVisitor.super.visitString(mobString);
		this.write(mobString.rawValue());
	}

	@Override
	public void visitSymbol(MobSymbol mobSymbol) {
		MobVisitor.super.visitSymbol(mobSymbol);
		this.write(mobSymbol.rawValue());
	}

	@Override
	public void visitAssign(MobAssign mobAssign) {
		MobVisitor.super.visitAssign(mobAssign);
		if (mobAssign.showParenthesis()) {
			this.write('(');
			this.write(' ');
		}
		mobAssign.left().accept(this);
		this.write(' ');
		this.write(":=");
		this.write(' ');
		mobAssign.right().accept(this);
		if (mobAssign.showParenthesis()) {
			this.write(' ');
			this.write(')');
		}
	}

	@Override
	public void visitVarDecl(MobVarDecl mobVarDecl) {
		MobVisitor.super.visitVarDecl(mobVarDecl);
		this.write('(');
		this.write(' ');
		this.write("decl");
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
		MobVisitor.super.visitReturn(mobReturn);
		if (mobReturn.showParenthesis()) {
			this.write('(');
			this.write(' ');
		}
		this.write("^");
		if (mobReturn.returned() != null) {
			this.write(' ');
			mobReturn.returned().accept(this);
		}
		if (mobReturn.showParenthesis()) {
			this.write(' ');
			this.write(')');
		}
	}

	@Override
	public void visitUnaryMessage(MobUnaryMessage mobUnaryMessage) {
		MobVisitor.super.visitUnaryMessage(mobUnaryMessage);
		MobEntity receiver = mobUnaryMessage.receiver();
		if (mobUnaryMessage.showParenthesis()) {
			this.write('(');
			this.write(' ');
		}
		receiver.accept(this);
		this.write(' ');
		this.write(mobUnaryMessage.keyword());
		if (mobUnaryMessage.showParenthesis()) {
			this.write(' ');
			this.write(')');
		}
	}

	@Override
	public void visitBinaryMessage(MobBinaryMessage mobBinaryMessage) {
		MobVisitor.super.visitBinaryMessage(mobBinaryMessage);
		MobEntity receiver = mobBinaryMessage.receiver();
		MobEntity arg = mobBinaryMessage.argument();
		if (mobBinaryMessage.showParenthesis()) {
			this.write('(');
			this.write(' ');
		}
		receiver.accept(this);
		this.write(' ');
		this.write(mobBinaryMessage.operator());
		this.write(' ');
		arg.accept(this);
		if (mobBinaryMessage.showParenthesis()) {
			this.write(' ');
			this.write(')');
		}
	}

	@Override
	public void visitKeywordMessage(MobKeywordMessage mobKeywordMessage) {
		MobVisitor.super.visitKeywordMessage(mobKeywordMessage);
		MobEntity receiver = mobKeywordMessage.receiver();
		if (mobKeywordMessage.showParenthesis()) {
			this.write('(');
			this.write(' ');
		}
		receiver.accept(this);
		this.write(' ');
		int idx = 0;
		for (String kw : mobKeywordMessage.keywords()) {
			this.write(kw);
			this.write(' ');
			mobKeywordMessage.arguments()[idx++].accept(this);
			if (idx < mobKeywordMessage.keywords().length) {
				this.write(' ');
			}
		}
		if (mobKeywordMessage.showParenthesis()) {
			this.write(' ');
			this.write(')');
		}
	}

	@Override
	public void visitSequence(MobSequence mobSequence) {
		MobVisitor.super.visitSequence(mobSequence);
		this.write('(');
		this.write(' ');
		for (MobEntity e : mobSequence.children()) {
			e.accept(this);
			this.write(' ');
		}
		this.write(')');
	}

	@Override
	public void visitUnit(MobUnit mobUnit) {
		MobVisitor.super.visitUnit(mobUnit);
		this.write('[');
		if (mobUnit.hasParameters()) {
			this.write(' ');
			mobUnit.plist().accept(this);
		}
		if (!mobUnit.code().isEmpty()) {
			this.write(' ');
			for (MobEntity e : mobUnit.code())
				e.accept(this);
		}
		this.write(" ]");
	}

	@Override
	public void visitQuoted(MobQuoted mobQuoted) {
		MobVisitor.super.visitQuoted(mobQuoted);
		this.write('\'');
		mobQuoted.entity().accept(this);
	}

	@Override
	public void visitParameterList(MobParameterList mobParameterList) {
		MobVisitor.super.visitParameterList(mobParameterList);
		this.write('{');
		this.write(' ');
		for (int i = 0; i < mobParameterList.size(); i++) {
			this.write(mobParameterList.get(i));
			this.write(' ');
		}
		this.write('}');
	}
	
}
