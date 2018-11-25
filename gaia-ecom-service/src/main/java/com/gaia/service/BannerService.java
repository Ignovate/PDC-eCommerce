package com.gaia.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gaia.common.util.FileUtils;
import com.gaia.domain.BannerEntity;
import com.gaia.domain.BannerImageEntity;
import com.gaia.repository.BannerImageRepo;
import com.gaia.repository.BannerRepo;

@Service
public class BannerService {

	@Autowired
	private BannerRepo bannerRepo;
	@Autowired
	private BannerImageRepo bannerImageRepo;

	public BannerEntity getBanner(Long id) {
		return bannerRepo.findById(id).orElse(null);
	}

	public List<BannerEntity> getBanners(String bannerName) {
		Specification<BannerEntity> spec = new Specification<BannerEntity>() {

			@Override
			public Predicate toPredicate(Root<BannerEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				predicate.add(criteriaBuilder.equal(root.get("layoutName"), bannerName));

				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return bannerRepo.findAll(spec);
	}

	public BannerEntity saveBanner(BannerEntity banner) {
		BannerEntity bannerEntity = bannerRepo.save(banner);
		FileUtils.createDirectory("banner/"+bannerEntity.getBannerId());
		return bannerEntity;
	}

	public void deleteBanner(Long id) {
		bannerRepo.deleteById(id);
	}

	public BannerImageEntity getBannerImage(Long id) {
		return bannerImageRepo.findById(id).orElse(null);
	}

	public List<BannerImageEntity> getBannerImages(Long bannerId) {
		Specification<BannerImageEntity> spec = new Specification<BannerImageEntity>() {

			@Override
			public Predicate toPredicate(Root<BannerImageEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicate = new ArrayList<Predicate>();

				predicate.add(criteriaBuilder.equal(root.get("bannerId"), bannerId));

				return criteriaBuilder.and(predicate.stream().toArray(Predicate[]::new));
			}

		};
		return bannerImageRepo.findAll(spec);
	}

	public BannerImageEntity saveBannerImage(BannerImageEntity bannerImage) {
		return bannerImageRepo.save(bannerImage);
	}

	public void deleteBannerImage(Long id) {
		bannerImageRepo.deleteById(id);
	}
	
	public void deleteBannerImages(Long id) {
		List<BannerImageEntity> list = this.getBannerImages(id);
		for(BannerImageEntity bannerImage: list) {
			this.deleteBannerImage(bannerImage.getImageId());
		}
	}
}
