package com.System;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
  *@Desctiption:桌面面板
  */
public class DesktopPanel extends JDesktopPane {
  private final Image backImage;
  public DesktopPanel(){
      super();
      URL url=DesktopPanel.class.getResource("/res/back.jpg");
      backImage=new ImageIcon(url).getImage();
  }

    @Override
    public void paintComponents(Graphics g) {
        int width=getWidth();
        int height=this.getHeight();
        g.drawImage(backImage,0,0,width,height,this);
    }
}
