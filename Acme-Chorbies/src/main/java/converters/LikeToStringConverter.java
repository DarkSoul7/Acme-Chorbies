package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Like;

@Component
@Transactional
public class LikeToStringConverter implements Converter<Like, String>{
	
	@Override
	public String convert(Like like) {
		String result;
		
		if(like == null) {
			result = null;
		} else {
			result = String.valueOf(like.getId());
		}
	
		return result;
	}

}