package com.System.Login;

import com.System.DesktopFrame;
import com.System.JDBC.Dao;
import com.System.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField userfield;
    private JPasswordField passfield;
    private MainFrame mainFrame;
    private DesktopFrame desktopFrame;
    private JButton loginButton =null;
    private JButton exitButton=null;
    private String userStr;
    private JLabel getUserLabel(){
        if(userLabel==null){
            userLabel=new JLabel("用户名");
            userLabel.setBounds(new Rectangle(85, 41, 56, 18));
        }
        return userLabel;
    }

    private JLabel getPassLabel(){
        if(passLabel==null){
            passLabel=new JLabel("密码");
            passLabel.setBounds(new Rectangle(86, 71, 55, 18));
        }
        return passLabel;
    }

    private JTextField getUserfield(){
        if(userfield==null){
            userfield=new JTextField();
            userfield.setBounds(new Rectangle(142, 39, 127, 22));
        }
        return userfield;
    }
    private JPasswordField getPassfield(){
        if(passfield==null){
            passfield=new JPasswordField();
            passfield.setBounds(new Rectangle(143, 69, 125, 22));// 设置“密码”文本框的位置
            passfield.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if(e.getKeyChar()=='\n')
                        loginButton.doClick();
                }
            });
        }
        return passfield;
    }

    private JButton getLoginButton(){
        if(loginButton==null){
            loginButton=new JButton();
            loginButton.setBounds(new Rectangle(109, 114, 48, 20));
            loginButton.setIcon(new ImageIcon(getClass().getResource("/res/loginButton.jpg")));// 设置“登录”按钮的图标
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        userStr = userfield.getText();// 获得“用户名”文本框中的内容
                        String passStr = new String(passfield.getPassword());// 获得“密码”文本框中的内容
                        if (!Dao.checkLogin(userStr, passStr)) {// 验证用户名、密码失败
                            JOptionPane.showMessageDialog(Login.this, "用户名与密码无法登录", "登录失败", JOptionPane.ERROR_MESSAGE);// 弹出“登录失败”对话框
                            return;
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    desktopFrame=new DesktopFrame();
                    desktopFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    desktopFrame.setVisible(true);
                  //  mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置主窗体的关闭方式
                    //mainFrame.setVisible(true);// 使主窗体可见
                    setVisible(false);// 使登录窗体不可见
                }
            });
        }
        return loginButton;
    }

    private JButton getExitButton(){
        if(exitButton==null){
            exitButton=new JButton();
            exitButton.setBounds(new Rectangle(181, 114, 45, 20));
            exitButton.setIcon(new ImageIcon(this.getClass().getResource("/res/exitButton.jpg")));// 设置“退出”按钮的图标
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return exitButton;
    }

    private LoginPanel getLoginPanel(){
        LoginPanel loginPanel=new LoginPanel();
     //   loginPanel.setLayout(new GridLayout(3,2,10,10));
        loginPanel.setLayout(null);
        loginPanel.add(getUserLabel());
        loginPanel.add(getUserfield());
        loginPanel.add(getPassLabel());
        loginPanel.add(getPassfield());
        loginPanel.add(getLoginButton());
        loginPanel.add(getExitButton());
        return loginPanel;
    }
    private void init(){
        setSize(296,188);
        setLocation(500,200);
        setTitle("登陆");
        setContentPane(getLoginPanel());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public Login()  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.System.JDBC.Dao");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        init();


    }
}
