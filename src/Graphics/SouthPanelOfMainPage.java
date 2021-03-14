package Graphics;

import logic.Program;
import logic.Zip;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SouthPanelOfMainPage extends JPanel implements ActionListener {

    private JTextField folderName;
    private JFileChooser jFileChooser;
    private JButton zipButton, unzipButton;
    JTextField  password;
    private JLabel resultFolder;
    private JPanel centerPanel, westPanel, southPanel, northPanel;

    SouthPanelOfMainPage(){
        this.setLayout(new BorderLayout());
        initSouthPanel();
        this.add(southPanel, BorderLayout.SOUTH);
        initWestPanel();
        this.add(westPanel, BorderLayout.WEST);
        initCenterPanel();
        this.add(centerPanel, BorderLayout.CENTER);
        initNorthPanel();
        this.add(northPanel, BorderLayout.NORTH);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == zipButton){
            if(Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText().equals("click Load to choose file")){
                Program.getInstance().mainPage.showErrorMessage("choose file");
                return;
            }
            else{
                String p=Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText();
                if(password.getText().equals("")){
                    if(p.endsWith(".txt"))
                        Zip.zip( p,false,"");
                    else {
                        try {
                            Zip.zipFolder(p,false,"",folderName.getText());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    if(p.endsWith(".txt"))
                        Zip.zip(p,true,password.getText());
                    else {
                        try {
                            Zip.zipFolder(p,true,password.getText(),folderName.getText());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
//            addResultFolderAndRun();
        }

        if(actionEvent.getSource() == unzipButton){
            if(Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText().equals("click Load to choose file")){
                Program.getInstance().mainPage.showErrorMessage("choose file");
                return;
            }
            else{
                Zip.getInstance().unzip(Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText(),password.getText());
            }
//            addResultFolderAndRun();
        }
    }


    private void initNorthPanel(){
        northPanel = new JPanel(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(200, 30));
        JLabel passwordLabel = new JLabel("password:");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setPreferredSize(new Dimension(90, 30));
        northPanel.add(passwordLabel, BorderLayout.WEST);
        password = new JTextField();
        northPanel.add(password, BorderLayout.CENTER);
    }
    private void initCenterPanel(){
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(200, 30));
        folderName = new JTextField("write folder name");
        centerPanel.add(folderName, BorderLayout.CENTER);
    }
    private void initWestPanel(){
        westPanel = new JPanel(new BorderLayout());
        resultFolder = new JLabel("Result folder:");
        resultFolder.setPreferredSize(new Dimension(90, 30));
        resultFolder.setHorizontalAlignment(JLabel.CENTER);
        westPanel.add(resultFolder, BorderLayout.CENTER);
    }
    private void initSouthPanel(){
        southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.setPreferredSize(new Dimension(200, 40));
        zipButton = new JButton("zip");
        zipButton.addActionListener(this);
        zipButton.setEnabled(false);
        southPanel.add(zipButton);
        unzipButton = new JButton("unzip");
        unzipButton.addActionListener(this);
        unzipButton.setEnabled(false);
        southPanel.add(unzipButton);
    }

    public void enableZipButton(){
        zipButton.setEnabled(true);
    }
    public void enableUnzipButton(){
        unzipButton.setEnabled(true);
    }



}
