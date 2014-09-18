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
	}

	public Context getApplicableContext() {
		return applicableContext;
	}
	
	public abstract int myType();
	
	public boolean isApplicable(Set<Context> current) {
		boolean returnValue = false;
		for (Context context : current) {
			if (applicableContext == context)
				returnValue = true;
		}
		return returnValue;
	}
	
	public Plan isAchievable(CGM cgm, Set<Context> current, QualityConstraint qc) {
		Refinement root = cgm.getRoot();
		return root.isAchievable(current, qc);
	}
	
	public Plan isAchievable(Set<Context> current, QualityConstraint qc) {
		if(!this.isApplicable(current)){
			return null;
		}
		if(this.myType() == Refinement.TASK){
			Task task = (Task) this;
			String metric = qc.getMetric();
			
			if(qc.abidesByQC(task.myProvidedQuality(metric, current), metric)){
				return new Plan(task);
			} else {
				return null;
			}
		}
		QualityConstraint consideredQualCons ;
		
		if(this.constraints.size() > 0 && this.getQualityConstraint(current) != null)
			consideredQualCons = qc.stricterQC(this.getQualityConstraint(current));
		else
			consideredQualCons = qc;

		if (this.isOrDecomposition()) {
			Plan plan;
			for (Refinement dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					return plan;
				}
			}
			return null;
		}

		if (this.isAndDecomposition()) {
			Plan complete, plan;
			complete = new Plan();
			for (Refinement dep : this.getApplicableDependencies(current)) {
				plan = dep.isAchievable(current, consideredQualCons);
				if (plan != null) {
					complete.add(plan);
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

	public QualityConstraint getQualityConstraint(Set<Context> current) {
		QualityConstraint qc = null, stricter = null;
		
		for (Context context : current) {
			qc = this.constraints.get(context);
			if (qc != null){
				if (stricter == null){
					stricter = qc;
				} else {
					stricter = qc.stricterQC(stricter);
				}
			}
		}
		return stricter;
	}
	
	public Collection<QualityConstraint> getAllQualityConstraint() {
		return this.constraints.values();
	}
	
	public void addQualityConstraint(QualityConstraint qc) {
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

	public Set<Refinement> getApplicableDependencies(Set<Context> current) {
		
		HashSet<Refinement> applicableDeps = new HashSet<Refinement>();
		
		for (Refinement dep : dependencies) {
			for (Context context : current) {
				if(dep.getApplicableContext() == context){
					applicableDeps.add(dep);
				}
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
