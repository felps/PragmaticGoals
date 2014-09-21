//package cgm.util;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//
//import org.yaml.snakeyaml.Yaml;
//
//import cgm.CGM;
//import cgm.Comparison;
//import cgm.Context;
//import cgm.ContextAnnotation;
//import cgm.Goal;
//import cgm.QualityConstraint;
//import cgm.Refinement;
//import cgm.Task;
//
//public class YamlHandler {
//
//	public Object parseFromYamlString(String data) {
//		Yaml yaml = new Yaml();
//		return yaml.load(data);
//	}
//
//	public String dumpToString(Object dumped) {
//		Yaml yaml = new Yaml();
//		return yaml.dump(dumped);
//	}
//
//	public void dumpToYamlFile(Object dumped) {
//		dumpToString(dumped);
//
//	}
//
//	public Object loadYamlFromFile(String filename) {
//		Yaml yaml = new Yaml();
//
//		File file = new File("singleGoalCGM.yaml");
//
//		FileInputStream fis = null;
//
//		try {
//			fis = new FileInputStream(file);
//			System.out.println("Total file size to read (in bytes) : "
//					+ fis.available());
//			Object data = yaml.load(fis);
//			System.out.println(data);
//			return data;
//		} catch (FileNotFoundException e) {
//			System.out.println("File not found");
//		} catch (IOException e) {
//			System.out.println("Error while reading " + filename + "file.");
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	public CGM parseCgmFromFile(String filename) {
//		// TODO Auto-generated method stub
//
//		Map<String, Object> yamlData = (Map<String, Object>) loadYamlFromFile(filename);
//		Map<String, Refinement> refinements = new HashMap<String, Refinement>();
//
//		Map<String, Object> tasks = (Map<String, Object>) yamlData
//				.get("tasks");
//
//		addAllTasks(refinements, tasks);
//
//		Map<String, Object> goals = (Map<String, Object>) yamlData
//				.get("goals");
//		
//		addAllGoals(refinements, goals);
//		
//		CGM complete = createCGM(yamlData, refinements);
//		return complete;
//	}
//
//	@SuppressWarnings("unchecked")
//	private CGM createCGM(Map<String, Object> yamlData,
//			Map<String, Refinement> refinements) {
//		Map<String, String> cgmData = (Map<String, String>) yamlData.get("CGM");
//		
//		String rootGoalName = cgmData.get("root");
//		Refinement rootGoal = refinements.get(rootGoalName);
//
//		CGM cgm = new CGM();
//		cgm.setRoot(rootGoal);
//		
//		for (Refinement ref : refinements.values()) {
//			Map<String, Object> goalsData = (Map<String, Object>) yamlData.get("goals");
//			
//			if(ref.myType() == Refinement.GOAL){
//				Map<String, Object> goalData = (Map<String, Object>) goalsData.get(ref.getIdentifier());
//				
//				addRefinements(refinements, ref, goalData);
//			}
//		}
//		return cgm;
//	}
//
//	private void addRefinements(Map<String, Refinement> refinements,
//			Refinement ref, Map<String, Object> goalData) {
//
//		Goal goal = (Goal) ref;
//		
//		for (String refinementName : (List<String>) goalData.get("deps")) {
//			goal.addDependency(refinements.get(refinementName));
//		}
//		
//	}
//
//	private void addAllGoals(Map<String, Refinement> refinements,
//			Map<String, Object> goals) {
//		
//		for (String goalName : goals.keySet()) {
//			
//			Map<String, Object> goalAttributes = ((Map<String, Object>) goals.get(goalName));
//			System.out.println(goalAttributes);
//			
//			addSingleGoal(refinements, goalName, goalAttributes);
//		}
//		
//	}
//
//	private void addSingleGoal(Map<String, Refinement> refinements,
//			String goalName, Map<String, Object> goalAttributes) {
//		boolean isOrDecomp = ((Boolean) goalAttributes.get("isOr")).booleanValue();
//		Goal goal = new Goal(isOrDecomp);
//
//		Context applicable;
//
//		applicable = getContextFromYaml(goalAttributes);
//		goal.addApplicableContext(applicable);
//
//		goal.setIdentifier(goalName);
//		
//		QualityConstraint qc = getQualityConstraints(goal, goalAttributes);
//		
//		
//		refinements.put(goalName, goal);
//	}
//
//	private QualityConstraint getQualityConstraints(Goal goal, Map<String, Object> goalAttributes) {
//		Map<String, Map<String, Object>> qualityConstraints = (Map<String, Map<String, Object>>) goalAttributes.get("qc");
//		
//		for (Map<String,Object> constraint : qualityConstraints.values()) {
//			Context applicable = null;
//			
//			String metric = null;
//			double value = 0;
//			int comparison = 0;
//			
//			metric = (String) constraint.get("metric");
//			value = (Double) constraint.get("value");
//			comparison = getComparisonCode((String) constraint.get("comparison"));
//			applicable = getContextFromYaml(goalAttributes);
//			
//			QualityConstraint qc = new QualityConstraint(applicable, metric,value,comparison);
//			
//			goal.addQualityConstraint(qc);
//		}
//		return null;
//	}
//
//	private int getComparisonCode(String comparisonObject) {
//		int comparison = 0;
//		
//		System.out.println(">>"+ comparisonObject + "<<");
//		if(comparisonObject.equals("<=")){
//			comparison = Comparison.LESS_OR_EQUAL_TO;
//		}
//		if(comparisonObject.equals("<")){
//			comparison = Comparison.LESS_THAN;
//		}
//		if(comparisonObject.equals("=")){
//			comparison = Comparison.EQUAL_TO;
//		}
//		if(comparisonObject.equals(">")){
//			comparison = Comparison.GREATER_THAN;
//		}
//		if(comparisonObject.equals(">=")){
//			comparison = Comparison.GREATER_OR_EQUAL_TO;
//		}
//		return comparison;
//	}
//
//	private void addAllTasks(Map<String, Refinement> refinements,
//			Map<String, Object> tasks) {
//		for (String taskName : tasks.keySet()) {
//			Task task = new Task();
//
//			HashSet<Context> applicable;
//
//			Map<String, Object> taskData = (Map<String, Object>) tasks
//					.get(taskName);
//
//			applicable = getContextFromYaml(taskData);
//			task.addApplicableContext(applicable);
//
//			Map<String, Object> qualityProvided = getProvidedquality(taskData);
//			task.setProvidedQuality(getContextFromYaml(qualityProvided),
//					((String) qualityProvided.get("metric")),
//					((Double) qualityProvided.get("value")).floatValue());
//
//			task.setIdentifier(taskName);
//			refinements.put(taskName, task);
//		}
//	}
//
//	private Map<String, Object> getProvidedquality(Map<String, Object> taskData) {
//		return (Map<String, Object>) taskData.get("qp");
//	}
//
//	private HashSet<Context> getContextFromYaml(Map<String, Object> taskData) {
//		HashSet<Context> context = new HashSet<Context>();
//
//		for (String annotation : (List<String>) taskData.get("context")) {
//			context.add(new Context(annotation));
//		}
//		return context;
//	}
//
//}
