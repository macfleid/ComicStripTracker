package com.mcfly.cstracker.sync.model;

import java.io.Serializable;
import java.util.List;

public class IsbnProperties implements Serializable{
	
	private String author;
   	private String city;
   	private List<String> form;
   	private List<String> isbn;
   	private String lang;
   	private List<String> oclcnum;
   	private String publisher;
   	private String title;
   	private List<String> url;
   	private String year;

 	public String getAuthor(){
		return this.author;
	}
	public void setAuthor(String author){
		this.author = author;
	}
 	public String getCity(){
		return this.city;
	}
	public void setCity(String city){
		this.city = city;
	}
 	public List<String> getForm(){
		return this.form;
	}
	public void setForm(List<String> form){
		this.form = form;
	}
 	public List<String> getIsbn(){
		return this.isbn;
	}
	public void setIsbn(List<String> isbn){
		this.isbn = isbn;
	}
 	public String getLang(){
		return this.lang;
	}
	public void setLang(String lang){
		this.lang = lang;
	}
 	public List<String> getOclcnum(){
		return this.oclcnum;
	}
	public void setOclcnum(List<String> oclcnum){
		this.oclcnum = oclcnum;
	}
 	public String getPublisher(){
		return this.publisher;
	}
	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
 	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
 	public List<String> getUrl(){
		return this.url;
	}
	public void setUrl(List<String> url){
		this.url = url;
	}
 	public String getYear(){
		return this.year;
	}
	public void setYear(String year){
		this.year = year;
	}
	
}
