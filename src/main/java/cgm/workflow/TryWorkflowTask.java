package cgm.workflow;

import cgm.Task;

/**
 * Created by Felipe on 13/07/2016.
 */
public class TryWorkflowTask extends WorkflowTask {

    private Plan attempt;
    private Plan ifSuccess;
    private Plan ifFail;

    public TryWorkflowTask(Task task) {
        super(task);
        attempt = new Plan(task);
    }

    public Plan getAttempt() {
        return attempt;
    }

    public void setAttempt(Plan attempt) {
        this.attempt = attempt;
    }

    public Plan getIfSuccess() {
        return ifSuccess;
    }

    public void setIfSuccess(Plan ifSuccess) {
        this.ifSuccess = ifSuccess;
    }

    public Plan getIfFail() {
        return ifFail;
    }

    public void setIfFail(Plan ifFail) {
        this.ifFail = ifFail;
    }
}
