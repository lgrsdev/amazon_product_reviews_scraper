package com.lgrsdev;

public class Review {

	private String author;
	private String title;
	private String content;
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setContent(String content){
		this.content = content;
	}

	@Override
	public String toString() {
		return "author=" + author + ", title=" + title + ", content=" + content + "\n";
	}
	
	
}
