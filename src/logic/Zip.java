package logic;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Zip {

    private static Zip zip=new Zip();

    public static Zip getInstance(){
        return zip;
    }

    //read file
    public static List<String> readFile(String name){
        List<String> lines=new ArrayList<>();
        Path path = Paths.get(name);

        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    //zip file
    public static void zip(String fileName,boolean pass,String password) {
        System.out.println("zipping");
        //read file
        List<String> file=readFile(fileName);
        List<String> result=new ArrayList<>();

        int v=Program.getInstance().mainPage.centerPanelOfMainPage.getV();
        int b=Program.getInstance().mainPage.centerPanelOfMainPage.getB();

        String first_line="";
        if(pass){
            first_line="pass:"+password;
        }
        else
            first_line="non";
        result.add(first_line);
        result.add(v+"");
        result.add(b+"");


        SlidingWindow slidingWindow=new SlidingWindow(0,0,v,b);
        for (String line:
                file) {
            slidingWindow.newLine();
            result.add(slidingWindow.zip_line(line));
        }

        //create a window

        //zip

        //save zipped
        try {
            String name=fileName;
            if(fileName.endsWith(".txt"))
                name=fileName.substring(0,fileName.length()-4);

            Files.write(Paths.get(name+"-zipped.txt"), result, StandardCharsets.UTF_8);
            System.out.println(Paths.get(name+"-zipped.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Program.getInstance().mainPage.showMsg("zipped successfully");
    }


    public static boolean checkPass(String firstLine,String pass){
        System.out.println("checking"+firstLine+"-"+pass);
        if(firstLine.equals("non"))
            return true;
        if(firstLine.equals("pass:"+pass))
            return true;
        return false;
    }
    //unzip file
    public static void unzip(String fileName,String pass){
        //read file
        List<String> file=readFile(fileName);
        List<String> result=new ArrayList<>();
        String firstline=file.get(0);

        if(!checkPass(firstline,pass)){
            Program.getInstance().mainPage.showErrorMessage("Wrong pass");
            return;
        }
        file.remove(0);

        System.out.println("unzipping");

        int v=Integer.parseInt(file.get(0));
        file.remove(0);
        int b=Integer.parseInt(file.get(0));
        file.remove(0);

        //create sliding window
        SlidingWindow slidingWindow=new SlidingWindow(0,0,v,b);

        //unzip
        for (String line:
                file) {
            slidingWindow.newLine();

            String unzipped=slidingWindow.unzip_line(line);

            result.add(unzipped);

        }

        //save unzip
        try {
            String name=fileName;
            if(fileName.endsWith("-zipped.txt"))
                name=fileName.substring(0,fileName.length()-11);

            Files.write(Paths.get(name+".txt"), result, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Program.getInstance().mainPage.showMsg("unzipped successfully");
    }


    public static void zipFolder(String folderPath,boolean pass,String password,String name) throws IOException {
        File folder = new File(folderPath);
        System.out.println(folder.toString());
        String path=folder.getAbsolutePath();
        System.out.println(path);

        File zipped=new File(path+name+"-zipped");

        List<File> filesInFolder = Files.walk(Paths.get("/path/to/folder"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        for ( File fileEntry : filesInFolder) {
            if (fileEntry.isDirectory()) {
                zipFolder(fileEntry.getPath(),pass,password,fileEntry.getName());
            } else {
                zip(path+name+"-zipped/"+fileEntry.getName()+"-zipped.txt",pass,password);
            }
        }

    }




}
