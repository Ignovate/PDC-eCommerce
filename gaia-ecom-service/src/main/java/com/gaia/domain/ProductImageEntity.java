package com.gaia.domain;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "products_images")
@DynamicUpdate(true)
public class ProductImageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873385722503537883L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "image")
	private String image;

	@Column(name = "image_label")
	private String imageLabel;

	@Column(name = "thumbnail")
	private String thumbnail;

	@Column(name = "thumbnail_label")
	private String thumbnailLabel;

	@Column(name = "small_image")
	private String smallImage;

	@Column(name = "small_image_label")
	private String smallImageLabel;

	@Column(name = "position")
	private Long position;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(String imageLabel) {
		this.imageLabel = imageLabel;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getThumbnailLabel() {
		return thumbnailLabel;
	}

	public void setThumbnailLabel(String thumbnailLabel) {
		this.thumbnailLabel = thumbnailLabel;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getSmallImageLabel() {
		return smallImageLabel;
	}

	public void setSmallImageLabel(String smallImageLabel) {
		this.smallImageLabel = smallImageLabel;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

}