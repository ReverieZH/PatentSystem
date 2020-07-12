package com.System.JDBC;

import com.Bean.Iuser;
import com.ORM.bean.TableInfo;
import com.ORM.core.Query;
import com.ORM.core.QueryFactory;
import com.ORM.core.TableContext;

import java.util.List;

public class Dao {
    private static Query query;
    static {
        query= QueryFactory.createQuery();
    }

    public static boolean checkLogin(String userStr, String passStr) {
       String sql="select * from iuser where username=?";
       Iuser user= (Iuser) query.queryUniqueRow(sql,Iuser.class,new Object[]{userStr});

       if(user!=null&&user.getUserid()!=null){
           System.out.println(user.getUserid()+" "+user.getUsername());
           return true;
       }
       return false;
    }

    public static List queryALL(Class clazz){
        String sql="select * from "+ TableContext.poClassTableMap.get(clazz).getTname();
        List list=query.queryRows(sql,clazz,null);
        return list;
    }

    public static Object queryByName(Class clazz,String name,String vaule){
        TableInfo tableInfo=TableContext.poClassTableMap.get(clazz);
        String sql="select * from "+tableInfo.getTname()+" where "+name+"=?";
        return  query.queryUniqueRow(sql,clazz,new Object[]{vaule});
    }

    public static Object queryById(Class clazz,String id,String vaule){
        TableInfo tableInfo=TableContext.poClassTableMap.get(clazz);
        String sql="select * from "+tableInfo.getTname()+" where "+id+"=?";
        return  query.queryUniqueRow(sql,clazz,new Object[]{vaule});
    }

    public static void upate(Object obj,String[] filedname){
        query.update(obj, filedname);
    }

    public static void insert(Object obj){
        query.insert(obj);
    }

    public static void delete(Object obj,Object id){
        query.delete(obj,id);
    }
    public static void delete(Class clazz,Object id){
        query.delete(clazz,id);
    }
}
