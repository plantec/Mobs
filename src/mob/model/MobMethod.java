package mob.model;

public abstract class MobMethod implements MobRunable {
	String [] signatureElements;
	
	public MobMethod(String signature) {
		this.signatureElements = signature.split("(?<=:)");
	}
	
	public String[] signatureElements() {
		return this.signatureElements;
	}
		
	public String selector () {
		String sel = "";
		for (String s : signatureElements) {
			sel = sel + s;
		}
		return sel;
	}
}
