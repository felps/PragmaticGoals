package metrics;

public abstract class Metric {

	protected String name = "";

	public String getName() {
		return name;
	};
	public void setName(String newName) {
		name = newName;
	};

	public abstract double serialComposition(double value1, double value2);
	public abstract double parallelComposition(double value1, double value2);

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
