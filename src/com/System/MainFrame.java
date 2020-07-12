package com.System;

import com.System.Login.Login;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.*;

public class MainFrame extends JFrame{
    private JPanel contentPane=null;    //内容面板
    private JButton button=new JButton();
    private JButton button2=new JButton();
    private DesktopPanel desktopPane=null;


    public static void main(String[] args) {
        //new Example().create();
//        JFrame login=new LoginDialog();   //登录窗体
//        login.setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置登录窗体的关闭方式
//        login.setVisible(true);  //登陆窗体可见*/
        Login login=new Login();
    }

    public MainFrame(){
        super();
        initialize();// 初始化主窗体的方法
    }

    private void initialize() {
        this.setSize(800,600);
        this.setContentPane(getFrameContentPane());
        this.setTitle("专利信息管理系统");
    }


    private JPanel getFrameContentPane(){
        if(contentPane==null){
            contentPane=new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(new JPanel(),NORTH);
            contentPane.add(getDesktopPane(),CENTER);
            contentPane.add(new JPanel(),SOUTH);
        }
        return contentPane;
    }

    private DesktopPanel getDesktopPane() {
        if(desktopPane==null){
            desktopPane=new DesktopPanel();
        }
        return desktopPane;
    }

}
