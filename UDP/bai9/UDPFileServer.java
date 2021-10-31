package bai9;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPFileServer {
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
                switch (bString) {
                    case "1":
                         //đọc file
                        final String FILE_URL1 = "D:/2021-2022/PTHTTH/java/UDP/bai9/int.txt";
                        // Đọc từng dòng của file và tiến hành cộng tổng lại
                        InputStream inputStream = new FileInputStream(FILE_URL1);
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(inputStreamReader);
                        String line="";
                       //---------------------------------------------------
                        while((line = reader.readLine())  != null){
                            System.out.println("file:"+line);
                            sendData= line.getBytes();
                            sendPacket = 
                                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
                            // //Gửi gói tin đi
                            serverSocket.send(sendPacket);
        
                            int [] Element;
                            String [] item = line.split(" "); //tach chuoi thanh cac phan tu chuoi
                            Element = new int[item.length];
                                int sum1 = 0;
                                for(int i=0; i<item.length; i++){//doi kieu string sang int cua cac phan tu
                                    Element[i] = Integer.parseInt(item[i]);
                                    sum1 += Element[i];
                                //    System.out.println("n: "+n[i]);
                                    
                                }
                            System.out.println("Tổng = " + sum1);
                        
                            String sentence_to_client1 = "Tổng của dãy số nào:"+ sum1;           
                            sendData = sentence_to_client1.getBytes();

                            sendPacket = 
                                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
                            serverSocket.send(sendPacket);

                        }
                    
                        break;
                    case "2":
                
                        // Đọc từng dòng của file và tiến hành cộng tổng lại
                        inputStream = new FileInputStream("D:/2021-2022/PTHTTH/java/UDP/bai9/int.txt");
                        inputStreamReader = new InputStreamReader(inputStream);
                        reader = new BufferedReader(inputStreamReader);
                        String line1="";
                    
                    //---------------------------------------------------
                        while((line1 = reader.readLine())  != null){
                            System.out.println("file:"+line1);
                            sendData= line1.getBytes();
                            sendPacket = 
                                new DatagramPacket(sendData, sendData.length, IPAddress, port);
                            // //Gửi gói tin đi
                            serverSocket.send(sendPacket);
        
                            int [] Element;
                            String [] item = line1.split(" "); //tach chuoi thanh cac phan tu chuoi
                            Element = new int[item.length];
                                int sum2 = 1;
                                for(int i=0; i<item.length; i++){//doi kieu string sang int cua cac phan tu
                                    Element[i] = Integer.parseInt(item[i]);
                                    sum2 *= Element[i];
                                //    System.out.println("n: "+n[i]);
                                    
                                }
                            System.out.println("Tổng = " + sum2);
                        
                            String sentence_to_client1 ="Tích của dãy số nào:"+ sum2;           
                            sendData = sentence_to_client1.getBytes();

                            sendPacket = 
                                new DatagramPacket(sendData, sendData.length, IPAddress, port);
                            serverSocket.send(sendPacket);
        
                        }
                    
                        break;
                    case "3":
                        System.exit(0);
                        break; 

                    default:
                        System.exit(0);
                        break;
                }


            }
            
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
