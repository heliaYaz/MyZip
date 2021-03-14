package logic;

import Graphics.MainPage;

public class Program {

    private static Program instance = new Program();
    public MainPage mainPage;

//    private logic.Program(){}


    public static Program getInstance(){
        return instance;
    }

    public void run(){
        mainPage = new MainPage();
    }
}
