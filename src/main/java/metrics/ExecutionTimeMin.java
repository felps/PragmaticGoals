package metrics;

public class ExecutionTimeMin extends Metric {

	public ExecutionTimeMin() {
		super();
		name = "TimeMinutes";
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
