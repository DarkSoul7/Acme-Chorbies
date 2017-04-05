
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class CachedTime extends DomainEntity {

	//Attributes

	private int	cachedHour;
	private int	cachedMinute;
	private int	cachedSecond;


	//Constructor

	public CachedTime() {
		super();
	}

	//Getter & setter

	@Min(0)
	@Max(99)
	public int getCachedHour() {
		return this.cachedHour;
	}

	public void setCachedHour(final int cachedHour) {
		this.cachedHour = cachedHour;
	}

	@Min(0)
	@Max(59)
	public int getCachedMinute() {
		return this.cachedMinute;
	}

	public void setCachedMinute(final int cachedMinute) {
		this.cachedMinute = cachedMinute;
	}

	@Min(0)
	@Max(59)
	public int getCachedSecond() {
		return this.cachedSecond;
	}

	public void setCachedSecond(final int cachedSecond) {
		this.cachedSecond = cachedSecond;
	}

	//RelationShips
}
