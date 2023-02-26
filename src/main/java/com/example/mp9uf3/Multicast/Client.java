package com.example.mp9uf3.Multicast;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Locale;

public class Client {

    private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    NetworkInterface netIf;
    InetSocketAddress group;
    int[] cont = {0,0,0,0};
    int max =0;


    public Client(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
    }

    public void runClient() throws IOException{
        DatagramPacket packet;
        byte [] receivedData = new byte[1024];

        socket.joinGroup(group,netIf);
        System.out.printf("Conectado a %s:%d%n",group.getAddress(),group.getPort());

        while(continueRunning){
            packet = new DatagramPacket(receivedData, 1024);
            socket.setSoTimeout(5000);
            try{
                socket.receive(packet);
                continueRunning = getData(packet.getData(),packet.getLength());
            }catch(SocketTimeoutException e){
                System.out.println("Se ha perdido la conexión con el servidor.");
                continueRunning = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socket.leaveGroup(group,netIf);
        socket.close();
    }

    protected  boolean getData(byte[] data, int lenght) {
        boolean ret=true;

        String palabra = new String(data,0,lenght).toLowerCase();
        System.out.println(palabra);

        if (palabra.equals("mesa")){
            cont[0]++;
        }else if (palabra.equals("silla")){
            cont[1]++;
        }else if (palabra.equals("mochila")){
            cont[2]++;
        }else if (palabra.equals(("portatil"))){
            cont[3]++;
        }
        max++;
        if (max == 15){
            System.out.println("mesa: " + cont[0] + "  " + "silla: " + cont[1] + "  " + "mochila: " + cont[2] + "  " + "portatil: " + cont[3]);
            ret = false;
        }

        return ret;
    }

    public static void main(String[] args) throws IOException {
        Client cvel = new Client(5557, "224.0.10.50");
        cvel.runClient();
        System.out.println("Se termino.");

    }
}
