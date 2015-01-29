package gm.cgm.util;
//package cgm.util;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import cgm.CGM;
//import cgm.Comparison;
//import cgm.Context;
//import cgm.ContextAnnotation;
//import cgm.QualityConstraint;
//import cgm.Refinement;
//
//public class YamlHandlerTest {
//
//	YamlHandler yamlHandler = new YamlHandler();
//
//	@Test
//	public void shouldLoadAFullCgmFromFile() throws Exception {
//		String filename = "singleGoalCGM.yaml";
//
//		CGM cgm = yamlHandler.parseCgmFromFile(filename);
//
//		assertEquals("g1", cgm.getRoot().getIdentifier());
//
//		assertTrue(cgm.getRoot().isAndDecomposition());
//
//		ContextAnnotation applicableContextAnnotations = ContextAnnotation
//				.createContextAnnotation("c1", "");
//		Context applicableContext = new Context(
//				applicableContextAnnotations.getIdentifier());
//
//		assertTrue(cgm.getRoot().getApplicableContext().contains(applicableContextAnnotations));
//
//		assertEquals(1, cgm.getRoot().getDependencies().size());
//
//		for( QualityConstraint qc : cgm.getRoot().getAllQualityConstraint()){
//			assertNotEquals(null, qc);
//			assertEquals(Comparison.LESS_OR_EQUAL_TO, qc.getComparison());
//			assertEquals(20.0, qc.getThreshold(), 0);
//			assertEquals("seconds", qc.getMetric());
//			assertEquals(applicableContext, qc.getApplicableContext());
//		}
//
//		for (Refinement ref : cgm.getRoot().getDependencies()) {
//			assertEquals("t1", ref.getIdentifier());
//			assertTrue(applicableContext.equals(ref.getApplicableContext()));
//
//		}
//	}
//
//}
