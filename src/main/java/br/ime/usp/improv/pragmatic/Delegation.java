package br.ime.usp.improv.pragmatic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;

import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;
import br.ime.usp.improv.proxy.utils.ObjectSerializer;
import br.ime.usp.improv.proxy.utils.Result;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvokation;
import br.ime.usp.improv.proxy.webservice.handlers.WsInvoker;

public class Delegation extends Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4159224450071751058L;
	private String nextActorEndpoint;
	private HashMap<String, Integer> output;
	private ArrayList<String> expectedOutput;
	private WsInvoker nextActorInvoker;
	private Result result;
	private String nextActorID;

	public Delegation(String ID) {
		super();
		this.setIdentifier(ID);
		output = new HashMap<>();
		expectedOutput = new ArrayList<>();
	}

	@Override
	public WorkflowPlan isAchievable(Set<Context> current, Interpretation interp) {

		WorkflowPlan plan = new WorkflowPlan(this);
		plan.getActors().put(getNextActorID(), nextActorEndpoint);
		byte[] serializedInterp = null;

		if (this.nextActorInvoker == null){
			this.nextActorInvoker = new WsInvoker(nextActorEndpoint);
			System.out.println("Invoker created");
		}

		this.nextActorInvoker.setTimeout(15000);
		System.out.println("Delegation underway");
		if (interp == null)
			interp = new Interpretation();
		
		try {
			serializedInterp = ObjectSerializer.serialize(interp);
			System.out.println("Serialized Interpretation :" + serializedInterp);
			
			WsInvokation invocation = nextActorInvoker.invokeWebMethod( "webPlan", serializedInterp);
			byte[] serializedPlan;
			do {
				serializedPlan = (byte[]) invocation.getResultSetter().getResultValue();
			} while (serializedPlan == null);
			System.out.println("Serialized Plan :" + serializedPlan);
			WorkflowPlan deserializedPlan = (WorkflowPlan) ObjectSerializer.deserialize(serializedPlan);
			plan.addSerial(deserializedPlan);
		} catch (TimeoutException e) {
			System.out.println("Delegation 54");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Delegation 57");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Delegation 60");
			e.printStackTrace();
		}
		System.out.println("Achievable plan at " + this.getExecutingActor());
		System.out.println("Plan Size:" + plan.getTasks().size());
		System.out.println("Plan Tasks:");	
		for (WorkflowTask task : plan.getTasks()) {
			System.out.println(">>" + task.getIdentifier());
		}
		return plan;
	}

	@Override
	public int size() {
		return 0;
	}

	public void setExpectedOutput(String outputName) {
		expectedOutput.add(outputName);

	}

	public void setEndpoint(String nextActorEndpoint) {
		this.nextActorEndpoint = nextActorEndpoint;
	}

	public String getNextActorID() {
		return nextActorID;
	}

	public void setNextActorID(String nextActorID) {
		this.nextActorID = nextActorID;
	}
}
