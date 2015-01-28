package cgm;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import metrics.DistanceErrorMargin;
import metrics.ExecutionTimeSec;

import org.junit.Test;

public class TaskTest {

	@Test
	public void shouldProvideCorrectValueForMetric() throws MetricNotFoundException {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.setProvidedQuality(current, (new DistanceErrorMargin()), 30.0);

		assertEquals(30.0, task.myProvidedQuality((new DistanceErrorMargin()), fullContext), 0);
	}

	@Test
	public void shouldProvideMetricForBaseline() throws MetricNotFoundException {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.setProvidedQuality(null, (new DistanceErrorMargin()), 30.0);

		assertEquals(30.0, task.myProvidedQuality((new DistanceErrorMargin()), fullContext), 0);
	}

	@Test(expected = MetricNotFoundException.class)
	public void shouldThrowExceptionIfMetricNotFound() throws MetricNotFoundException {
		Task task = new Task();

		Context current = new Context("C1");
		HashSet<Context> fullContext = new HashSet<Context>();
		fullContext.add(current);

		task.setProvidedQuality(null, (new ExecutionTimeSec()), 30.0);

		assertEquals(30.0, task.myProvidedQuality((new DistanceErrorMargin()), fullContext), 0);
	}
}
