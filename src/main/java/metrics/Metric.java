package metrics;

public abstract class Metric {

	protected String name = "";

	public String getName() {
		return name;
	};
	public void setName(String newName) {
		name = newName;
	};

	public abstract float serialComposition(float value1, float value2);
	public abstract float parallelComposition(float value1, float value2);

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			if (this.name.contentEquals(((Metric) obj).getName()))
				return true;
			else
				return false;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return name.toLowerCase().hashCode();
	}
}
