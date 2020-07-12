package com.System.iframe;

import com.Bean.Invention;
import com.ORM.bean.ColumnInfo;
import com.ORM.bean.TableInfo;
import com.ORM.core.MySQL.MySQLTypeConvertor;
import com.ORM.core.Query;
import com.ORM.core.QueryFactory;
import com.ORM.core.TableContext;
import com.ORM.utils.StringUntils;
import com.System.JDBC.Dao;
import com.System.Login.Login;
import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class InventionFrame extends JInternalFrame {
    private JPanel contenpane=null;
    private JPanel topPanel=null;
    private JPanel bottomPanel=null;
    private JScrollPane tablePane=null;
    private JTable table=null;

    private JLabel idLabel=new JLabel("发明号");
    private JTextField idTextfield=new JTextField(10);
    private JButton QIdButton=null;

    private JLabel nameLabel=new JLabel("发明名称");
    private JTextField nameTextField=new JTextField(10);
    private JButton QNameButton=null;

    private JButton addButton=null;
    private JButton removeButton=null;
    private JButton upadteButton=null;

    private InventionFrame frame=this;

    private Map<String,ColumnInfo> map;
    public InventionFrame(String title){
        super();
        init(title);
    }

    private void init(String title){
        map=TableContext.tables.get("invention").getColumns();
        setTitle(title);
        setResizable(true);  //设置允许自由调整大小
        setClosable(true);    //设置可关闭
        setIconifiable(true);   //设置可最小化
        setMaximizable(true);    //设置可最大化
        setContentPane(getContenpane());
    }

    private JButton getQIdButton(){
        if(QIdButton==null){
            QIdButton=new JButton();
            QIdButton.setText("发明号查找");
            QIdButton.setSize(90,30);
            QIdButton.setLocation(280,30);
            QIdButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id=idTextfield.getText();
                    if(id!=null){
                        Query query= QueryFactory.createQuery();
                        Invention invention= (Invention) query.queryById(Invention.class,id);
                      //  System.out.println(invention.getInvid()+"-------"+invention.getInvname()+"-----"+invention.getInvnum());
                        if(invention!=null) {
                            clearAllRows();  //清除所有行
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            Vector<String> rowV=new Vector<>();
                            String columnNames[]=getColumnNames();
                            Field[] fields=invention.getClass().getDeclaredFields();
                            for(int i=0;i<fields.length;i++){
                                try {
                                    Field field=invention.getClass().getDeclaredField(columnNames[i]);
                                    field.setAccessible(true);
                                    Object obj=field.get(invention);
                                    rowV.add(String.valueOf(obj));
                                } catch (NoSuchFieldException ex) {
                                    ex.printStackTrace();
                                } catch (IllegalAccessException ex) {
                                    ex.printStackTrace();
                                }
                                /*fields[i].setAccessible(true);
                                try {
                                    Object obj=fields[i].get(invention);
                                    rowV.add(String.valueOf(obj));
                                } catch (IllegalAccessException ex) {
                                    ex.printStackTrace();
                                }*/
                            }
                            model.addRow(rowV);
                        }
                    }
                }
            });
        }
        return QIdButton;
    }


    private JButton getQNameButton(){
        if(QNameButton==null){
            QNameButton=new JButton();
            QNameButton.setText("发明名查找");
            QNameButton.setSize(90,30);
            QNameButton.setLocation(280,30);
            QNameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name=nameTextField.getText();
                    if(name!=null){
                        Query query= QueryFactory.createQuery();
                        Invention invention= (Invention) Dao.queryByName(Invention.class,"invname",name);
                        //  System.out.println(invention.getInvid()+"-------"+invention.getInvname()+"-----"+invention.getInvnum());
                        if(invention!=null) {
                            clearAllRows();  //清除所有行
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            Vector<String> rowV=new Vector<>();
                            String columnNames[]=getColumnNames();
                           // Field[] fields=invention.getClass().getDeclaredFields();
                            for(int i=0;i<columnNames.length;i++){
                                try {
                                    Field field=invention.getClass().getDeclaredField(columnNames[i]);
                                    field.setAccessible(true);
                                    Object obj=field.get(invention);
                                    rowV.add(String.valueOf(obj));
                                } catch (NoSuchFieldException ex) {
                                    ex.printStackTrace();
                                } catch (IllegalAccessException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            model.addRow(rowV);
                        }
                    }
                }
            });
        }
        return QNameButton;
    }
    private JPanel getTopPanel(){
        if(topPanel==null){
            topPanel=new JPanel();
            topPanel.setLayout(new FlowLayout(0,10,10));
            idLabel.setSize(100,30);
//            idLabel.setLocation(30,30);
            topPanel.add(idLabel);

//            idTextfield.setLocation(90,30);
            topPanel.add(idTextfield);
            topPanel.add(getQIdButton());
            topPanel.add(nameLabel);
            topPanel.add(nameTextField);
            topPanel.add(getQNameButton());
        }
        return topPanel;
    }


    private String[] getColumnNames(){
        List<String> columns=TableContext.tables.get("invention").getColumnNames();
        String columnNames[]=new String[columns.size()];
        for(int i=0;i<columnNames.length;i++){
            columnNames[i]=columns.get(i);
        }
        return columnNames;
    }

    private JTable initTable(){
        String[] cName=new String[]{"发明编号","发明号","作者","发明名","发明地区","发明机构","申请时间","描述"};
        String columnNames[]=getColumnNames();
        int size=columnNames.length;

        List values= Dao.queryALL(Invention.class);
        if(values.size()<=0||values==null){
            return new JTable();
        }
        String[][] rowVaules = new String[values.size()][columnNames.length];
        for (int i = 0; i < values.size(); i++) {
            Invention invention = (Invention) values.get(i);
            for (int j = 0; j < columnNames.length; j++) {
                try {
                    Field field=invention.getClass().getDeclaredField(columnNames[j]);
                    field.setAccessible(true);
                    rowVaules[i][j] = String.valueOf(field.get(invention));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println();
        JTable t=new JTable();
        t.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));// 设置表格模型的边框样式
        t.setShowGrid(true);// 绘制网格线
        t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //  ((DefaultTableModel) table.getModel()).setColumnIdentifiers(columnNames);// 创建表格模型对象并向其中添加表头
        ((DefaultTableModel) t.getModel()).setDataVector(rowVaules,cName);

        return t;
    }

    public void upadteTable(){
        if(table!=null&&table.getRowCount()>=0){
            tablePane.remove(table);
            tablePane.setViewportView(initTable());
        }
    }


    private JTable getTable(){
        if(table==null){
            table=initTable();
        }
        return table;
    }


    private JScrollPane getTablePane(){
        if(tablePane==null){
            tablePane=new JScrollPane();
            tablePane.setViewportView(getTable());
        }
        return tablePane;
    }

    private JButton getAddButton(){
        if(addButton==null){
            addButton=new JButton();
            addButton.setText("添加");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddUpdateInvFrame addUpdateInvFrame=new AddUpdateInvFrame("添加",Invention.class,frame,null);
                    addUpdateInvFrame.setVisible(true);
                }
            });
        }
        return addButton;
    }

    private JButton getUpadteButton(){
        if(upadteButton==null){
            upadteButton=new JButton();
            upadteButton.setText("修改");
            upadteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row=table.getSelectedRow();
                    if(row<0) {
                        JOptionPane.showMessageDialog(InventionFrame.this, "请选中一行", "提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Class clazz=Invention.class;
                    try {
                        Object obj=clazz.newInstance();
                        String[] columnNames=getColumnNames();
                        for(int i=0;i<columnNames.length;i++){
                            ColumnInfo columnInfo=map.get(columnNames[i]);
                            String datatype=new MySQLTypeConvertor().DbTypeToJavaType(columnInfo.getDataType());
                            Class pargam=Class.forName(datatype);

                            Method method=clazz.getDeclaredMethod("set"+ StringUntils.firstToUpperCase(columnNames[i]),pargam);
                            String p;
                            Integer I;
                            Date date;
                            if(datatype.equals("java.lang.String")){
                                p=new String(String.valueOf(table.getValueAt(row,i)));
                                method.invoke(obj, p);
                            }else if(datatype.equals("java.lang.Integer")){
                                I=new Integer(String.valueOf(table.getValueAt(row,i)));
                                method.invoke(obj, I);
                            }else if(datatype.equals("java.sql.Date")){
                                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                                java.util.Date da=sf.parse(String.valueOf(table.getValueAt(row,i)));
                                java.sql.Date d=new Date(da.getTime());
                                method.invoke(obj,d);
                            }
                        }

                        AddUpdateInvFrame addUpdateInvFrame=new AddUpdateInvFrame("修改",Invention.class,frame,obj);
                        addUpdateInvFrame.setVisible(true);
                    } catch (InstantiationException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchMethodException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                }
            });
        }
        return upadteButton;
    }

    private JButton getRemoveButton(){
        if(removeButton==null){
            removeButton=new JButton();
            removeButton.setText("删除");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row=table.getSelectedRow();
                    if(row<0)
                        return;
                    String id= String.valueOf(table.getValueAt(row,0));
                    Dao.delete(Invention.class,id);
                    upadteTable();
                }
            });
        }
        return removeButton;
    }
    private JPanel getBottomPanel(){
        if(bottomPanel==null){
            bottomPanel=new JPanel();
            bottomPanel.add(getAddButton());
            bottomPanel.add(getUpadteButton());
            bottomPanel.add(getRemoveButton());
        }
        return bottomPanel;
    }

    private JPanel getContenpane(){
        if(contenpane==null){
            contenpane=new JPanel();
            contenpane.setLayout(new BorderLayout());
            contenpane.add(getTopPanel(),BorderLayout.NORTH);
            contenpane.add(getTablePane(),BorderLayout.CENTER);
            contenpane.add(getBottomPanel(),BorderLayout.SOUTH);
        }
        return contenpane;
    }

    private void clearAllRows(){
        DefaultTableModel tableModel= (DefaultTableModel) table.getModel();
       tableModel.setRowCount(0);
    }
}
