package pragmatic;

import pragmatic.workflow.Plan;

import java.util.Set;

public class Delegation extends Refinement {

    @Override
    public Plan isAchievable(Set<Context> current, Interpretation interp) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
