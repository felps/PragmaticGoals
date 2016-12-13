package br.ime.usp.improv.pragmatic.runtime.annotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.ime.usp.improv.pragmatic.Refinement;
import br.ime.usp.improv.pragmatic.WorkflowPlan;

/**
 * Created by Felipe on 29/06/2016.
 */
public abstract class RuntimeAnnotation implements Cloneable{

    public static final int Try = 0;
    public static final int Sequential = 1;
    public static final int Interleaved = 2;
    public static final int SequentiallyIterated = 3;
    public static final int InterleavedIterated = 4;
    public static final int Alternative = 5;
    public static final int skip = 6;
    public boolean goalType;
    protected List<Refinement> sequence;

    public RuntimeAnnotation() {
        sequence = Collections.synchronizedList(new ArrayList<Refinement>());
    }

    public boolean getGoalType() {
        return goalType;
    }

    public void setGoalType(boolean goalType) {
        this.goalType = goalType;
    }

    public List<Refinement> getRefinements() {
        return sequence;
    }

    public void includeRefinement(Refinement refinement, int position) {
        sequence.add(refinement);
    }

    public abstract List<WorkflowPlan> getPossiblePlans(Map<Refinement, WorkflowPlan> approaches);

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clone = super.clone();
        ((RuntimeAnnotation) clone).sequence = new ArrayList<>();
        return clone;
    }
}