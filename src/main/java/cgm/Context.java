package cgm;

public class Context {
	private String name;

	public Context(String name) {
		this.setName(name);
	}

	public boolean equals(Context c) {
		return name.equalsIgnoreCase(c.getName());
	}

	@Override
	public boolean equals(Object c) {
		if (c.getClass().equals(Context.class)) {
			return name.equalsIgnoreCase(((Context) c).getName());
		} else
			return (this == c);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
