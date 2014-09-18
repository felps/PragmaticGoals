package cgm;

import java.util.HashMap;
import java.util.Set;

import cgm.util.YamlHandler;

public class Task extends Refinement {

	private HashMap<String, HashMap<Context, Float>> providedQualityLevels;
	private boolean lessIsMore;

	@Override
	public int myType() {
		return Refinement.TASK;
	}

	public Task(boolean lessIsMore) {
		providedQualityLevels = new HashMap<String, HashMap<Context, Float>>();
		this.lessIsMore = lessIsMore;
	}

	public Task() {
		providedQualityLevels = new HashMap<String, HashMap<Context, Float>>();
		this.lessIsMore = false;
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

	public float myProvidedQuality(String metric, Set<Context> current) {
		float myQuality = 0;
		boolean set = false;
		for (Context context : current) {
			if (providedQualityLevels.get(metric) != null
					&& providedQualityLevels.get(metric).get(context) != null) {
				System.out.println("Setting...");
				if (!set) {
					myQuality = providedQualityLevels.get(metric).get(context)
							.floatValue();
					set = true;
				} else {
					if (lessIsMore) {
						if (myQuality > providedQualityLevels.get(metric)
								.get(context).floatValue()) {
							myQuality = providedQualityLevels.get(metric)
									.get(context).floatValue();
						}
					} else if (myQuality < providedQualityLevels.get(metric)
							.get(context).floatValue()) {
						myQuality = providedQualityLevels.get(metric)
								.get(context).floatValue();
					}
				}
			}

		}
		return myQuality;
	}

	public void parseFromYamlFile() {
		YamlHandler yaml = new YamlHandler();
		yaml.dumpToYamlFile(this);
	}

	public void dumpToYamlFile() {
		YamlHandler yaml = new YamlHandler();
		yaml.dumpToYamlFile(this);
	}
}
