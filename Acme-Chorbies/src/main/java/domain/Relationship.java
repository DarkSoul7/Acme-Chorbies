
package domain;

public enum Relationship {

	ACTIVITIES("ACTIVITIES", "ACTIVIDADES"),
	FRIENDSHIP("FRIENDSHIP", "AMISTAD"),
	LOVE("LOVE", "AMOR");

	private final String	name;
	private final String	spanishName;


	private Relationship(final String name, final String spanishName) {
		this.name = name;
		this.spanishName = spanishName;
	}

	public String getName() {
		return this.name;
	}

	public String getSpanishName() {
		return this.spanishName;
	}
}
