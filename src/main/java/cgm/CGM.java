package cgm;

import cgm.util.YamlHandler;

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
		YamlHandler yaml = new YamlHandler();
		yaml.dumpToYamlFile(this);
		
	}
}
