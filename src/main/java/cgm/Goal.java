package cgm;

import java.util.HashSet;

import cgm.util.YamlHandler;

public class Goal extends Refinement{

	public final static boolean OR = true;
	public final static boolean AND = false;
	
	public Goal(boolean isOrDecomposition) {
		dependencies = new HashSet<Refinement>();
		this.isOrDecomposition = isOrDecomposition;
	}
	
	@Override
	public int myType() {
		return Refinement.GOAL;
	}


	public void parseFromYamlFile(){
		
	}

	public void dumpToYamlFile(){
		YamlHandler yaml = new YamlHandler();
		yaml.dumpToYamlFile(this);
		
	}

}
