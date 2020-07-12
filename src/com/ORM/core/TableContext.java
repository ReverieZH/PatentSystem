package com.ORM.core;

import com.ORM.bean.ColumnInfo;
import com.ORM.bean.FiledGetSet;
import com.ORM.bean.TableInfo;
import com.ORM.core.MySQL.MySQLTypeConvertor;
import com.ORM.utils.JavaFileUntils;
import com.ORM.utils.StringUntils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableContext {

    /**
     * 表名为key，表信息对象为value
     */
    public static Map<String, TableInfo> tables = new HashMap<String,TableInfo>();

    /**
     * 将po的class对象和表信息对象关联起来，便于重用！
     */
    public static  Map<Class,TableInfo>  poClassTableMap = new HashMap<Class,TableInfo>();

    private TableContext(){}

    static {
        try {
            //初始化获得表的信息
            Connection con = DBManager.getConn();
            DatabaseMetaData dbmd = con.getMetaData();

            ResultSet tableRet = dbmd.getTables(DBManager.getConf().getSchema(), "%","%",new String[]{"TABLE"});

            while(tableRet.next()){
                String tableName = (String) tableRet.getObject("TABLE_NAME");

                TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>(),new HashMap<String, ColumnInfo>());


                ResultSet set = dbmd.getColumns(null, "%", tableName, "%");  //查询表中的所有字段
                List<String> columnNames=new ArrayList<>();
                while(set.next()){
                    ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),
                            set.getString("TYPE_NAME"), 0);
                    ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
                    columnNames.add(set.getString("COLUMN_NAME"));
                }
                ti.setColumnNames(columnNames);


                ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);  //查询表中的主键
                while(set2.next()){
                    ColumnInfo ci2 = (ColumnInfo) ti.getColumns().get(set2.getObject("COLUMN_NAME"));
                    System.out.println(" 主键名：------------------"+ci2.getName());
                    ci2.setKetType(1);  //设置为主键类型
                    ti.getPrikeys().add(ci2);
                }

                if(ti.getPrikeys().size()>0){  //取唯一主键。。方便使用。如果是联合主键。则为空！
                    ti.setMajorKey(ti.getPrikeys().get(0));
                }
                tables.put(tableName, ti);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //更新类结构
//		updateJavaPOFile();
    }


    /**
     *@param:
     *@return:
     *@Description:根据表结构，生成java类
     */
    public static void createPOFile(){
         for(TableInfo t:tables.values()){
             JavaFileUntils.createJavaFile(t,new MySQLTypeConvertor());
         }
    }


    /**
     *@param:
     *@return:
     *@Description:加载类
     */
    public static void loadPoFile(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>load>>>>>>>>>>>>>>>>>>>>>");
        for(TableInfo t:tables.values()){
            try {
                Class c=Class.forName(DBManager.getConf().getPoPackage()+"."+ StringUntils.firstToUpperCase(t.getTname()));
                poClassTableMap.put(c,t);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

/*
    public static void main(String[] args) {
        Map<String,TableInfo>  tables = TableContext.tables;
        for(String t:tables.keySet()){
             TableInfo tableInfo=tables.get(t);
            System.out.println(tableInfo.getTname());
        }
      *//*  System.out.println("-----------------");
        TableInfo t1=tables.get("iuser");
        System.out.println(t1.getTname());
        Map<String,ColumnInfo> c=t1.getColumns();
        for(String s:c.keySet()){
            System.out.print(c.get(s).getName()+" ");
        }
        System.out.println("");
        System.out.println("--------------------");
        TableInfo t2=tables.get("patent");
        System.out.println(t2.getTname());
        Map<String,ColumnInfo> c2=t2.getColumns();
        for(String s:c2.keySet()){
            System.out.print(c2.get(s).getName()+" ");
        }
        System.out.println("");
        System.out.println("--------------------");
        TableInfo t3=tables.get("invention");
        System.out.println(t3.getTname());
        Map<String,ColumnInfo> c3=t3.getColumns();
        for(String s:c3.keySet()){
            System.out.println(c3.get(s).getName()+" "+c3.get(s).getDataType()+" "+c3.get(s).getKetType()+" ");
            FiledGetSet getSet=JavaFileUntils.createGetSet(c3.get(s),new MySQLTypeConvertor());
            System.out.println(getSet.getFieldInfo()+"》》》》》》》》》》》》");
        }
        System.out.println("");
        System.out.println("--------------------");

        JavaFileUntils.createJavaFile(t3,new MySQLTypeConvertor());
        System.out.println("生成类文件");*//*

    }*/
}
