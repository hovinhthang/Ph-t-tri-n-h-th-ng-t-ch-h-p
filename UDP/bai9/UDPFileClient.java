package bai9;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPFileClient {
    public static void main(String[] args) {
        try {
            //tao moi socket client
            DatagramSocket ClientSocket = new DatagramSocket();
            System.out.println("Đã tạo client. ");

             //Đếm số lượng dòng trong file
             InputStream inputStream = new FileInputStream("D:/2021-2022/PTHTTH/java/UDP/bai9/int.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             LineNumberReader lnr=new LineNumberReader(inputStreamReader);
 
             int j=0;
             while(lnr.readLine() !=null){
                 j++;
             }

            while (true) {
                System.out.println("Chon chuc nang: \n");
                System.out.println("1.Tong | 2.Tich | 3.Exit");
                System.out.println("Nhap lua chon: ");

                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                String bString = read.readLine();

                byte[] ghi = bString.getBytes();
                //lay lua chon nhap tu ban phim
                DatagramPacket in = new DatagramPacket(ghi, ghi.length,InetAddress.getByName("localhost"),9876);
                ClientSocket.send(in);

                byte[] nhan = new byte[1024];

                // tạo goi nhan du lieu ve
                DatagramPacket os;

                //-------------------------------
                int a=Integer.parseInt(bString);

                    if(a ==3 ){
                        ClientSocket.close();
                        System.exit(0);
                    }

                //-------------------------------------------------------------------------------------
                for (int i = 0; i < j; i++) {
                
                    os = new DatagramPacket(nhan, nhan.length);
                    ClientSocket.receive(os);

                    String aString = new String(os.getData(),0,os.getLength());
                    System.out.println("\nDay so: "+aString);

                    // nhan goi tra ve tu server
                    os = new DatagramPacket(nhan, nhan.length);
                    ClientSocket.receive(os);
                    //khai bao bien de chuyen tu kieu byte sang kieu String
                    String data;
                    // dua du lieu tu mang byte vao bien data, lay tu vi tri so 0.
                    data =new String(os.getData(),0,os.getLength());
                    //in ket qua ra man hinh
                    System.out.println(data);

                }
                
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
