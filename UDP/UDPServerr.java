import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.nio.ByteBuffer;

import javax.xml.crypto.Data; 

public class UDPServerr {
    private static final int PORT = 13;
    public static void main(String[] args) 
    {
        try
        {
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("SERVER DA~ MO -----:");
            
            while(true)
            {
                byte[] iputB = new byte[6000];
                //nhan
                DatagramPacket iputP = new DatagramPacket(iputB,iputB.length);
                socket.receive(iputP);

                byte[] request = iputP.getData();
                int chon = convertByteArrayToInt(request);
                    switch(chon)
                    {
                        case 1:
                        {
                            long millis=System.currentTimeMillis();
                            java.sql.Date date=new java.sql.Date(millis);
                            String oputstr = date.toString();

                            byte[] oputB = oputstr.getBytes();
                            DatagramPacket oputP = new DatagramPacket(oputB, oputB.length, iputP.getAddress(),iputP.getPort());
                            socket.send(oputP);
                            break;
                        }
                        case 2:
                        {
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
                            String oputstr = sdf.format(date).toString();
                            byte[] oputB = oputstr.getBytes();
                            DatagramPacket oputP = new DatagramPacket(oputB, oputB.length, iputP.getAddress(),iputP.getPort());
                            socket.send(oputP);
                            break;
                        }
                        case 3:
                        {
                      
                            Date date = new Date();
                            String oputstr = date.toString();

                            byte[] oputB = oputstr.getBytes();
                            DatagramPacket oputP = new DatagramPacket(oputB, oputB.length, iputP.getAddress(),iputP.getPort());
                            socket.send(oputP);

                            break;
                        }
                        case 4:
                        {
                            String oputstr = "END CLIENT";
                            byte[] oputB = oputstr.getBytes();
                            DatagramPacket oputP = new DatagramPacket(oputB, oputB.length, iputP.getAddress(),iputP.getPort());
                            socket.send(oputP);
                            socket.close();
                            break;
        
                        }
                        default:
                            break;
                
                    
                    }
            }
        }catch(IOException ex)
        {
            System.out.println("ERROR SERVER ----: " + ex.toString());
        }
    }
    public static int convertByteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

}
            