package bait8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class Server {
    public static void main(String[] args) {
        try {
            //Tạo socket phía server với số hiệu cổng 9876
            DatagramSocket serverSocket = new DatagramSocket(9876);   
            System.out.println("Server da duoc tao!");

            while (true) {
                //tạo biến receiveData để nhận dữ liệu từ gói tin đến 
                byte[] receiveData = new byte[1000];
                //tạo sendData để nhận dữ liệu gửi lên gói tin đi
                byte[] sendData  = new byte[60000];

                //----------------------------------------------------------------------
                //Lấy lựa chọn
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 

                serverSocket.receive(receivePacket);
                String bString = new String(receivePacket.getData(),0,receivePacket.getLength());
                System.out.println("Client: "+bString);

                // //Lấy địa chỉ IP của bên gửi
                InetAddress IPAddress = receivePacket.getAddress();
                // //Lấy số hiệu cổng bên gửi
                int port = receivePacket.getPort(); 
                 
                DatagramPacket sendPacket;
                //---------------------------------------------------------------------
            }
            
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
