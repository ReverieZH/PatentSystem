package com.ORM.core;


/**
*@param:
*@return:
*@Description:java数据类型与数据库数据类型的相互转换
*/
public interface TypeConvertor {
    /**
    *@param:
    *@return:
    *@Description:  用于将数据库类型转换为Java数据类型
    */
    public String DbTypeToJavaType(String columnType);


    /**
    *@param:
    *@return:
    *@Description:  用于将Java类型转换为数据库类型
    */
    public String JavaTypeToDbType(String dataType);
}
