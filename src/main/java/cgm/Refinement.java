package cgm;

import java.sql.Ref;
import java.util.HashSet;
import java.util.Set;

public abstract class Refinement {

	public static final String GOAL = "GOAL";
	public static final String TASK = "TASK";
	public static final String DELEGATION = "DELEGATION";
	
	private Context applicableContext;
	private QualityConstraint qc;
	protected boolean isOrDecomposition = false;
	protected HashSet<Refinement> dependencies;
	private String identifier;
	
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
	
	public Refinement isAchievable(CGM cgm, Context current, QualityConstraint qc) {
		Refinement root = cgm.getRoot();
		return root.isAchievable(current, qc);
	}
	
	public Refinement isAchievable(Context current, QualityConstraint qc) {
		if(!this.isApplicable(current)){
			return null;
		}
		
		if(this.myType().contentEquals(Refinement.TASK)){
			Task task = (Task) this;
			String metric = qc.getMetric();
			
			if(qc.abidesByQC(task.myProvidedQuality(metric, current), metric))
				return this;
			else
				return null;
		}
		QualityConstraint consideredQualCons ;
		
		if(this.qc != null)
			consideredQualCons = qc.stricterQC(this.getQualityConstraint());
		else
			consideredQualCons = qc;
		
		if (this.isOrDecomposition()) {
			Refinement plan, complete;
			// create an object of the same type as this one
			complete = this.cloneWithoutDependencies();
			System.out.println("Object Cloned");
			for (Refinement dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					System.out.println("Plan is not null");
					complete.addDependency(plan);
					return complete;
				}
				System.out.println("plan is null");
			}
			return null;
		}
		
		if (this.isAndDecomposition()) {
			Refinement complete, plan;
			System.out.println("AND-Decomp");
			// create an object of the same type as this one
			complete = this.cloneWithoutDependencies();
			System.out.println("Object Cloned");
			for (Refinement dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					System.out.println("Plan is not null");
					complete.addDependency(plan);
				}
				else {
					System.out.println("plan is null");
					return null;
				}
			}
			return complete;
		}
		return null;
	}

	public Refinement cloneWithoutDependencies() {

		switch(myType()){
		case Refinement.GOAL:
			 Goal clone = new Goal(this.isOrDecomposition);
			 clone.setApplicableContext(applicableContext);
			 clone.setIdentifier(this.getIdentifier());
			 clone.setQualityConstraint(qc);
			 return clone;
		case Refinement.DELEGATION:
			Delegation delegation = new Delegation();
			delegation.setApplicableContext(getApplicableContext());
			delegation.setIdentifier(identifier);
			delegation.setQualityConstraint(getQualityConstraint());
			return delegation;
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

	public void addDependency(Refinement goal) {
		dependencies.add(goal);
	}

	public Set<Refinement> getDependencies() {
		return dependencies;
	}

	public Set<Refinement> getApplicableDependencies(Context context) {
		
		HashSet<Refinement> applicableDeps = new HashSet<Refinement>();
		
		for (Refinement dep : dependencies) {
			if(dep.getApplicableContext() == context){
				applicableDeps.add(dep);
			}
		}
		return applicableDeps;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
