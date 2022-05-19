package com.example.demo.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class Items {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="code")
	private Integer code;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="picture")
	private String picture;
	
	@Column(name="stock")
	private Integer stock;

	@Column(name="categoryKey")
	private Integer categoryKey;
	
	@Column(name="delivaryDays")
	private Integer delivaryDays;
	
	@Column(name="sellerUserCode")
	private Integer sellerUserCode;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="delete_flag")
	private Integer delete_flag;
	
	public Items() {
		
	}

	public Items(Integer code, String name, Integer price, String picture, Integer stock, Integer categoryKey,
			Integer delivaryDays, Integer sellerUserCode, Date date, Integer delete_flag) {
		super();
		this.code = code;
		this.name = name;
		this.price = price;
		this.picture = picture;
		this.stock = stock;
		this.categoryKey = categoryKey;
		this.delivaryDays = delivaryDays;
		this.sellerUserCode = sellerUserCode;
		this.date = date;
		this.delete_flag = delete_flag;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}

	public String getPicture() {
		return picture;
	}

	public Integer getStock() {
		return stock;
	}

	public Integer getCategoryKey() {
		return categoryKey;
	}

	public Integer getDelivaryDays() {
		return delivaryDays;
	}

	public Integer getSellerUserCode() {
		return sellerUserCode;
	}

	public Date getDate() {
		return date;
	}

	public Integer getDelete_flag() {
		return delete_flag;
	}
	
	

}
