package com.Bean;

import java.sql.*;
import java.util.*;

public class Iuser{
	private Integer uselimit;
	private String userpwd;
	private Integer userid;
	private String username;


	public Integer getUselimit(){
		return uselimit;
	}
	public String getUserpwd(){
		return userpwd;
	}
	public Integer getUserid(){
		return userid;
	}
	public String getUsername(){
		return username;
	}
	public void setUselimit(Integer uselimit){
		this.uselimit=uselimit;
	}
	public void setUserpwd(String userpwd){
		this.userpwd=userpwd;
	}
	public void setUserid(Integer userid){
		this.userid=userid;
	}
	public void setUsername(String username){
		this.username=username;
	}
}
