package cgm;

import java.util.Collection;
import java.util.HashSet;

public class Context {
	public HashSet<ContextAnnotation> contextAnnotations;
	
	public Context(Collection<ContextAnnotation> annotations) {
		contextAnnotations = new HashSet<ContextAnnotation>();
		contextAnnotations.addAll(annotations);
	}
	
	public Context(String... annotationNames) {
		contextAnnotations = new HashSet<ContextAnnotation>();
		for (String name : annotationNames) {
			contextAnnotations.add(ContextAnnotation.createContextAnnotation(name, ""));
		}
	}
	

	public boolean equals(Context c){
		return contextAnnotations.equals(c.contextAnnotations);
	}
	
	@Override
	public boolean equals(Object c){
		if(c.getClass().equals(Context.class)){
		return contextAnnotations.equals(((Context) c).contextAnnotations);
		}
		else return (this == c);
	}
	
	
	
	
}
