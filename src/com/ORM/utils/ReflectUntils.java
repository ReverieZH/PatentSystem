package com.ORM.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
  *@Desctiption:反射工具类
  */
public class ReflectUntils {
    
    /**
    *@param:
    *@return:
    *@Description:
    */
    public static Object invokeGet(String filedname,Object obj){
        try {
            Class c=obj.getClass();
            Method m=c.getDeclaredMethod("get"+StringUntils.firstToUpperCase(filedname),null);
            return m.invoke(obj,null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
    
    /**
    *@param:
    *@return:
    *@Description:
    */
    public static void invokeSet(String filename,Object obj,Object value){
        try {
            Class c=obj.getClass();
            if(value!=null){
                Method m=c.getDeclaredMethod("set"+StringUntils.firstToUpperCase(filename),value.getClass());
                m.invoke(obj,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
