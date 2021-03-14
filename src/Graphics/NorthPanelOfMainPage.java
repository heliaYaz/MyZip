package Graphics;

import logic.Program;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NorthPanelOfMainPage extends JPanel implements ActionListener {
    JTextField fileLocation;
    private JButton loadButton, viewButton;
    private JLabel filePathLabel;
    private JFileChooser jFileChooser;
    private JPanel centerPanel, eastPanel, westPanel, southPanel;

    String path="";


    public NorthPanelOfMainPage(){
        this.setLayout(new BorderLayout());
        initEastPanel();
        this.add(eastPanel, BorderLayout.EAST);
        initSouthPanel();
        this.add(southPanel, BorderLayout.SOUTH);
        initWestPanel();
        this.add(westPanel, BorderLayout.WEST);
        initCenterPanel();
        this.add(centerPanel, BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == loadButton){
            loadButtonAction();
            return;
        }
        if(actionEvent.getSource() == viewButton){
            viewButtonAction();
            return;
        }
    }



    private void loadButtonAction(){
        jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
        jFileChooser.addChoosableFileFilter(restrict);
        if(jFileChooser.showDialog(null, "Load") == JFileChooser.APPROVE_OPTION){
            fileLocation.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            String pth=jFileChooser.getSelectedFile().getAbsolutePath();
            if(pth.endsWith("-zipped.txt")){
                Program.getInstance().mainPage.southPanelOfMainPage.enableUnzipButton();
            }
            else if(pth.endsWith(".txt")){
                Program.getInstance().mainPage.southPanelOfMainPage.enableZipButton();
            }
        }
    }


    private void viewButtonAction(){
        jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
        jFileChooser.addChoosableFileFilter(restrict);
        if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            Program.getInstance().mainPage.centerPanelOfMainPage.setFileEditorText(getInputFileText(jFileChooser.getSelectedFile().getAbsolutePath()));
            fileLocation.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            path=jFileChooser.getSelectedFile().getAbsolutePath();
            Program.getInstance().mainPage.centerPanelOfMainPage.enableSaveButton();
        }
    }

    public String getPath(){
        return path;
    }


    private void initCenterPanel(){
        centerPanel = new JPanel(new BorderLayout());
        fileLocation = new JTextField("");
        fileLocation.setEnabled(true);
        centerPanel.add(fileLocation, BorderLayout.CENTER);

    }
    private void initEastPanel(){
        eastPanel = new JPanel(new BorderLayout());
        loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(80, 40));
        loadButton.addActionListener(this);
        eastPanel.add(loadButton, BorderLayout.CENTER);
    }
    private void initWestPanel(){
        westPanel = new JPanel(new BorderLayout());
        filePathLabel = new JLabel("File Path:");
        filePathLabel.setHorizontalAlignment(JTextField.CENTER);
        filePathLabel.setPreferredSize(new Dimension(90, 40));
        westPanel.add(filePathLabel, BorderLayout.CENTER);
    }
    private void initSouthPanel(){
        southPanel = new JPanel(new BorderLayout());
        southPanel.setPreferredSize(new Dimension(100, 40));
        viewButton = new JButton("view");
        viewButton.addActionListener(this);
        southPanel.add(viewButton, BorderLayout.CENTER);
    }



    public void disableLoadButton(){
        loadButton.setEnabled(false);
    }
    public String selectedFilePath(){
        return fileLocation.getText();
    }

    private String getInputFileText(String path){
        Path filePath = Paths.get(path);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e){
            System.out.println("path of the selected file is incorrect");
        }

        String fileText = "";
        for(String string : lines){
            fileText = fileText + string + "\n";
        }
        return fileText;
    }
}
