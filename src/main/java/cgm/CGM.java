package cgm;

import java.util.Set;

public class CGM {

	private Refinement rootGoal;
	
	public void setRoot(Refinement root) {
		this.rootGoal = root;
		
	}

	public Refinement getRoot() {
		return rootGoal;
	}
	
	public Plan isAchievable(Set<Context> current, Interpretation interp){
		return rootGoal.isAchievable(current, interp);
	}
	public void parseFromYamlFile(){
		
	}

//	public void dumpToYamlFile(){
//		YamlHandler yaml = new YamlHandler();
//		yaml.dumpToYamlFile(this);
//		
//	}
}
