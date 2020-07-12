package com.ORM.core.MySQL;

import com.ORM.core.TypeConvertor;


/**
  *@Desctiption:mySQl数据类型和Java数据类型的相互转换
  */
public class MySQLTypeConvertor implements TypeConvertor {
    @Override
    public String DbTypeToJavaType(String columnType) {
        if("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType)){
            return "java.lang.String";
        }else if("text".equalsIgnoreCase(columnType)||"tinytext".equalsIgnoreCase(columnType)||"mediumtext".equalsIgnoreCase(columnType)||"longtext".equalsIgnoreCase(columnType)){
            return "java.lang.String";
        } else if("int".equalsIgnoreCase(columnType)||"tinyint".equalsIgnoreCase(columnType)||"smallint".equalsIgnoreCase(columnType)||"integer".equalsIgnoreCase(columnType)){
            return "java.lang.Integer";
        }else if("bigint".equalsIgnoreCase(columnType)){
            return "java.lang.Long";
        }else if("doble".equalsIgnoreCase(columnType)||"float".equalsIgnoreCase(columnType)){
            return "java.lang.Double";
        }else if("clob".equalsIgnoreCase(columnType)){
            return "java.sql.Clob";
        }else if("blob".equalsIgnoreCase(columnType)){
            return "java.sql.Blob";
        }else if("date".equalsIgnoreCase(columnType)){
            return "java.sql.Date";
        }else if("time".equalsIgnoreCase(columnType)){
            return "java.sql.Time";
        }else if("timestamp".equalsIgnoreCase(columnType)){
            return "java.sql.Timestamp";
        }
       return null;
    }

    @Override
    public String JavaTypeToDbType(String dataType) {
        return null;
    }
}
