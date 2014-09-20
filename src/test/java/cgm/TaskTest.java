package cgm;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class TaskTest {

	@Test
	public void shouldProvideCorrectValueForMetric() throws MetricNotFoundException {
		Task task = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		task.setProvidedQuality(current, Metric.METERS, 30.0);
		
		assertEquals(30.0, task.myProvidedQuality(Metric.METERS, fullContext), 0);
	}
	
	@Test
	public void shouldProvideMetricForBaseline() throws MetricNotFoundException {
		Task task = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		task.setProvidedQuality(null, Metric.METERS, 30.0);
		
		assertEquals(30.0, task.myProvidedQuality(Metric.METERS, fullContext), 0);
	}
	
	@Test (expected=MetricNotFoundException.class)
	public void shouldThrowExceptionIfMetricNotFound() throws MetricNotFoundException {
		Task task = new Task();
		
		Context current = new Context();
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);
		
		task.setProvidedQuality(null, Metric.SECONDS, 30.0);
		
		assertEquals(30.0, task.myProvidedQuality(Metric.METERS, fullContext), 0);
	}
	
	

}
