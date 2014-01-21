package cgm;

import java.util.HashMap;

public class Task extends Dependency{

	HashMap<String, Float> providedQualityLevels;
	
	@Override
	public String myType() {
		return Dependency.TASK;
	}
	
	public Task() {
		providedQualityLevels = new HashMap<String, Float>();
	}

	public void setProvidedQuality(String metric, double value) {
		providedQualityLevels.put(metric, new Float(value));
	}

	public float myProvidedQuality(String metric) {
		return providedQualityLevels.get(metric).floatValue();
	}


}
