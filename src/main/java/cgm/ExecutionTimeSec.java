package cgm;

public class ExecutionTimeSec extends Metric{
	
	public static final String unity = "s";
	
	private int value;
	
	public int getValue(){
		return value;
	}
	
	public int parallelCompose(ExecutionTimeSec t){
		if(t.getValue() > this.getValue()){
			return t.getValue();
		}
		else{
			return this.getValue();
		}
	}
	
	public int serialCompose(ExecutionTimeSec t){
		if(t.getValue() > this.getValue()){
			return t.getValue();
		}
		else{
			return this.getValue();
		}
	}
}
