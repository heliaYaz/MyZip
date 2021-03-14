package Graphics;
import logic.Program;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    public CenterPanelOfMainPage centerPanelOfMainPage;
    public SouthPanelOfMainPage southPanelOfMainPage;
    public NorthPanelOfMainPage northPanelOfMainPage;
    public Dimension frameSize;

    public MainPage(){
        frameSize = new Dimension(1000,800);
        this.setSize(frameSize);
        this.setLocation(100, 100);
        this.setTitle("HZip");
        this.setLayout(new BorderLayout());

        northPanelOfMainPage = new NorthPanelOfMainPage();
        northPanelOfMainPage.setPreferredSize(new Dimension(frameSize.width - 100, 80));
        this.add(northPanelOfMainPage, BorderLayout.NORTH);

        centerPanelOfMainPage = new CenterPanelOfMainPage();
        this.add(centerPanelOfMainPage, BorderLayout.CENTER);

        southPanelOfMainPage = new SouthPanelOfMainPage();
        southPanelOfMainPage.setPreferredSize(new Dimension(200, 100));
        this.add(southPanelOfMainPage, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void showErrorMessage(String error){
        JOptionPane.showMessageDialog(Program.getInstance().mainPage, error, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void showMsg(String msg){
        JOptionPane.showMessageDialog(Program.getInstance().mainPage, msg, "!", JOptionPane.INFORMATION_MESSAGE);

    }


}
