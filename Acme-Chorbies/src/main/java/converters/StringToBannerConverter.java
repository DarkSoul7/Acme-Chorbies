package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.BannerRepository;
import domain.Banner;

@Component
@Transactional
public class StringToBannerConverter implements Converter<String, Banner>{
	
	@Autowired
	private BannerRepository bannerRepository;
	
	@Override
	public Banner convert(String text) {
		Banner result;
		int id;
		
		try {
			if(StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = bannerRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}