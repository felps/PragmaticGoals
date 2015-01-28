package metrics;

public class FalseNegativePercentage extends metrics.Metric{
	
	public FalseNegativePercentage() {
		super();
		name="FalseNegative";
	}
	@Override
	public double serialComposition(double value1, double value2) {
		return Math.max(value1, value2);
	}

	@Override
	public double parallelComposition(double value1, double value2) {
		return Math.max(value1, value2);
	}
}
