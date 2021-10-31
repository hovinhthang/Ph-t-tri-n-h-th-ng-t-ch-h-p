package bai7;

import java.net.*;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPFileServer {
    private static final int PORT = 9000;
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            byte []inputByte = new byte[60000];
            while(true){
                //Nhận tên file từ client 
                DatagramPacket inputPack = new DatagramPacket(inputByte, inputByte.length);
                socket.receive(inputPack);
                String inputStr =  new String(inputPack.getData(), 0, inputPack.getLength());
                String fileName = inputStr.substring(5);
                //Dọc file và đưa vào mảng byet
                File file = new File(fileName);
                int fileLength = (int)file.length();
                byte []outputByte = new byte[fileLength];
                FileInputStream fis = new FileInputStream(file);
                fis.read(outputByte);
                //Gửi dữ liệu cho client
                DatagramPacket outputPack = new DatagramPacket(outputByte, outputByte.length, inputPack.getAddress(), inputPack.getPort());
                socket.send(outputPack);
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
