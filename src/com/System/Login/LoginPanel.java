package com.System.Login;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


/**
  *@Desctiption:登录面板
  */
public class LoginPanel extends JPanel {
    public int width,height;    //面板的宽高
    private Image img;        //登陆背景图片

    public LoginPanel(){
        super();
        URL url=getClass().getResource("/res/login.jpg");
        img=new ImageIcon(url).getImage();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(img,0,0,this);
    }
}
