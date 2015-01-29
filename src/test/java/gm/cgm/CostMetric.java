package gm.cgm;

import metrics.Metric;

public class CostMetric extends Metric {

	public CostMetric() {
		super();
		name = "cost";
	}

	@Override
	public double serialComposition(double value1, double value2) {
		return value1 + value2;
	}

	@Override
	public double parallelComposition(double value1, double value2) {
		return value1 + value2;
	}

}
