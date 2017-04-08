
package domain;

public enum Brand {
	
	VISA("VISA", "VISA"),
	MASTERCARD("MASTERCARD", "MASTERCARD"),
	DISCOVER("DISCOVER", "DISCOVER"),
	DINNERS("DINNERS", "DINNERS"),
	AMEX("AMEX", "AMEX");
	
	private final String	name;
	private final String	spanishName;
	
	
	private Brand(final String name, final String spanishName) {
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
