package pragmatic;

import pragmatic.metrics.Metric;
import pragmatic.metrics.exceptions.MetricNotFoundException;
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

    @Test
    public void shouldInferFullReliabilityAndNoTime() {
        Task t1 = new Task();

        assertEquals(1.0, t1.getReliability(), 0.01);
        assertEquals(0.0, t1.getTimeConsumed(), 0.01);
    }

}