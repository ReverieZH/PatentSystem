package com.ORM.core;


import com.ORM.bean.ColumnInfo;
import com.ORM.bean.TableInfo;
import com.ORM.core.DBManager;
import com.ORM.utils.JDBCUntils;
import com.ORM.utils.JavaFileUntils;
import com.ORM.utils.ReflectUntils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.XMLFormatter;

/**
  *@Desctiption:负责查询
  */
public abstract class Query implements Cloneable{

    /**
     *@param:
     *@return:
     *@Description: 将查询操作封装成模板
     */
     public Object queryTemplate(String sql,Object[] params,Class clazz,CallBack back){
         Connection con= DBManager.getConn();
         PreparedStatement ps=null;
         ResultSet rs=null;
         try {
             ps=con.prepareStatement(sql);
             JDBCUntils.setPrePargams(ps,params);
             rs=ps.executeQuery();
             return back.doExcute(con,ps,rs);
         } catch (SQLException e) {
             e.printStackTrace();
             return null;
         }finally {
             DBManager.close(con,ps);
         }
     }

     /**
      *@param:
      *@return:返回影响行数
      *@Description:执行DML语句(插入，删除，更新)
      */
     public int excuteDML(String sql,Object[] pargams){
         int count=0;
         Connection con=DBManager.getConn();
         PreparedStatement ps=null;
         try {
             ps=con.prepareStatement(sql);
             JDBCUntils.setPrePargams(ps,pargams);
             count=ps.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             DBManager.close(con,ps);
         }
         return count;
     }

     /**
      *@param:
      *@return:
      *@Description:插入一个对象到数据库
      */
     public void insert(Object obj){
         //insert into 表名(属性1,属性2,属性3） values (?,?,?)
         Class c=obj.getClass();
         List<Object> pargams=new ArrayList<>();   //存储参数
         TableInfo tableInfo=TableContext.poClassTableMap.get(c);
         StringBuilder sql=new StringBuilder("insert into "+tableInfo.getTname()+" (");
         Field[] fields=c.getDeclaredFields();
         for(Field f:fields){
             String fieldname=f.getName();
             Object value= ReflectUntils.invokeGet(fieldname,obj);  //通过get的反射方法获取

             if(value!=null){
                 sql.append(fieldname+",");
                 pargams.add(value);
             }
         }
         sql.setCharAt(sql.length()-1,')');
         sql.append(" values (");
         for(int i=0;i<pargams.size();i++){
             sql.append("?,");
         }
         sql.setCharAt(sql.length()-1,')');

         excuteDML(sql.toString(),pargams.toArray());
     }


     /**
      *@param:
      *@return:
      *@Description:在数据库中根据id值删除某一对象
      */
     public void delete(Object obj,Object id){
         //delete from 表名 where 主键名=?
         Class c=obj.getClass();
         TableInfo tableInfo=TableContext.poClassTableMap.get(c);
         ColumnInfo key=tableInfo.getMajorKey();   //获取主键

         String sql="delete from "+tableInfo.getTname()+" where "+key.getName()+"=?";
         excuteDML(sql,new Object[]{id});
     }

     public void delete(Class clazz,Object id){
         TableInfo tableInfo=TableContext.poClassTableMap.get(clazz);
         ColumnInfo key=tableInfo.getMajorKey();   //获取主键

         String sql="delete from "+tableInfo.getTname()+" where "+key.getName()+"=?";
         excuteDML(sql,new Object[]{id});
     }

     /**
      *@param:
      *@return:
      *@Description:删除某一对象
      */
     public void delete(Object obj){
         Class c=obj.getClass();
         TableInfo tableInfo=TableContext.poClassTableMap.get(c);
         ColumnInfo key=tableInfo.getMajorKey();

         Object value=ReflectUntils.invokeGet(key.getName(),obj);
         delete(obj,value);
     }


     /**
      *@param:
      *@return:
      *@Description:在表中更新某一对象
      */
     public void update(Object obj,String[] fieldNames){
         //upadte 表名 set 属性1=?，属性2=? where 主键名=?
         Class c=obj.getClass();
         TableInfo tableInfo=TableContext.poClassTableMap.get(c);
         ColumnInfo key=tableInfo.getMajorKey();
         List<Object> params=new ArrayList<>();

         StringBuilder sql=new StringBuilder("update "+tableInfo.getTname()+" set ");
         for(String f:fieldNames){
             sql.append(f+"=?,");
             Object value=ReflectUntils.invokeGet(f,obj);
             params.add(value);
         }
         sql.setCharAt(sql.length()-1,' ');
         sql.append("where "+key.getName()+"=?");
         params.add(ReflectUntils.invokeGet(key.getName(),obj));

         excuteDML(sql.toString(),params.toArray());
     }


     /**
      *@param:
      *@return:
      *@Description:查询多行记录
      */
     public List queryRows(final String sql,final Class clazz,final Object[] paragms){
         return (List) queryTemplate(sql, paragms, clazz, new CallBack() {
             @Override
             public Object doExcute(Connection con, PreparedStatement ps, ResultSet rs) {
                 List list=null;
                 try {
                     ResultSetMetaData data=rs.getMetaData();

                     while(rs.next()){
                        if(list==null){
                            list=new ArrayList();
                        }

                        Object obj=clazz.newInstance();

                        for(int i=0;i<data.getColumnCount();i++){
                            String columnName=data.getColumnLabel(i+1);
                            Object value=rs.getObject(i+1);

                            ReflectUntils.invokeSet(columnName,obj,value);
                        }
                        list.add(obj);
                     }
                 } catch (SQLException e) {
                     e.printStackTrace();
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 } catch (InstantiationException e) {
                     e.printStackTrace();
                 }
                 return list;
             }
         });
     }

     /**
      *@param:
      *@return:
      *@Description:查询一行记录
      */
     public Object queryUniqueRow(String sql,Class clazz,Object[] params){
         List list=queryRows(sql, clazz, params);
         return (list!=null&&list.size()>0)?list.get(0):null;
     }

     /**
      *@param:
      *@return:
      *@Description:根据主键值查找记录
      */
     public Object queryById(Class clazz,Object id){
         TableInfo tableInfo=TableContext.poClassTableMap.get(clazz);
        if(tableInfo==null){
            System.out.println("表为空");
        }
         System.out.println(tableInfo.getMajorKey());
         ColumnInfo key=tableInfo.getMajorKey();

         String sql="select * from "+tableInfo.getTname()+" where "+key.getName()+"=?";

         return queryUniqueRow(sql,clazz,new Object[]{id});
     }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
