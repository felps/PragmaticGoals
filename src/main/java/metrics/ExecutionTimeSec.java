package metrics;

public class ExecutionTimeSec extends Metric {

	public ExecutionTimeSec() {
		super();
		name = "TimeSeconds";
	}

	@Override
	public double serialComposition(double value1, double value2) {
		return value1 + value2;
	}

	@Override
	public double parallelComposition(double value1, double value2) {
		return Math.max(value1, value2);
	}

}
