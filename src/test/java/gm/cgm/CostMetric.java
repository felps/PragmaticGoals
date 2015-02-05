package gm.cgm;

import metrics.Metric;

public class CostMetric extends Metric {

	public CostMetric() {
		super();
		name = "cost";
	}

	@Override
	public float serialComposition(float value1, float value2) {
		return value1 + value2;
	}

	@Override
	public float parallelComposition(float value1, float value2) {
		return value1 + value2;
	}

}
