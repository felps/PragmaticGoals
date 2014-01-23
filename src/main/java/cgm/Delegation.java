package cgm;

import cgm.util.YamlHandler;

public class Delegation extends Refinement{

	@Override
	public int myType() {
		return Refinement.DELEGATION;
	}


	public void parseFromYamlFile(){
		
	}

	public void dumpToYamlFile(){
		YamlHandler yaml = new YamlHandler();
		yaml.dumpToYamlFile(this);
	}
}
