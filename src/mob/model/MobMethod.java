package mob.model;

public abstract class MobMethod implements MobRunable {
	String [] signature;
	
	public MobMethod(String signature) {
		this.signature = signature.split("[ \t]+");
	}
	
	public String[] selectorElements() {
		String [] r = new String[this.signature.length/2];
		for (int i = 0, j = 0; i < this.signature.length; i+=2, j++) {
			r[j] = this.signature[i];
		}
		return r;
	}
	
	public String[] arguments() {
		String [] r = new String[this.signature.length/2];
		for (int i = 1, j = 0; i < this.signature.length; i+=2, j++) {
			r[j] = this.signature[i];
		}
		return r;
	}
	
	public String selector () {
		String sel = "";
		for (int i = 0; i < this.signature.length; i+=2) {
			sel = sel + this.signature[i];
		}
		return sel;
	}
}
