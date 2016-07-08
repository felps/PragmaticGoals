package cgm;

import cgm.workflow.Plan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Refinement {

	public static final int GOAL = 1;
	public static final int TASK = 2;
	public static final int DELEGATION = 3;

    protected ArrayList<Refinement> dependencies;
    private HashSet<Context> applicableContexts;
    private HashSet<Context> nonApplicableContexts;
	private String identifier;
    private double timeConsumedInSeconds;
    private double reliability;

	public Refinement() {
		applicableContexts = new HashSet<Context>();
		applicableContexts.add(null);
		nonApplicableContexts = new HashSet<Context>();
        dependencies = new ArrayList<Refinement>();

    }

    public double getTimeConsumed() {
        return timeConsumedInSeconds;
    }

    public void setTimeConsumed(double timeConsumedInSeconds) {
        this.timeConsumedInSeconds = timeConsumedInSeconds;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) throws Exception {
        if (reliability > 1) throw new Exception("reliability cannot be greater than one.");
        this.reliability = reliability;
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

    public abstract Plan isAchievable(Set<Context> current, Interpretation interp, String compositeMetric);

    public Plan isAchievable(Set<Context> current, Interpretation interp) {
        return isAchievable(current, interp, null);
    }

    public abstract void addDependency(Refinement goal);

    public List<Refinement> getDependencies() {
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
