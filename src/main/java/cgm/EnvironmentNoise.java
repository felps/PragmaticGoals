package cgm;

public class EnvironmentNoise extends Metric{
	
	public static final String unity = "dB";
	
	private int value;
	
	public int getValue(){
		return value;
	}
	
	public int serialDecomposition(EnvironmentNoise n){
		if(n.getValue() > this.getValue()){
			return n.getValue();
		}
		else {
			return this.getValue();
		}
	}
	
	public int parallelDecomposition(EnvironmentNoise n){
		if(n.getValue() > this.getValue()){
			return n.getValue();
		}
		else {
			return this.getValue();
		}
	}
}
