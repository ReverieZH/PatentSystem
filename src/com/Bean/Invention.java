package com.Bean;

import java.sql.*;
import java.util.*;

public class Invention{
	private String invstruct;
	private java.sql.Date invtime;
	private String invtext;
	private Integer invid;
	private String invnum;
	private String invwr;
	private String invname;
	private String invstate;


	public String getInvstruct(){
		return invstruct;
	}
	public java.sql.Date getInvtime(){
		return invtime;
	}
	public String getInvtext(){
		return invtext;
	}
	public Integer getInvid(){
		return invid;
	}
	public String getInvnum(){
		return invnum;
	}
	public String getInvwr(){
		return invwr;
	}
	public String getInvname(){
		return invname;
	}
	public String getInvstate(){
		return invstate;
	}
	public void setInvstruct(String invstruct){
		this.invstruct=invstruct;
	}
	public void setInvtime(java.sql.Date invtime){
		this.invtime=invtime;
	}
	public void setInvtext(String invtext){
		this.invtext=invtext;
	}
	public void setInvid(Integer invid){
		this.invid=invid;
	}
	public void setInvnum(String invnum){
		this.invnum=invnum;
	}
	public void setInvwr(String invwr){
		this.invwr=invwr;
	}
	public void setInvname(String invname){
		this.invname=invname;
	}
	public void setInvstate(String invstate){
		this.invstate=invstate;
	}
}
