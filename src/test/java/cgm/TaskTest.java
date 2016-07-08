package cgm;

import cgm.metrics.Metric;
import cgm.metrics.exceptions.MetricNotFoundException;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TaskTest {

	@Test
	public void shouldProvideCorrectValueForMetric() throws MetricNotFoundException {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.setProvidedQuality(current, Metric.METERS, 30.0);

		assertEquals(30.0, task.myProvidedQuality(Metric.METERS, fullContext), 0);
	}

	@Test
	public void shouldProvideMetricForBaseline() throws MetricNotFoundException {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.setProvidedQuality(null, Metric.METERS, 30.0);

		assertEquals(30.0, task.myProvidedQuality(Metric.METERS, fullContext), 0);
	}

	@Test(expected = MetricNotFoundException.class)
	public void shouldThrowExceptionIfMetricNotFound() throws MetricNotFoundException {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.setProvidedQuality(null, Metric.SECONDS, 30.0);

		assertEquals(30.0, task.myProvidedQuality(Metric.METERS, fullContext), 0);
	}
	
	@Test
	public void shouldUseLessIsMoreQC(){
		Task t1= new Task(true);
	}
}
