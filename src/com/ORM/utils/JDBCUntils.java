package com.ORM.utils;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


/**
  *@Desctiption: JDBC工具类：
  */
public class JDBCUntils {
/**
*@param:
*@return:
*@Description:用于给PreparedStatement设定参数
*/
 public static void setPrePargams(PreparedStatement ps,Object[] params) {
    if(params!=null){
        for(int i=0;i<params.length;i++){
            try {
                ps.setObject(i+1,params[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 }
}
