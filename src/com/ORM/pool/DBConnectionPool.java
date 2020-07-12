package com.ORM.pool;


import com.ORM.core.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
  *@Desctiption:数据库连接池
  */
public class DBConnectionPool {
    private List<Connection> pool;  //连接池对象
    private static  final int POOL_MAX_SIZE= DBManager.getConf().getPoolMaxSzie();
    private static final int POOL_MIN_SIZE=DBManager.getConf().getPoolMinSize();

    public DBConnectionPool(){
        init();
    }


    /**
    *@param:
    *@return:
    *@Description:连接池初始化 循环检测连接池，保持连接池的中的连接对象数量不小于最小值
    */
    private void init(){
        if(pool==null){
            pool=new ArrayList<>();
        }
        while(pool.size()<POOL_MIN_SIZE){
            pool.add(DBManager.creatConn());
        }
    }

    /**
    *@param:
    *@return:
    *@Description:从连接池取出一个连接
    */
    public synchronized Connection getConnection(){
        int lastIndex=pool.size()-1;
        Connection con=pool.get(lastIndex);
        pool.remove(lastIndex);
        return con;
    }

    /**
    *@param:
    *@return:
    *@Description:放回一个连接到连接池中  如果连接池数量大与最大数量，则将这个对象关闭
    */
    public synchronized void closeBack(Connection con){
          if(pool.size()>POOL_MAX_SIZE){
              try {
                  if(con!=null){
                      con.close();
                  }
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }else{
              pool.add(con);
          }
    }
}
