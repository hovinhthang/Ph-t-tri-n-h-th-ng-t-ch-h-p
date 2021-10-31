import java.io.BufferedWriter;
import java.io.FileFilter;

public class FileTWrite extends Thread{
    String name;
    public FileTWrite(String ten){
        this.name = ten;
    }
    public void nhap() {
        try {
            FileTWrite fw = new FileTWrite(ten);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i <=10; i++){
                int rand = (int) (Math.random()*10);
                bw.write(rand +"\t");

            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();//TODO: handle exception
        }
    }
    public static void main(String[] args) {
        FileTWrite t1 = new FileTWrite("./data.txt");
        t1.start();

        FileTWrite t2 = new FileTWrite("./file1.txt");
        t2.start();

        FileTWrite t3 = new FileTWrite("./data1.txt");
        t3.start();
    }
    
}