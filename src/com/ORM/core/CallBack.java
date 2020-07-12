package com.ORM.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
  *@Desctiption:回掉接口，不同查询的不同处理方法
  */
public interface CallBack {
    public Object doExcute(Connection con, PreparedStatement ps, ResultSet rs);
}
