package com.elux.ado.product;

import java.io.Serializable;

public class Grade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8934987603596641369L;
	private double graID;
	private String grade;

	Grade(double graID, String grade) {
		this.graID = graID;
		this.grade = grade;
	}

	public double getGraID() {
		return graID;
	}

	public void setGraID(int graID) {
		this.graID = graID;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
