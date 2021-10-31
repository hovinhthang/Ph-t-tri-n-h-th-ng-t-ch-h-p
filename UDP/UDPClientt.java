
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class UDPClientt 
{
    private static final int PORT = 13;
    public static void main(String[] args)
    {
        try
        {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            System.out.println("Client DA DUOC TAO -----");
            Scanner sc = new Scanner(System.in);
            while(true)
            {   
                //Gữi dữ liệu qua sv
                System.out.println("\nCHON MENU:... ");
                System.out.println("\n1. HIEN THI DATE:... ");
                System.out.println("\n2.HIEN THI TIME:...");
                System.out.println("\n3.HIEN THI DATE/TIME:...");
                System.out.println("4. Exit");
                int request = sc.nextInt();
                byte[] oputB = convertIntToByteArray(request);
                //
                DatagramPacket oputP = new DatagramPacket(oputB, oputB.length,serverAddress,PORT);
                socket.send(oputP);
                //Nhận dữ liệu từ sv
                byte[] iputB = new byte[60000];

                DatagramPacket iputP = new  DatagramPacket(iputB, iputB.length);
                socket.receive(iputP);

                String iputstr = new String(iputP.getData(),0,iputP.getLength());
                System.out.println("SERVER tra~ ve--: " + iputstr);

            }
        }catch(IOException ex)
        {
            System.out.println("ERROR Client ---: " + ex.toString());
        }
    }
    public static byte[] convertIntToByteArray(int value) {
        return  ByteBuffer.allocate(4).putInt(value).array();
    }
        
}
