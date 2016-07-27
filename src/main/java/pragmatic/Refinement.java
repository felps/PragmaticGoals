package pragmatic;

import pragmatic.workflow.Plan;

import java.util.HashSet;
import java.util.Set;

public abstract class Refinement {

    private HashSet<Context> applicableContexts;
    private HashSet<Context> nonApplicableContexts;
	private String identifier;
    private double timeConsumedInSeconds;
    private double reliability;

	public Refinement() {
		applicableContexts = new HashSet<Context>();
		applicableContexts.add(null);
		nonApplicableContexts = new HashSet<Context>();
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

    public HashSet<Context> getApplicableContexts() {
        return applicableContexts;
	}


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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

    public abstract int size();

}