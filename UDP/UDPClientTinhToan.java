
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClientTinhToan {
    public static void main(String[] args) {
        try {
            DatagramSocket ds=new DatagramSocket();
            System.out.println("Client da duoc tao!");

            while (true) {

                System.out.println("Thuc hien chuc nang: \n");
                System.out.println("\t1. Tổng 1+3+5+7+...+(2n+1)\n");
                System.out.println("\t2. Tổng 1*2 + 2*3+...+n*(n+1)\n");
                System.out.println("\t3. Biểu thức 1-2+3-4+..+(2n+1)\n");
                System.out.println("\t0.Exit\n");
                System.out.println("Nhap lua chon: ");

                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                String bString = read.readLine();

                byte[] ghi = bString.getBytes();
                //lay lua chon nhap tu ban phim
                DatagramPacket in = new DatagramPacket(ghi, ghi.length,InetAddress.getByName("localhost"),9876);

                ds.send(in);

                byte[] nhan = new byte[1024];

                DatagramPacket os = new DatagramPacket(nhan, nhan.length);
                ds.receive(os);
                //lay yeu cau nhap n tu server
                String doi = new String(os.getData(),0,os.getLength());
                System.out.println(doi);

                //gui n den server
                String aString=read.readLine();
                ghi=aString.getBytes();
                in = new DatagramPacket(ghi, ghi.length,InetAddress.getByName("localhost"),9876);
                ds.send(in);
                //nhan kq tu server
                os = new DatagramPacket(nhan, nhan.length);
                ds.receive(os);

                String doc = new String(os.getData(),0,os.getLength());
                int m= Integer.parseInt(doc);
                System.out.println("KQ: "+m);

                if(bString.compareTo("0")==0){
                    System.out.println("Client da dong!");
                    ds.close();
                    
                    
                    break;
                }
     
            }
        
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
