package com.ORM.bean;

import java.util.List;
import java.util.Map;

/**
  *@Desctiption:封装一张表的结构信息
  */
public class TableInfo {
    private String tname;
    private Map<String,ColumnInfo> columns;   //存储所有字段
    private ColumnInfo majorKey;  //主键
    private ColumnInfo foreginKet; //外键
    private List<ColumnInfo> prikeys;  //联合主键
    private List<String> columnNames;

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public TableInfo() {
    }

    public TableInfo(String tname, Map<String, ColumnInfo> columns, ColumnInfo majorKey, ColumnInfo foreginKet) {
        this.tname = tname;
        this.columns = columns;
        this.majorKey = majorKey;
        this.foreginKet = foreginKet;
    }

    public TableInfo(String tname,List<ColumnInfo> prikeys, Map<String, ColumnInfo> columns) {
        super();
        this.tname = tname;
        this.columns = columns;
        this.prikeys = prikeys;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Map<String, ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, ColumnInfo> cloumns) {
        this.columns = cloumns;
    }

    public ColumnInfo getMajorKey() {
        return majorKey;
    }

    public void setMajorKey(ColumnInfo majorKey) {
        this.majorKey = majorKey;
    }

    public ColumnInfo getForeginKet() {
        return foreginKet;
    }

    public void setForeginKet(ColumnInfo foreginKet) {
        this.foreginKet = foreginKet;
    }

    public List<ColumnInfo> getPrikeys() {
        return prikeys;
    }

    public void setPrikeys(List<ColumnInfo> prikeys) {
        this.prikeys = prikeys;
    }
}
