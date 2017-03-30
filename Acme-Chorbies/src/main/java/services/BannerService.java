
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	//Managed repository

	@Autowired
	private BannerRepository		bannerRepository;

	//Supported services

	@Autowired
	private AdministratorService	administratorService;


	//Constructor

	public BannerService() {
		super();
	}

	public Collection<Banner> findAll() {
		final Collection<Banner> result = this.bannerRepository.findAll();

		return result;
	}

	public Banner findOne(final int bannerId) {
		final Banner result = this.bannerRepository.findOne(bannerId);

		return result;

	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		final Banner result = this.bannerRepository.save(banner);

		return result;
	}

	public void delete(final Banner banner) {
		this.administratorService.findByPrincipal();

		this.bannerRepository.delete(banner);
	}

	public void delete(final int bannerId) {
		final Banner banner = this.bannerRepository.findOne(bannerId);

		this.delete(banner);
	}

	//Other business methods

}
