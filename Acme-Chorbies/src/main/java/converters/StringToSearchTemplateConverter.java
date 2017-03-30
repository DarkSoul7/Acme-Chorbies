package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SearchTemplateRepository;
import domain.SearchTemplate;

@Component
@Transactional
public class StringToSearchTemplateConverter implements Converter<String, SearchTemplate>{
	
	@Autowired
	private SearchTemplateRepository searchTemplateRepository;
	
	@Override
	public SearchTemplate convert(String text) {
		SearchTemplate result;
		int id;
		
		try {
			if(StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = searchTemplateRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}