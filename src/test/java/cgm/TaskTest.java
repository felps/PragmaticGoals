package cgm;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void shouldProvideCorrectValueForMetric() {
		Task task = new Task();
		
		task.setProvidedQuality(Metric.METERS, 30.0);
		
		assertEquals(30.0, task.myProvidedQuality(Metric.METERS), 0);
	}

}
