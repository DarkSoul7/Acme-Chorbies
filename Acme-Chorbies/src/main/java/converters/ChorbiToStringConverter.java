package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Chorbi;

@Component
@Transactional
public class ChorbiToStringConverter implements Converter<Chorbi, String>{
	
	@Override
	public String convert(Chorbi chorbi) {
		String result;
		
		if(chorbi == null) {
			result = null;
		} else {
			result = String.valueOf(chorbi.getId());
		}
	
		return result;
	}

}