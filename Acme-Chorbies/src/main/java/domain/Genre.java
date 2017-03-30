
package domain;

public enum Genre {

	WOMAN("WOMAN", "MUJER"),
	MAN("MAN", "HOMBRE");

	private final String	name;
	private final String	spanishName;


	private Genre(final String name, final String spanishName) {
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
