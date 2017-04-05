
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class CachedTime extends DomainEntity {

	//Attributes

	private int	cachedValue;


	//Constructor

	public CachedTime() {
		super();
	}

	//Getter & setter

	public int getCachedValue() {
		return this.cachedValue;
	}

	public void setCachedValue(final int cachedValue) {
		this.cachedValue = cachedValue;
	}
	//RelationShips
}
