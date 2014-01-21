package cgm;

import java.util.HashMap;

public class Task extends Dependency {

	HashMap<String, HashMap<Context, Float>> providedQualityLevels;

	@Override
	public String myType() {
		return Dependency.TASK;
	}

	public Task() {
		providedQualityLevels = new HashMap<String, HashMap<Context, Float>>();
	}

	public void setProvidedQuality(Context context, String metric, double value) {
		HashMap<Context, Float> map;

		if (providedQualityLevels.containsKey(metric)) {
			map = providedQualityLevels.get(metric);
			map.put(context, new Float(value));
		} else {
			map = new HashMap<Context, Float>();
			map.put(context, new Float(value));
			providedQualityLevels.put(metric, map);
		}
	}

	public float myProvidedQuality(String metric, Context context) {
		return providedQualityLevels.get(metric).get(context).floatValue();
	}

}
