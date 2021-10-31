package bai7;

import java.net.*;
import java.io.*;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.sound.sampled.Port;

public class UDPFileClient {
    private static final int PORT = 9000;
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serveAddress = InetAddress.getByName("127.0.01");
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhap ten file: ");
            String fileName = sc.nextLine();
            //Gửi yêu cầu 
            String request = "READ " +fileName;
            byte []outputByte = request.getBytes();
            DatagramPacket outputPack = new DatagramPacket(outputByte, outputByte.length, serveAddress, PORT);
            socket.send(outputPack);

            //Nhận yêu cầu và lưu ra file
            File file = new File("result");
            FileOutputStream fos = new FileOutputStream(file);
            byte []inputByte = new byte[60000];
            DatagramPacket inputPack = new DatagramPacket(inputByte, inputByte.length);
            socket.receive(inputPack);
            fos.write(inputPack.getData(), 0, inputPack.getLength());

            System.out.println("Luu file thanh cong");
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
