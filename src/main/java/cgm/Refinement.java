package cgm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Refinement {

	public static final int GOAL = 1;
	public static final int TASK = 2;
	public static final int DELEGATION = 3;
	
	private Context applicableContext;
	private HashMap<Context, QualityConstraint> constraints;
	protected boolean isOrDecomposition = false;
	protected HashSet<Refinement> dependencies;
	private String identifier;
	
	public Refinement() {
		constraints= new HashMap<Context, QualityConstraint>();
		
	}
	
	public void setApplicableContext(Context context) {
		applicableContext = context;
//		dependencies = new HashSet<Refinement>();
	}

	public Context getApplicableContext() {
		return applicableContext;
	}
	
	public abstract int myType();
	
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
		
		if(this.myType() == Refinement.TASK){
			Task task = (Task) this;
			String metric = qc.getMetric();
			
			if(qc.abidesByQC(task.myProvidedQuality(metric, current), metric))
				return this;
			else
				return null;
		}
		QualityConstraint consideredQualCons ;
		
		if(this.constraints.size() > 0)
			consideredQualCons = qc.stricterQC(this.getQualityConstraint(current));
		else
			consideredQualCons = qc;
		
		if (this.isOrDecomposition()) {
			Refinement plan, complete;
			// create an object of the same type as this one
			complete = this.cloneWithoutDependencies();
			for (Refinement dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					complete.addDependency(plan);
					return complete;
				}
			}
			return null;
		}
		
		if (this.isAndDecomposition()) {
			Refinement complete, plan;
			// create an object of the same type as this one
			complete = this.cloneWithoutDependencies();
			for (Refinement dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					complete.addDependency(plan);
				}
				else {
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
			 clone.setQualityConstraint(constraints.values());
			 return clone;
		case Refinement.DELEGATION:
			Delegation delegation = new Delegation();
			delegation.setApplicableContext(getApplicableContext());
			delegation.setIdentifier(identifier);
			delegation.setQualityConstraint(getAllQualityConstraint());
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

	public QualityConstraint getQualityConstraint(Context current) {
		return this.constraints.get(current);
	}
	
	public Collection<QualityConstraint> getAllQualityConstraint() {
		return this.constraints.values();
	}
	
	public void setQualityConstraint(QualityConstraint qc) {
		this.constraints.put(qc.getApplicableContext(), qc);
	}

	public void setQualityConstraint(Collection<QualityConstraint> qcs) {
		for (QualityConstraint qualityConstraint : qcs) {
			this.constraints.put(qualityConstraint .getApplicableContext(), qualityConstraint );
		}
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
