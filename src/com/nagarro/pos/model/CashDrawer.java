package com.nagarro.pos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Entity
public class CashDrawer extends AbstractTimestampEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9195161596197446426L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private Date date;
	private float startBal;
	private float endBal;

	@JsonIgnore
	@ManyToOne
	private Employee employee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getStartBal() {
		return startBal;
	}

	public void setStartBal(float startBal) {
		this.startBal = startBal;
	}

	public float getEndBal() {
		return endBal;
	}

	public void setEndBal(float endBal) {
		this.endBal = endBal;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
