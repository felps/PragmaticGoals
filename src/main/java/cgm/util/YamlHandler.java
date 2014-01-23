package cgm.util;

import org.yaml.snakeyaml.Yaml;

public class YamlHandler {

	public Object parseFromYamlString(String data){
		Yaml yaml = new Yaml();
		return yaml.load(data);
	}

	public String dumpToString(Object dumped){
		Yaml yaml = new Yaml();
		return yaml.dump(dumped);
	}
	
	public void dumpToYamlFile(Object dumped) {
		dumpToString(dumped);

	}
}
