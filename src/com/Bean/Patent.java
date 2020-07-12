package com.Bean;

import java.sql.*;
import java.util.*;

public class Patent{
	private String patstruct;
	private String patstate;
	private Integer patid;
	private String patwr;
	private String patname;
	private java.sql.Date pattime;
	private String pattext;
	private String patnum;


	public String getPatstruct(){
		return patstruct;
	}
	public String getPatstate(){
		return patstate;
	}
	public Integer getPatid(){
		return patid;
	}
	public String getPatwr(){
		return patwr;
	}
	public String getPatname(){
		return patname;
	}
	public java.sql.Date getPattime(){
		return pattime;
	}
	public String getPattext(){
		return pattext;
	}
	public String getPatnum(){
		return patnum;
	}
	public void setPatstruct(String patstruct){
		this.patstruct=patstruct;
	}
	public void setPatstate(String patstate){
		this.patstate=patstate;
	}
	public void setPatid(Integer patid){
		this.patid=patid;
	}
	public void setPatwr(String patwr){
		this.patwr=patwr;
	}
	public void setPatname(String patname){
		this.patname=patname;
	}
	public void setPattime(java.sql.Date pattime){
		this.pattime=pattime;
	}
	public void setPattext(String pattext){
		this.pattext=pattext;
	}
	public void setPatnum(String patnum){
		this.patnum=patnum;
	}
}
