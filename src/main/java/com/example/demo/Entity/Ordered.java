package com.example.demo.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ordered")
public class Ordered {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="code")
	private Integer code;
	
	@Column(name="user_code")
	private Integer userCode;
	
	@Column(name="ordered_date")
	private Date orderedDate;
	
	@Column(name="total_price")
	private Integer totalPrice;
	
	@Column(name="delete_flag")
	private Integer deleteFlag;

	public Ordered() {
		
	}
	
	
	public Ordered(Integer code, Integer userCode, Date orderedDate, Integer totalPrice, Integer deleteFlag) {
		super();
		this.code = code;
		this.userCode = userCode;
		this.orderedDate = orderedDate;
		this.totalPrice = totalPrice;
		this.deleteFlag = deleteFlag;
	}

	public Integer getCode() {
		return code;
	}

	public Integer getUserCode() {
		return userCode;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	

}
