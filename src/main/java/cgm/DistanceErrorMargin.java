package cgm;

public class DistanceErrorMargin extends Metric{
	
	public static final String unity = "m";
	
	private int value;
	
	public int getValue(){
		return value;
	}
	
	public int serialCompose(DistanceErrorMargin m2){
		if (m2.getValue() > this.getValue()){
			return m2.getValue();
		}
		else
			return this.getValue();
	}
	
	public int parallelCompose(DistanceErrorMargin m2){
		if (m2.getValue() > this.getValue()){
			return m2.getValue();
		}
		else
			return this.getValue();
	}
}
