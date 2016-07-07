package cgm.workflow;

/**
 * Created by Felipe on 07/07/2016.
 */
public class TryTask extends WorkflowTask {


    public TryTask(String id) {
        super(id);
    }

    public TryTask(String id, Plan attempt, Plan suceed, Plan fail) {
        super(id);
    }
}
