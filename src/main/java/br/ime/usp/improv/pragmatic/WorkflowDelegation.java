package br.ime.usp.improv.pragmatic;

import br.ime.usp.improv.pragmatic.workflow.WorkflowTask;

public class WorkflowDelegation extends WorkflowTask {

	public WorkflowDelegation(Delegation delegation) {
		super(delegation);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8373616371801588417L;

	@Override
	public boolean perform() {
		System.out.println("Delegating goals "+ getId());
		return true;
	}
}
