import java.net.*;
import java.util.Vector;
import java.io.*;

import javax.print.DocFlavor.STRING;


public class udpserver implements Runnable {
	public DatagramSocket udpServSock;
	public DatagramPacket servPacket;
	byte[] recvData = new byte[1024];
	String myName;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String recvMsg=null;
		Vector<String> memberList=null;
		udpserver udpServInstance = new udpserver();
		try {
			udpServInstance.myName = InetAddress.getLocalHost().getHostName();
		} catch(UnknownHostException e) {
			System.out.println(e);
		}
		System.out.println(udpServInstance.myName);
		udpServInstance.servPacket = new DatagramPacket(udpServInstance.recvData,udpServInstance.recvData.length);
		try {
			udpServInstance.udpServSock = new DatagramSocket(8000);
		} catch (SocketException e) {
			System.out.println(e);
		}
		try {
			udpServInstance.udpServSock.receive(udpServInstance.servPacket);
		} catch (IOException e) {
			System.out.println(e);
		}
		//recvMsg = udpServInstance.recvData.toString();
		//recvMsg = udpServInstance.servPacket.toString();
		//recvMsg = new String(udpServInstance.servPacket.getData());
		ByteArrayInputStream baos = new ByteArrayInputStream(udpServInstance.recvData);
		
		try {
			ObjectInputStream oos = new ObjectInputStream(baos);
			//memberList = (Vector<String>)oos.readObject();
			recvMsg = (String)oos.readObject();
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		System.out.print(recvMsg);
	}

}
