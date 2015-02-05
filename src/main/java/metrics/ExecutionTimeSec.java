package metrics;

public class ExecutionTimeSec extends Metric {

	public ExecutionTimeSec() {
		super();
		name = "TimeSeconds";
	}

	@Override
	public float serialComposition(float value1, float value2) {
		return value1 + value2;
	}

	@Override
	public float parallelComposition(float value1, float value2) {
		return Math.max(value1, value2);
	}

}
