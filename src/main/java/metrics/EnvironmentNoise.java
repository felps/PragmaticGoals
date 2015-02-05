package metrics;

public class EnvironmentNoise extends Metric {

	public EnvironmentNoise() {
		super();
		name = "Noise";
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