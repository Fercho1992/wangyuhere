package com.elux.ado.product;

import java.io.Serializable;

public class Grade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8934987603596641369L;
	private int graID;
	private String grade;

	Grade(int graID, String grade) {
		this.graID = graID;
		this.grade = grade;
	}

	public int getGraID() {
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
