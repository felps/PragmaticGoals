package cgm;

public class ExecutionTimeMin extends Metric{

	public static final String unity = "min";
	
	private int value;
	
	public int getValue(){
		return value;
	}

	public int serialDecomposition(ExecutionTimeMin t){
		if(t.getValue() > this.getValue()){
			return t.getValue();
		}
		else {
			return this.getValue();
		}
	}
	
	public int parallelDecomposition(ExecutionTimeMin t){
		if(t.getValue() > this.getValue()){
			return t.getValue();
		}
		else {
			return this.getValue();
		}
	}
}
