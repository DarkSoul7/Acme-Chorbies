
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

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

	//Simple CRUD methods

	public Banner create() {
		this.administratorService.findByPrincipal();
		final Banner result = new Banner();

		return result;
	}

	public Collection<Banner> findAll() {
		final Collection<Banner> result = this.bannerRepository.findAll();

		return result;
	}

	public Collection<Banner> findAllBanners() {
		this.administratorService.findByPrincipal();
		final Collection<Banner> result = this.bannerRepository.findAll();

		return result;
	}

	public Banner findOne(final int bannerId) {
		final Banner result = this.bannerRepository.findOne(bannerId);

		return result;

	}

	public Banner save(final Banner banner) {
		this.administratorService.findByPrincipal();
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

	public Banner getRandomBanner() {
		Banner result;
		final ArrayList<Banner> banners = new ArrayList<>(this.findAll());
		if (banners.size() == 0)
			result = null;
		else if (banners.size() == 1)
			result = banners.get(0);
		else {
			final Random random = new Random();
			final int index = random.nextInt(banners.size());
			result = banners.get(index);
		}

		return result;
	}
}
