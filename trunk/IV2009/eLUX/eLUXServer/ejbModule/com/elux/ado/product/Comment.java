package com.elux.ado.product;

import java.io.Serializable;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8073848151360545191L;
	private int comID;
	private String comContents;
	private String comments;

	Comment(int comID, String comContents, String comments) {
		this.comID = comID;
		this.comContents = comContents;
		this.comments = comments;
	}

	public int getComID() {
		return comID;
	}

	public void setComID(int comID) {
		this.comID = comID;
	}

	public String getComContents() {
		return comContents;
	}

	public void setComContents(String comContents) {
		this.comContents = comContents;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
