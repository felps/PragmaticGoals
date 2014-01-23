package cgm;

import java.util.HashMap;

public class ContextAnnotation {
	private static int last_id = 0;
	private String name;
	private String description;
	private static HashMap<String, ContextAnnotation> existent = new HashMap<String, ContextAnnotation>();

	public ContextAnnotation(String id, String descriptionString)
			throws Exception {

		if (!existent.containsKey(id)) {
			name = id;
			description = descriptionString;
			existent.put(id, this);
		} else
			throw (new Exception("Previously created annotation"));
	}

	public static ContextAnnotation createContextAnnotation(String id) {
		return createContextAnnotation(id, "");
	}

	public static ContextAnnotation createContextAnnotation(String id,
			String descriptionString) {
		if (!existent.containsKey(id)) {
			try {
				return (new ContextAnnotation(id, descriptionString));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return existent.get(id);
		}

	}

	public static ContextAnnotation getContextAnnotation(String id,
			String descriptionString) {
		if (!existent.containsKey(id)) {
			try {
				return (new ContextAnnotation(id, descriptionString));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return existent.get(id);
		}

	}

	public String getIdentifier() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
