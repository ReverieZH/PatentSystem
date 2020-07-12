package com.ORM.bean;


/**
  *@Desctiption:封装数据库连接的配置信息
  */
public class Configuration {
    private String usingDB;    //正在使用哪个数据库
    private String driver;  //驱动类
    private String url;   //jdbc的url
    private String user;   //数据库的用户名
    private String pwd;   //数据库的密码
    private String  srcPath;   //项目的路径
    private String poPackage;     //生成java类的包
    private String  queryClass;    //查询类
    private int poolMinSize;    //连接池中的最大连接数
    private int poolMaxSzie;    //连接池中的最小连接数
    private String schema;     //使用的数据库模式


    public Configuration() {
    }

    public Configuration(String driver, String url, String user, String pwd, String srcPath, String poPackage) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }

    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }

    public int getPoolMinSize() {
        return poolMinSize;
    }

    public void setPoolMinSize(int poolMinSize) {
        this.poolMinSize = poolMinSize;
    }

    public int getPoolMaxSzie() {
        return poolMaxSzie;
    }

    public void setPoolMaxSzie(int poolMaxSzie) {
        this.poolMaxSzie = poolMaxSzie;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
