package cgm;

public abstract class Dependency {

	public static final String GOAL = "GOAL";
	public static final String TASK = "TASK";
	public static final String DELEGATION = "DELEGATION";
	
	private Context applicableContext;
	
	public void setApplicableContext(Context context) {
		applicableContext = context;
	}

	public Context getApplicableContext() {
		return applicableContext;
	}
	
	public abstract String myType();
	
	public boolean isApplicable(Context context) {
		boolean returnValue;
		if (applicableContext == context)
			returnValue = true;
		else returnValue =  false;
		
		return returnValue;
	}


}
