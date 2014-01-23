package cgm;

import org.yaml.snakeyaml.Yaml;

public class CGM {

	private Refinement rootGoal;
	
	public void setRoot(Refinement root) {
		this.rootGoal = root;
		
	}

	public Refinement getRoot() {
		return rootGoal;
	}

	public void parseFromYamlFile(){
		
	}

	public void dumpToYamlFile(){
		Yaml yaml = new Yaml();
		System.out.println();
	}
}
