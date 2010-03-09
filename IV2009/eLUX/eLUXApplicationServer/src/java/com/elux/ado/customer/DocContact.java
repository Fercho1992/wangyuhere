package com.elux.ado.customer;

import java.io.Serializable;

public class DocContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int docID;
	private String startTime;
	private String finishTime;
	private String docTime;
	private int cusID;
	private int perID;
	private String content;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getDocTime() {
		return docTime;
	}
	public void setDocTime(String docTime) {
		this.docTime = docTime;
	}
	public int getCusID() {
		return cusID;
	}
	public void setCusID(int cusID) {
		this.cusID = cusID;
	}
	public int getPerID() {
		return perID;
	}
	public void setPerID(int perID) {
		this.perID = perID;
	}

}
