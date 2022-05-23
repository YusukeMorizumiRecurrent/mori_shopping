package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class Payments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code")
	private Integer code;
	
	@Column(name = "user_code")
	private Integer userCode;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "bank_blunch")
	private Integer bankBlunch;
	
	@Column(name = "bank_num")
	private Integer bankNum;
	
	@Column(name = "bank_type")
	private Integer bankType;
	
	@Column(name = "delete_flag")
	private Integer deleteFlag;

	public Payments() {
		
	}
	
	public Payments(Integer code, Integer userCode, String name, Integer bankBlunch, Integer bankNum, Integer bankType,
			Integer deleteFlag) {
		super();
		this.code = code;
		this.userCode = userCode;
		this.name = name;
		this.bankBlunch = bankBlunch;
		this.bankNum = bankNum;
		this.bankType = bankType;
		this.deleteFlag = deleteFlag;
	}

	public Integer getCode() {
		return code;
	}

	public Integer getUserCode() {
		return userCode;
	}

	public String getName() {
		return name;
	}

	public Integer getBankBlunch() {
		return bankBlunch;
	}

	public Integer getBankNum() {
		return bankNum;
	}

	public Integer getBankType() {
		return bankType;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	
	

}
