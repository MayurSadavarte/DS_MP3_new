import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.io.*;

public class udpclient {
	DatagramSocket clientSock;
	DatagramPacket clientPacket;
	Vector<String> memberList=null;
	HashMap<String,Vector<String>> myMap=null;
	MapleJuicePayload mypacket=null;
	
	
	private Vector<String> sortMap()
	{
		Vector<String> keys = new Vector<String>(myMap.keySet());
		final HashMap<String, Vector<String>> temp_myMap = myMap;
		//List<String> tempKeys = (List<String>)keys;
		//List<String> tempKeys = new ArrayList<String>(keys);
		
		Collections.sort(keys, new Comparator<String>(){
					public int compare(String firstkey, String secondkey){
						//String firstkey = (String)first;
						//String secondkey = (String)second;
						Vector<String> firstValue = temp_myMap.get(firstkey);
						Vector<String> secondValue = temp_myMap.get(secondkey);
						int firstLength = firstValue.size();
						int secondLength = secondValue.size();
						
						//if(firstLength > secondLength)
						//	return 1;
						//else
						//	return 0;
						return (firstLength - secondLength);
					}
				});

		
		return keys;  //TODO need to verify that sorting on tempkeys actually affects also keys
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String servip = args[0];
		System.out.print(servip);
		InetAddress ipAddr=null;
		try {
			ipAddr = InetAddress.getByName(servip);
		} catch(UnknownHostException e) {
			System.out.println(e);
		}
		String sendMsg=null;
		sendMsg = "Just a test String!!";
		udpclient udpClientInst = new udpclient();
		udpClientInst.memberList = new Vector<String>();
		udpClientInst.memberList.add("mayur");
		udpClientInst.memberList.add("varun");
		udpClientInst.memberList.add("prerana");
		
		byte[] sendbytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
	    	ObjectOutputStream oos = new ObjectOutputStream(baos);
	    	oos.writeObject(sendMsg);
	    	oos.flush();
	    	//TODO - need to decide whether we need to send length also in first packet and then actual packet
	    	// get the byte array of the object
	    	//byte[] Buf= baos.toByteArray();
	    	//sendMsg = baos.toString();
	    	sendbytes = baos.toByteArray();
	    	System.out.println("the actual string sent is" + sendMsg);
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	    
		//byte[] sendData = sendMsg.getBytes();
		udpClientInst.clientPacket = new DatagramPacket(sendbytes, sendbytes.length, ipAddr, 8000);
		try {
			udpClientInst.clientSock = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println(e);
		}
		try {
			udpClientInst.clientSock.send(udpClientInst.clientPacket);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
		//packet test code
		MapleAction mypayload = new MapleAction();
		mypayload.mapleTaskId = 77;
		mypayload.machineId = 66;
		byte[] payload = mypayload.getByteArray();
		MapleJuicePayload mypacket = new MapleJuicePayload(1, 2, payload);
		
		try {
	    	ObjectOutputStream oos = new ObjectOutputStream(baos);
	    	oos.writeObject(mypacket);
	    	oos.flush();
	    	//TODO - need to decide whether we need to send length also in first packet and then actual packet
	    	// get the byte array of the object
	    	//byte[] Buf= baos.toByteArray();
	    	//sendMsg = baos.toString();
	    	sendbytes = baos.toByteArray();
	    	System.out.println("the actual string sent is" + sendMsg);
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
		udpClientInst.clientPacket = new DatagramPacket(sendbytes, sendbytes.length, ipAddr, 8000);
		try {
			udpClientInst.clientSock.send(udpClientInst.clientPacket);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
		//packet test code ends
		
		udpClientInst.myMap = new HashMap<String,Vector<String>>();
		Vector<String> myList = new Vector<String> ();
		Vector<String> varunList = new Vector<String> ();
		Vector<String> peruList = new Vector<String> ();
		myList.add("doing ms");
		myList.add("computer science");
		myList.add("not sure abt specialization");
		myList.add("what abt disributed sys");
		myList.add("or operating systems?");
		udpClientInst.myMap.put("mayur", myList);
		varunList.add("my brother");
		varunList.add("working in amazon");
		varunList.add("has done MCA");
		udpClientInst.myMap.put("varun", varunList);
		peruList.add("my fiance");
		peruList.add("working in cummins");
		peruList.add("worked at vw");
		peruList.add("has done btech");
		udpClientInst.myMap.put("prerana", peruList);
		Vector<String> newKeys;
		newKeys = udpClientInst.sortMap();
		
		System.out.println(newKeys);
	}

}
