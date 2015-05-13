package com.pps.rabbit.robot.module;

import java.util.Date;

public class ChatMsg {

	String name;
	Date date;
	String content;
	Type type;
	
	public enum Type{
		Income,Outcome
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
