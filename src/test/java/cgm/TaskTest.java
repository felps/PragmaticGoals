package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void shouldProvideCorrectValueForMetric() {
		Task task = new Task();
		Context context = new Context();
		
		task.setProvidedQuality(context, Metric.METERS, 30.0);
		
		assertEquals(30.0, task.myProvidedQuality(Metric.METERS, context), 0);
	}

}
