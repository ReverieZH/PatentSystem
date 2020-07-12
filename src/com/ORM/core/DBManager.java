package com.ORM.core;

import com.ORM.bean.Configuration;
import com.ORM.pool.DBConnectionPool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class DBManager {
    private static Configuration conf;   //配置信息
    private static DBConnectionPool pool;   //连接池对象

    static{   //初始化
        Properties pros=new Properties();
        try {
            pros.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"),"GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        conf=new Configuration();
        conf.setUsingDB(pros.getProperty("UsingDB"));
        conf.setDriver(pros.getProperty("Driver"));
        conf.setUrl(pros.getProperty("URL"));
        conf.setUser(pros.getProperty("User"));
        conf.setPwd(pros.getProperty("Pwd"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setPoPackage(pros.getProperty("PoPackage"));
        conf.setQueryClass(pros.getProperty("queryClass"));
        conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));
        conf.setPoolMaxSzie(Integer.parseInt(pros.getProperty("poolMaxSize")));
        conf.setSchema(pros.getProperty("usSchema"));
    }

    /**
    *@param:
    *@return:
    *@Description:获得数据库配置对象
    */
    public static Configuration getConf() {
        return conf;
    }
    /**
    *@param:
    *@return:
    *@Description:获取一个Connection对象
    */
    public static Connection getConn(){
        if(pool==null){
            pool=new DBConnectionPool();
        }
        return pool.getConnection();
    }

    /**
    *@param:
    *@return:
    *@Description:创建一个连接对象
    */
    public static Connection creatConn(){
        try {
            Class.forName(conf.getDriver());
            return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *@param:
     *@return:
     *@Description:关闭Connection对象
     */
    public static void close(Connection con){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *@param:
     *@return:
     *@Description:关闭Connection对象和Statement对象
     */
    public static void close(Connection con, Statement ps){
        try {
            if(ps!=null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(con!=null){
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *@param:
     *@return:
     *@Description:关闭Connection对象,Statement对象和ResultSet对象
     */
    public static void close(Connection con, Statement ps, ResultSet res){
        try {
            if(res!=null){
                res.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(ps!=null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(con!=null){
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
