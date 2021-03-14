package Graphics;

import logic.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CenterPanelOfMainPage extends JPanel implements ActionListener {
    private JTextArea fileEditor;
    private JScrollPane scrollPaneFileEditor;
    private JPanel centerPanel, southPanel;
    private JButton saveButton;
    private JPanel northPanel;

    private JTextField v;
    private JTextField b;



    CenterPanelOfMainPage(){
        this.setLayout(new BorderLayout());
        initialCenterPanel();
        this.add(centerPanel, BorderLayout.CENTER);
        initSouthPanel();
        this.add(southPanel, BorderLayout.SOUTH);
        initNorthPanel();
        this.add(northPanel,BorderLayout.NORTH);
    }


    private void initNorthPanel(){
        northPanel = new JPanel(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(200, 60));

        v=new JTextField("enter v");
        v.setPreferredSize(new Dimension(400, 30));
        northPanel.add(v,BorderLayout.WEST);
        b=new JTextField("enter b");
        b.setPreferredSize(new Dimension(400, 30));
        northPanel.add(b,BorderLayout.EAST);
    }

    private void initialCenterPanel(){
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
        fileEditor = new JTextArea();
        scrollPaneFileEditor = new JScrollPane(fileEditor);
        scrollPaneFileEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFileEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(scrollPaneFileEditor, BorderLayout.CENTER);
    }

    private void initSouthPanel(){
        southPanel = new JPanel(new BorderLayout());
        saveButton = new JButton("save");
        saveButton.addActionListener(this);
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setEnabled(false);
        southPanel.add(saveButton, BorderLayout.CENTER);
    }


    public int getV(){
        int res=0;
        try {
            res= Integer.parseInt(v.getText());
        }catch (Exception e){
            Program.getInstance().mainPage.showErrorMessage("Enter integer for v");
        }
        return res;
    }
    public int getB(){
        int res=0;
        try {
            res= Integer.parseInt(b.getText());
        }catch (Exception e){
            Program.getInstance().mainPage.showErrorMessage("Enter integer for b");
        }
        return res;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == saveButton){
            saveButtonAction();
            return;
        }
    }
    public void setFileEditorText(String fileText){
        fileEditor.setText(fileText);
    }
    public String getFileEditorText(){
        return fileEditor.getText();
    }
    private void saveButtonAction(){
        try {
            Files.writeString(Paths.get(Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText()), Program.getInstance().mainPage.centerPanelOfMainPage.getFileEditorText());
        } catch (IOException e) {

        }
    }
    public void enableSaveButton(){
        saveButton.setEnabled(true);
    }

}
