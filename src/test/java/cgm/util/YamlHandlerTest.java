package cgm.util;

import org.junit.Test;
import static org.junit.Assert.*;

import cgm.util.YamlHandler;
import cgm.*;

public class YamlHandlerTest {

	YamlHandler yaml = new YamlHandler();
	CGM cgm = new CGM();
	Task taskAbide = new Task();
	Task taskDoesNotAbide = new Task();
	Goal goal = new Goal(false);
	Delegation deleg = new Delegation();
	Context current = new Context();
	QualityConstraint qc = new QualityConstraint(current, Metric.SECONDS, 15, Comparison.LESS_OR_EQUAL_TO);
	String data;
	
	@Test
	public void shouldDumpATask() {
		data = "!!cgm.Task {applicableContext: null, identifier: null, qualityConstraint: null}\n";

		assertEquals(data, yaml.dumpToString(taskAbide));
	}

	@Test
	public void shouldDumpAQualityConstraint() {
		data = "!!cgm.Task {applicableContext: null, identifier: null, qualityConstraint: null}\n";

		System.out.println(yaml.dumpToString(qc));
		
		assertEquals(data, yaml.dumpToString(qc));
	}
	
	@Test
	public void shouldDumpAGoal() {
		goal.setApplicableContext(current);
		goal.setIdentifier("root goal");
		goal.setQualityConstraint(qc);
		System.out.println(yaml.dumpToString(goal));
	}
	
	@Test
	public void shouldDumpADelegation() {
		data = "!!cgm.Delegation {applicableContext: null, identifier: null, qualityConstraint: null}\n";


		System.out.println(yaml.dumpToString(deleg));
	}
	
	@Test
	public void shouldDumpAnEmptyCGM() {
		
		System.out.println(yaml.dumpToString(cgm));
	}
	
	@Test
	public void shouldDumpAComplexCGM() {
		data = "!!cgm.CGM\nroot: !!cgm.Goal\n  applicableContext: {}\n  identifier: root goal\n  qualityConstraint: {}\n";
		
		cgm.setRoot(goal);
		
		goal.setApplicableContext(current);
		goal.setIdentifier("root goal");
		goal.setQualityConstraint(qc);
	
		taskAbide.setApplicableContext(current);
		taskAbide.setIdentifier("TaskAbide");
		taskAbide.setProvidedQuality(current, Metric.SECONDS, 13);
		
		taskDoesNotAbide.setApplicableContext(current);
		taskDoesNotAbide.setIdentifier("TaskAbide");
		taskDoesNotAbide.setProvidedQuality(current, Metric.SECONDS, 16);
		
		goal.addDependency(taskAbide);
		goal.addDependency(taskDoesNotAbide);
		goal.addDependency(deleg);
		
//		System.out.println(yaml.dumpToString(cgm));
	}
	
	
	

}
