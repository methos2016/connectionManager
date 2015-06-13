package net.smythtech.connectionManager;

import android.annotation.SuppressLint;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Communicator {

	private HashMap sockets = new HashMap();
	
	private HashMap serverSockets = new HashMap();
	private HashMap serverClients = new HashMap();
	private String clientData = "";
	
	
	public Communicator() {
		
	}

	//Socket
	public void createSocket(String name, String address, int port) throws Exception{
		
		this.sockets.put(name, new Socket(address, port));
	}
	
	public void socketSendData(String name, String data) throws Exception {
		
		Socket socket = (Socket) this.sockets.get(name);
		
		PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);
		outStream.println(data);
			
		outStream.flush();
		
		
	}
	
	public String socketReadData(String name) throws Exception {
		
		Socket socket = (Socket) this.sockets.get(name);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String data = "";
		data = in.readLine();
		
		return data;
		
	}
	
	public void closeSocket(String name) throws Exception {
		
		Socket socket = (Socket) this.sockets.get(name);
		
		try {
			socket.close();
		} catch(IOException e) {
			//Could not close socket
		} catch(NullPointerException npe) {
			//Socket has not been created
		}
		
		this.sockets.remove(name);
	}
	
	//Server socket
	public void createServerSocket(String name, int port) throws Exception {
		
		this.serverSockets.put(name, new ServerSocket(port));
		this.serverClients.put(name, new HashMap());
		
	}
	
	public String serverSocketListen(String serverName) {
		
		ServerSocket serverSocket = (ServerSocket) this.serverSockets.get(serverName);
		
		try {
			
			Socket serverClient = serverSocket.accept();
			((HashMap) this.serverClients.get(serverName)).put(serverClient.getInetAddress().toString(), serverClient);
			return serverClient.getInetAddress().toString();
			
		} catch (IOException e) {
			return "";
		}
	}
	
	public void serverSocketDropClient(String serverName, String clientName) throws Exception{
		
		HashMap clients = (HashMap) this.serverClients.get(serverName);
		Socket client = (Socket) clients.get(clientName);
		
		client.close();
		
		clients.remove(clientName);
		
	}
	
	public String serverSocketReadData(String serverName, String clientName) throws Exception {
		
		HashMap clients = (HashMap) this.serverClients.get(serverName);
		Socket client = (Socket) clients.get(clientName);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		String data = "";
		data = in.readLine();
		return data;
	}
	
	public void serverSocketSendData(String serverName, String clientName, String data) throws IOException {
		
		HashMap clients = (HashMap) this.serverClients.get(serverName);
		Socket client = (Socket) clients.get(clientName);
		
		System.out.println("SENDING DATA");
		
		PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
		outStream.println(data);
			
		outStream.flush();
		
		System.out.println("DATA SENT");
		
	}
	
	public void closeServerSocket(String serverName) throws Exception{
		ServerSocket serverSocket = (ServerSocket) this.serverSockets.get(serverName);
		try {
			serverSocket.close();
		} catch(IOException e) {
			//Socket has not been created
		} catch(NullPointerException npe) {
			//Socket has not been created
		}
		
		this.serverSockets.remove(serverName);
		this.serverClients.remove(serverName);
	}
	
	
	//HTTP
	public String sendHTTPGet(String request) throws Exception{
		
		String result = "";
			  
	    URL url = new URL(request);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-Type", "text/plain"); 
	    conn.setRequestProperty("charset", "utf-8");
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    
	    String line = "";
	    
	    while ((line = rd.readLine()) != null) {
	       result += line;
	    }
	    
	    rd.close();
	    
	    return result;
	}
	
	@SuppressLint("NewApi")
	public String sendHTTPPost(String request, String parameters) throws Exception{
		
		String result = "";
		
		byte[] postData = parameters.getBytes(Charset.forName( "UTF-8" ));
		int dataLength = postData.length;
		URL url = new URL(request);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setDoOutput( true );
	    conn.setDoInput ( true );
	    conn.setInstanceFollowRedirects( false );
	    conn.setRequestMethod( "POST" );
	    conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
	    conn.setRequestProperty( "charset", "utf-8");
	    conn.setRequestProperty( "Content-Length", Integer.toString(dataLength ));
	    conn.setUseCaches( false );
	    
	    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	    dos.write( postData );
	    
	    
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    
	    String line = "";
	    
	    while ((line = rd.readLine()) != null) {
	       result += line;
	    }
	    
	    rd.close();
		
		return result;
		
	}
	
	//Getters and Setters
	public Socket getSocket(String name) {
		Socket socket = (Socket) sockets.get(name);
		return socket;
	}

	public ServerSocket getServerSocket(String name) {
		ServerSocket serverSocket = (ServerSocket) this.serverSockets.get(name);
		return serverSocket;
	}
	
}
