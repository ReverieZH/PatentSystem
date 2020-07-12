package com.ORM.utils;


/**
  *@Desctiption:字符串工具类
  */
public class StringUntils {
    /**
    *@param:
    *@return:
    *@Description:将首字母转换为大写
    */
    public static String firstToUpperCase(String str){
        return str.toUpperCase().substring(0,1)+str.substring(1);
    }
}
