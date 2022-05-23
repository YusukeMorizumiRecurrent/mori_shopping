package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orderdetail")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="code")
	private Integer code;
	
	@Column(name="ordered_code")
	private Integer orderedCode;
	
	@Column(name="items_code")
	private Integer itemsCode;
	
	@Column(name="delete_flag")
	private Integer deleteFlag;
	
	public OrderDetail() {
		
	}
	
	

	public OrderDetail(Integer orderedCode, Integer itemsCode, Integer deleteFlag) {
		super();
		this.orderedCode = orderedCode;
		this.itemsCode = itemsCode;
		this.deleteFlag = deleteFlag;
	}



	public OrderDetail(Integer code, Integer orderedCode, Integer itemsCode, Integer deleteFlag) {
		super();
		this.code = code;
		this.orderedCode = orderedCode;
		this.itemsCode = itemsCode;
		this.deleteFlag = deleteFlag;
	}

	public Integer getCode() {
		return code;
	}

	public Integer getOrderedCode() {
		return orderedCode;
	}

	public Integer getItemsCode() {
		return itemsCode;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	
	
	


	
}
