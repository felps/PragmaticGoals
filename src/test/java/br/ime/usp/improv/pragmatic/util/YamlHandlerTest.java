package br.ime.usp.improv.pragmatic.util;
//package cgm.util;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import pragmatic.CGM;
//import pragmatic.Comparison;
//import pragmatic.Context;
//import pragmatic.ContextAnnotation;
//import pragmatic.FilterQualityConstraint;
//import pragmatic.Refinement;
//
//public class YamlHandlerTest {
//
//	YamlHandler yamlHandler = new YamlHandler();
//
//	@Test
//	public void shouldLoadAFullCgmFromFile() throws Exception {
//		String filename = "singleGoalCGM.yaml";
//
//		CGM pragmatic = yamlHandler.parseCgmFromFile(filename);
//
//		assertEquals("g1", pragmatic.getRoot().getIdentifier());
//
//		assertTrue(pragmatic.getRoot().isAndDecomposition());
//
//		ContextAnnotation applicableContextAnnotations = ContextAnnotation
//				.createContextAnnotation("c1", "");
//		Context applicableContext = new Context(
//				applicableContextAnnotations.getIdentifier());
//
//		assertTrue(pragmatic.getRoot().getApplicableContext().contains(applicableContextAnnotations));
//
//		assertEquals(1, pragmatic.getRoot().getDependencies().size());
//
//		for( FilterQualityConstraint qc : pragmatic.getRoot().getAllQualityConstraint()){
//			assertNotEquals(null, qc);
//			assertEquals(Comparison.LESS_OR_EQUAL_TO, qc.getComparison());
//			assertEquals(20.0, qc.getThreshold(), 0);
//			assertEquals("seconds", qc.getMetric());
//			assertEquals(applicableContext, qc.getApplicableContext());
//		}
//
//		for (Refinement ref : pragmatic.getRoot().getDependencies()) {
//			assertEquals("t1", ref.getIdentifier());
//			assertTrue(applicableContext.equals(ref.getApplicableContext()));
//
//		}
//	}
//
//}
