package com.System.iframe;

import com.ORM.bean.ColumnInfo;
import com.ORM.bean.TableInfo;
import com.ORM.core.MySQL.MySQLTypeConvertor;
import com.ORM.core.TableContext;
import com.ORM.utils.StringUntils;
import com.System.DesktopFrame;
import com.System.JDBC.Dao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddUpdateInvFrame extends JFrame {
    private DesktopFrame main;
    private JPanel panel=new JPanel();
    private JButton button;
    private List<JLabel> jLabels=new ArrayList<>();
    private List<JTextField> textFields=new ArrayList<>();
    private List<String> columns;
    private String columnNames[];  //列名
    private Map<String,String> nameMap=new HashMap<>();
    private TableInfo tableInfo;
    private Map<String, ColumnInfo> map;
    private InventionFrame father;
    private AddUpdateInvFrame frame=this;
    private Date d;



    public AddUpdateInvFrame(String type, Class clazz, InventionFrame father,Object obj){
        tableInfo= TableContext.tables.get("invention");
        columns=tableInfo.getColumnNames();
        map=tableInfo.getColumns();
        columnNames=new String[columns.size()];
        String[] cName=new String[]{"发明编号","发明号","作者","发明名","发明地区","发明机构","申请时间","描述"};
        for(int i=0;i<columnNames.length;i++){
            columnNames[i]=columns.get(i);
            nameMap.put(columns.get(i),cName[i]);
        }
       panel.setLayout(new GridLayout(columns.size(),2,5,5));
        for(int i=0;i<columns.size();i++){
            JLabel label=new JLabel(cName[i]);
            JTextField text=new JTextField(10);
            ColumnInfo columnInfo=map.get(columns.get(i));
            if(new MySQLTypeConvertor().DbTypeToJavaType(columnInfo.getDataType()).equals("java.sql.Date")){
                d=new Date(System.currentTimeMillis());
                SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                text.setText(ft.format(d));
            }

            if(type.equals("修改")){
                try {
                    Method method=clazz.getDeclaredMethod("get"+StringUntils.firstToUpperCase(columns.get(i)),null);
                    Object value=method.invoke(obj,null);
                    text.setText(String.valueOf(value));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

            panel.add(label);
            panel.add(text);
            jLabels.add(label);
            textFields.add(text);
        }

        JPanel bottompanel=new JPanel();
        bottompanel.add(getButton(type,clazz,obj));
        JPanel content=new JPanel();
        content.add(panel);
        content.add(bottompanel);
        setContentPane(content);
        setResizable(false);
        setSize(290,400);
        setLocation(90,280);
        this.father=father;
    }
    private JButton getButton(String type,Class clazz,Object obj){
        if(button==null){
            button=new JButton(type);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Object obj=clazz.newInstance();
                        for(int i=0;i<columns.size();i++){
                            ColumnInfo columnInfo=map.get(columns.get(i));
                            //System.out.println(columnInfo.getDataType()+"   000000");
                            String datatype=new MySQLTypeConvertor().DbTypeToJavaType(columnInfo.getDataType());
                            Class pargam=Class.forName(datatype);

                            Method method=clazz.getDeclaredMethod("set"+ StringUntils.firstToUpperCase(columns.get(i)),pargam);
                            System.out.println(method.getName());
                            System.out.println(textFields.get(i).getText());
                            String p;
                            Integer I;
                            Date date;
                            if(datatype.equals("java.lang.String")){
                                p=new String(textFields.get(i).getText());
                                method.invoke(obj, p);
                            }else if(datatype.equals("java.lang.Integer")){
                                I=new Integer(textFields.get(i).getText());
                                method.invoke(obj, I);
                            }else if(datatype.equals("java.sql.Date")){
                                method.invoke(obj,d);
                            }
                        }
                        if(type.equals("添加")){
                            Dao.insert(obj);
                        }else if(type.equals("修改")){
                            Dao.upate(obj,columnNames);
                        }
                        father.upadteTable();
                        frame.setVisible(false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        return button;
    }
}
