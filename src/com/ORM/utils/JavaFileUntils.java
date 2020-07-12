package com.ORM.utils;


import com.ORM.bean.ColumnInfo;
import com.ORM.bean.FiledGetSet;
import com.ORM.bean.TableInfo;
import com.ORM.core.DBManager;
import com.ORM.core.MySQL.MySQLTypeConvertor;
import com.ORM.core.TypeConvertor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*@param:
*@return:
*@Description:  用于生成java文件的工具类
*/
public class JavaFileUntils {

    /**
     *@param:ColumnInfo 字段信息
     *@param:TypeConvertor 类型转换器
     *@return:FiledGetSet
     *@Description:用于生成字段信息的属性,get和set源码
     */
    public static FiledGetSet createGetSet(ColumnInfo columnInfo, TypeConvertor convertor){
        FiledGetSet filedsrc=new FiledGetSet();

        String filedType=convertor.DbTypeToJavaType(columnInfo.getDataType());  //属性类型
        //生成属性源码
        filedsrc.setFieldInfo("\tprivate "+filedType+" "+columnInfo.getName()+";\n");


        //生成属性的get源码
        StringBuilder getsrc=new StringBuilder();
        getsrc.append("\tpublic "+filedType+" "+"get"+StringUntils.firstToUpperCase(columnInfo.getName())+"(){\n");
        getsrc.append("\t\treturn "+columnInfo.getName()+";\n");
        getsrc.append("\t}\n");
        filedsrc.setGetInfo(getsrc.toString());

        //生成属性的set源码
        StringBuilder setsrc=new StringBuilder();
        setsrc.append("\tpublic void "+"set"+StringUntils.firstToUpperCase(columnInfo.getName())+"("+filedType+" "+columnInfo.getName()+"){\n");
        setsrc.append("\t\tthis."+columnInfo.getName()+"="+columnInfo.getName()+";\n");
        setsrc.append("\t}\n");
        filedsrc.setSetInfo(setsrc.toString());

        return filedsrc;
    }



    /**
     *@param:
     *@return:
     *@Description:
     */
    public static String createJavaSrc(TableInfo tableInfo,TypeConvertor convertor){
        Map<String,ColumnInfo> columns=tableInfo.getColumns();

        List<FiledGetSet> javaFileds=new ArrayList<>();

        for(ColumnInfo c:columns.values()){
            javaFileds.add(createGetSet(c,convertor));
        }

        StringBuilder src=new StringBuilder();

        //生成package语句
        src.append("package "+ DBManager.getConf().getPoPackage()+";\n\n");
        //生成import语句
        src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");

        //生产类声明
        src.append("public class "+StringUntils.firstToUpperCase(tableInfo.getTname())+"{\n");

        //生成属性列表
        for(FiledGetSet f:javaFileds){
            src.append(f.getFieldInfo());
        }
        src.append("\n\n");

        //生成get方法
        for(FiledGetSet f:javaFileds){
            src.append(f.getGetInfo());
        }
        for(FiledGetSet f:javaFileds){
            src.append(f.getSetInfo());
        }
        src.append("}\n");
        return src.toString();
    }



    /**
     *@param:
     *@return:
     *@Description:生成java文件
     */
    public static void createJavaFile(TableInfo tableInfo,TypeConvertor convertor){
        //生成源代码
        String src=createJavaSrc(tableInfo,convertor);

        //获取路径
        String srcPath=DBManager.getConf().getSrcPath()+"\\";
        String packagePath=DBManager.getConf().getPoPackage().replaceAll("\\.","/");
        System.out.println(srcPath);
        System.out.println(DBManager.getConf().getPoPackage());
        System.out.println(packagePath);
        //建立路径
        File javaFile=new File(srcPath+packagePath);
        if(!javaFile.exists()){
            javaFile.mkdirs();
        }

        BufferedWriter bw=null;
        System.out.println(javaFile.getAbsolutePath()+"-------------");
        try {
            bw=new BufferedWriter(new FileWriter(javaFile.getAbsoluteFile()+"/"+StringUntils.firstToUpperCase(tableInfo.getTname())+".java"));
            bw.write(src);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
                if(bw!=null){
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
   /* public static void main(String[] args) {
        ColumnInfo ci=new ColumnInfo("id","int",0);
        FiledGetSet f=createGetSet(ci, new MySQLTypeConvertor());
        System.out.println(f);
    }*/
}
