package com.ORM.bean;


/**
  *@Desctiption:封装表中一个字段的信息
  */
public class ColumnInfo {
    private String name;  //字段名称
    private String dataType;  //字段的数据类型
    private int ketType;    //字段的建类型   0表示普通建 1表示主键 2表示外键

    public ColumnInfo() {
    }

    public ColumnInfo(String name, String dataType, int ketType) {
        this.name = name;
        this.dataType = dataType;
        this.ketType = ketType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getKetType() {
        return ketType;
    }

    public void setKetType(int ketType) {
        this.ketType = ketType;
    }
}
