package com.System;

import com.Bean.Invention;
import com.ORM.core.Query;
import com.ORM.core.QueryFactory;
import com.System.iframe.InventionFrame;
import com.System.iframe.PatentFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.net.URL;

public class DesktopFrame extends JFrame {
    JDesktopPane desktopPane=null;
    InventionFrame invention =null;
    PatentFrame patent=null;
    private JLabel back;
    private JPanel topPanel;
    private JButton inbutton;
    private JButton panbutton;

    public DesktopFrame(){
        super();
        setTitle("专利信息管理");
        setBounds(100,100,800,600);
        desktopPane=new DesktopPanel();
        desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        getContentPane().add(desktopPane, BorderLayout.CENTER);
        URL url=this.getClass().getResource("/res/background.jpg");
        ImageIcon icon=new ImageIcon(url);
        back=new JLabel();
        back.setIcon(icon);
        back.setBounds(0,0,800,500);
        desktopPane.add(back,new Integer(Integer.MIN_VALUE));
        getContentPane().add(getTopPanel(),BorderLayout.NORTH);
    }

    private JPanel getTopPanel(){
        if(topPanel==null){
            topPanel=new JPanel();
            topPanel.setBackground(Color.white);
            topPanel.add(getInbutton());
            topPanel.add(getPanbutton());
        }
        return topPanel;
    }

    private JButton getInbutton(){
        if(inbutton==null){
            inbutton=new JButton();
            inbutton.setText("发明管理");
            inbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(invention==null||invention.isClosed()){
                        JInternalFrame[] allFrames=desktopPane.getAllFrames();
                        int titleBarHight=30*allFrames.length;
                        int x=10+titleBarHight,y=x;
                        int width=700,height=450;
                        invention=new InventionFrame("发明管理");
                        invention.setBounds(x,y,width,height);
                        invention.setVisible(true);
                        desktopPane.add(invention);
                    }
                    try {
                        invention.setSelected(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        return inbutton;
    }

    private JButton getPanbutton(){
        if(panbutton==null){
            panbutton=new JButton();
            panbutton.setText("专利管理");
            panbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(patent==null||patent.isClosed()){
                        JInternalFrame[] allFrames=desktopPane.getAllFrames();
                        int titleBarHight=30*allFrames.length;
                        int x=10+titleBarHight,y=x;
                        int width=700,height=450;
                        patent=new PatentFrame("专利管理");
                        patent.setBounds(x,y,width,height);
                        patent.setVisible(true);
                        desktopPane.add(patent);
                    }
                    try {
                        patent.setSelected(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        return panbutton;
    }

}
