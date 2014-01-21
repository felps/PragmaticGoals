package cgm;

import java.util.HashSet;
import java.util.Set;

public abstract class Dependency {

	public static final String GOAL = "GOAL";
	public static final String TASK = "TASK";
	public static final String DELEGATION = "DELEGATION";
	
	private Context applicableContext;
	private QualityConstraint qc;
	protected boolean isOrDecomposition = false;
	protected HashSet<Dependency> dependencies;
	
	public void setApplicableContext(Context context) {
		applicableContext = context;
	}

	public Context getApplicableContext() {
		return applicableContext;
	}
	
	public abstract String myType();
	
	public boolean isApplicable(Context context) {
		boolean returnValue;
		if (applicableContext == context)
			returnValue = true;
		else returnValue =  false;
		
		return returnValue;
	}
	
	public Dependency isAchievable(CGM cgm, Context current, QualityConstraint qc) {
		Dependency root = cgm.getRoot();
		return root.isAchievable(current, qc);
	}
	
	private Dependency isAchievable(Context current, QualityConstraint qc) {
		if(!this.isApplicable(current)){
			return null;
		}
		
		if(this.myType().contentEquals(Dependency.TASK)){
			Task task = (Task) this;
			String metric = qc.getMetric();
			
			if(qc.abidesByQC(task.myProvidedQuality(metric, current), metric))
				return this;
			else
				return null;
		}
		
		QualityConstraint consideredQualCons = qc.stricterQC(this.getQualityConstraint());
		
		if (this.isOrDecomposition()) {
			Dependency plan;
			// create an object of the same type as this one
			// plan = this.cloneWithoutDependencies();
			for (Dependency dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					// plan.addDependency(dep);
					return plan;
				}
			}
			return null;
		}
		
		if (this.isAndDecomposition()) {
			Dependency plan;
			// create an object of the same type as this one
			// plan = this.cloneWithoutDependencies();
			for (Dependency dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					// complete = plan.addDependency(dep);
				}
				else return null;
			}
			return null;
		}
		return null;
	}

	public boolean isOrDecomposition() {
		return isOrDecomposition;
	}

	public boolean isAndDecomposition() {
		return !isOrDecomposition;
	}

	public QualityConstraint getQualityConstraint() {
		return this.qc;
	}
	public void setQualityConstraint(QualityConstraint qc) {
		this.qc = qc;
	}

	public void addDependency(Dependency goal) {
		dependencies.add(goal);
	}

	public Set<Dependency> getDependencies() {
		return dependencies;
	}

	public Set<Dependency> getApplicableDependencies(Context context) {
		
		HashSet<Dependency> applicableDeps = new HashSet<Dependency>();
		
		for (Dependency dep : dependencies) {
			if(dep.getApplicableContext() == context){
				applicableDeps.add(dep);
			}
		}
		return applicableDeps;
	}
}
