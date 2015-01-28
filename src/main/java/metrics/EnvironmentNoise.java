package metrics;

public class EnvironmentNoise extends Metric {

	public EnvironmentNoise() {
		super();
		name = "Noise";
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