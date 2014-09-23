package cgm;

import java.util.HashSet;
import java.util.Set;

public abstract class Refinement {

	public static final int GOAL = 1;
	public static final int TASK = 2;
	public static final int DELEGATION = 3;

	private HashSet<Context> applicableContexts;
	private HashSet<Context> nonApplicableContexts;

	protected boolean isOrDecomposition = false;
	protected HashSet<Refinement> dependencies;
	private String identifier;

	public Refinement() {
		applicableContexts = new HashSet<Context>();
		applicableContexts.add(null);
		nonApplicableContexts = new HashSet<Context>();
		dependencies = new HashSet<Refinement>();
	}

	public void addApplicableContext(Context context) {
		if (applicableContexts.contains(null)) {
			applicableContexts.remove(null);
		}
		applicableContexts.add(context);
	}

	public void addNonApplicableContext(Context wrongContext) {
		nonApplicableContexts.add(wrongContext);

	}

	public void addApplicableContext(HashSet<Context> contextSet) {
		applicableContexts.addAll(contextSet);
	}

	public HashSet<Context> getApplicableContext() {
		return applicableContexts;
	}

	public abstract int myType();

	public boolean isApplicable(Set<Context> current) {
		boolean returnValue = false;
		int unapplicableContextsFound = 0;
		if (applicableContexts.contains(null)) {
			returnValue = true;
		}
		if(nonApplicableContexts.size()>0){
			 returnValue = true;
		}
		
		for (Context context : current) {
			if (nonApplicableContexts.contains(context))
				return false;
			if (applicableContexts.contains(context))
				returnValue = true;
		}
		return returnValue;
	}

	public abstract Plan isAchievable(Set<Context> current, Interpretation interp);

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
				if (dep.getApplicableContext().contains(context) || dep.getApplicableContext().contains(null)) {
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
