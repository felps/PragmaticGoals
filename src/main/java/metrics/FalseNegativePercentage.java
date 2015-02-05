package metrics;

public class FalseNegativePercentage extends metrics.Metric{
	
	public FalseNegativePercentage() {
		super();
		name="FalseNegative";
	}
	@Override
	public float serialComposition(float value1, float value2) {
		return Math.max(value1, value2);
	}

	@Override
	public float parallelComposition(float value1, float value2) {
		return Math.max(value1, value2);
	}
}
