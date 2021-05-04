package mob.model;

public abstract class MobMethod implements MobRunable {
	String [] signature;
	
	public MobMethod(String signature) {
		this.signature = signature.split("[ \t]+");
	}
	
	public String selector () {
		String sel = "";
		for (int i = 0; i < this.signature.length; i+=2) {
			sel = sel + this.signature[i];
		}
		return sel;
	}
}
