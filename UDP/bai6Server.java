import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class bai6Server {
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(6789);
            System.out.println("Server bai 6 da duoc tao");
            while(true){
                byte[] reice = new byte[1024];
                DatagramPacket in = new DatagramPacket(reice, reice.length);
                ds.receive(in);

                String bString = new String(in.getData(),0,in.getLength());
                System.out.println("Client: "+bString);

                byte[] send = new byte[1024];
                DatagramPacket os;
                switch(bString){
                    case "1":
                    //gui yeu cau nhap n den clirnt
                        String yc = "Nhap n:";
                        send=yc.getBytes();
                        os = new DatagramPacket(send, send.length, in.getAddress(), in.getPort());
                        ds.send(os);

                    //nhan n tu client
                        in = new DatagramPacket(reice, reice.length);
                        ds.receive(in);
                        int tong=0;
                        String m=new String(in.getData(),0,in.getLength());

                        int n = Integer.parseInt(m);

                        for (int i = 0; i <=n; i++) {
                            tong += (2*i)+1;
                        }
                        String s= String.valueOf(tong);
                        send=s.getBytes();
                        os = new DatagramPacket(send, send.length, in.getAddress(), in.getPort());
                        ds.send(os);
                        break; 
                        case "2":
                        //gui yeu cau nhap n den clirnt
                        yc = "Nhap n:";
                        send=yc.getBytes();
                        os = new DatagramPacket(send, send.length, in.getAddress(), in.getPort());
                        ds.send(os);

                        //nhan n tu client
                        in = new DatagramPacket(reice, reice.length);
                        ds.receive(in);
                        tong=0;
                        m=new String(in.getData(),0,in.getLength());

                        n = Integer.parseInt(m);

                        for (int i = 0; i <=n; i++) {
                            tong += i*(i+1);
                        }
                        s= String.valueOf(tong);
                        send=s.getBytes();
                        os = new DatagramPacket(send, send.length, in.getAddress(), in.getPort());
                        ds.send(os);
                        break; 
                    
                    case "3":
                        //gui yeu cau nhap n den clirnt
                        yc = "Nhap n:";
                        send=yc.getBytes();
                        os = new DatagramPacket(send, send.length, in.getAddress(), in.getPort());
                        ds.send(os);

                        //nhan n tu client
                        in = new DatagramPacket(reice, reice.length);
                        ds.receive(in);
                        tong=0;
                        m=new String(in.getData(),0,in.getLength());

                        n = Integer.parseInt(m);

                        for (int i = 0; i <=n; i++) {
                            tong += (-2*i)+((2*i)+1);
                        }
                        s= String.valueOf(tong);
                        send=s.getBytes();
                        os = new DatagramPacket(send, send.length, in.getAddress(), in.getPort());
                        ds.send(os);
                        break; 



                    case "0":
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
