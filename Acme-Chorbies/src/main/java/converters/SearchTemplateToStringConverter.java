package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SearchTemplate;

@Component
@Transactional
public class SearchTemplateToStringConverter implements Converter<SearchTemplate, String>{
	
	@Override
	public String convert(SearchTemplate searchTemplate) {
		String result;
		
		if(searchTemplate == null) {
			result = null;
		} else {
			result = String.valueOf(searchTemplate.getId());
		}
	
		return result;
	}

}