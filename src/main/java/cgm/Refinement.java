package cgm;

import java.util.HashSet;
import java.util.Set;

import workflow.datatypes.Workflow;

public abstract class Refinement {

	public static final int GOAL = 1;
	public static final int TASK = 2;
	public static final int DELEGATION = 3;

	private HashSet<Context> applicableContexts;
	private HashSet<Context> nonApplicableContexts;

	protected boolean isOrDecomposition = false;
	private String identifier;

	public Refinement() {
		applicableContexts = new HashSet<Context>();
		applicableContexts.add(null);
		nonApplicableContexts = new HashSet<Context>();
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
	public abstract Workflow workflow(Set<Context> context) throws EmptyWorkflow;

	public boolean isApplicable(Set<Context> current) {
		boolean returnValue = false;
		if (current == null) {
			current = setUpNullContext();
		}

		if (applicableContexts.contains(null)) {
			returnValue = true;
		}

		if (nonApplicableContexts.size() > 0) {
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

	protected Set<Context> setUpNullContext() {
		Set<Context> current;
		current = new HashSet<Context>();
		current.add(null);
		return current;
	}

	public abstract Plan isAchievable(Set<Context> current, Interpretation interp);

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
