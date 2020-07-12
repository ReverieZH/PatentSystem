package com.ORM.core;

import com.ORM.bean.TableInfo;

public class QueryFactory {
    private static Query query;
    static {
        try {
            Class c=Class.forName(DBManager.getConf().getQueryClass());
            query= (Query) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

       TableContext.loadPoFile();
    }


    private QueryFactory(){    //   构造器私有化
    }

    /**
     *@param:
     *@return:
     *@Description:
     */
    public static Query createQuery(){
        try {
            return (Query) query.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
