package FileIO;

import java.io.*;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class FileIO {

    public void fileReader(String fileName){
        File file = new File(fileName);
        FileReader fr=null;
        char[] ch = new char[100];
        try {
            fr= new FileReader(file);
            fr.read(ch);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fr!=null){
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("************");
        }
        System.out.println(ch);


    }

    public void fileWriter(String fileName,String str) throws IOException {
        FileWriter fw=new FileWriter(fileName,true);
        fw.write(str);
        fw.close();
    }
    public void bufferReader(String fileName) throws IOException {
        BufferedReader bf0 = new BufferedReader(new FileReader(new File(fileName)));
        String str = null;
        while ((str = bf0.readLine()) != null) {
            System.out.println(str);
        }
        bf0.close();

        File file=new File(fileName);
        InputStreamReader isr=new InputStreamReader(new FileInputStream(file));
        BufferedReader bf1 = new BufferedReader(isr);
        String str1 = null;
        while ((str1 = bf1.readLine()) != null) {
            System.out.println(str1);
        }
        bf1.close();
    }
    public void bufferWriter(String fileName,String str) throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter(fileName));
        bw.append(str);
        bw.close();
    }
    public void fileInputStream(String fileName) throws IOException {
        File file=new File(fileName);
        byte[] b=new byte[100];
        FileInputStream fis=null;
        try {
            fis=new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fis.read(b);
        String str=new String(b);
        System.out.println(str);
        fis.close();

    }

    public void fileOutPutStream(String fileName,String str) throws IOException {
        FileOutputStream fos=new FileOutputStream(new File(fileName));
        byte[] b=str.getBytes();
        fos.write(b);


    }
    public void inputStreamReader(String fileName) throws IOException {
        File file=new File(fileName);

        InputStreamReader isr=new InputStreamReader(new FileInputStream(file));
        char[] ch=new char[100];
        isr.read(ch);
        System.out.println(ch);
        isr.close();

    }
    public void outputStreamWriter(String fileName,String str) throws IOException {
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(fileName),"GBK");
        osw.append(str);
        osw.close();
    }
    public static void main(String[] args) throws IOException {

        FileIO io = new FileIO();
        String fileName="E:\\test.txt";
        String str="孔艳莉";
        //io.fileInputStream(fileName);
        io.fileReader(fileName);
       // io.bufferReader(fileName);
       // io.inputStreamReader(fileName);
       // io.bufferReader(fileName);
        //io.fileWriter(fileName,str); //乱码
       // io.outputStreamWriter(fileName,str);
        //io.fileOutPutStream(fileName,str);
        io.bufferWriter(fileName,str);



    }
}
