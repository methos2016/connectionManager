package net.smythtech.connectionManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.content.Context;

public class ConnectionManager extends CordovaPlugin {

	public static final String ACTION_TEST = "test";
	public static final String ACTION_CREATE_SOCKET = "createSocket";
	public static final String ACTION_SEND_SOCKET_DATA = "socketSendData";
	public static final String ACTION_READ_SOCKET_DATA = "socketReadData";
	public static final String ACTION_CLOSE_SOCKET = "closeSocket";
	public static final String ACTION_CREATE_SERVER_SOCKET = "createServerSocket";
	public static final String ACTION_SERVER_SOCKET_LISTEN = "serverSocketListen";
	public static final String ACTION_SEND_SERVER_SOCKET_DATA = "serverSocketSendData";
	public static final String ACTION_READ_SERVER_SOCKET_DATA = "serverSocketReadData";
	public static final String ACTION_SERVER_SOCKET_DROP_CLIENT = "serverSocketDropClient";
	public static final String ACTION_CLOSE_SERVER_SOCKET = "closeServerSocket";
	public static final String ACTION_HTTP_GET = "sendHTTPGet";
	public static final String ACTION_HTTP_POST = "sendHTTPPost";
	private Communicator comm = new Communicator();
	
	
	@Override
	public boolean execute(String action, JSONArray JSONargs, CallbackContext callbackContext) throws JSONException {
	
		Context context = cordova.getActivity().getApplicationContext();
		
		try {
			
			JSONObject args = JSONargs.getJSONObject(0);
			
			if(ACTION_CREATE_SOCKET.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	String address = args.getString("address");
		    	int port = (int) args.get("port");
		    	
		    	this.comm.createSocket(name, address, port);
		    	
		    	callbackContext.success();
		    	
		    	return true;
		    	
		    } else if (ACTION_SEND_SOCKET_DATA.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	String dataToSend = args.getString("data");
		    	
		    	this.comm.socketSendData(name, dataToSend);
		    	
		    	callbackContext.success();
		    	
		    	return true;
		    	
		    } else if (ACTION_READ_SOCKET_DATA.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	String dataRead = "";
		    	
		    	dataRead = this.comm.socketReadData(name);
		    	
		    	callbackContext.success(dataRead);
		    	
		    	return true;
		    	
		    } else if (ACTION_CLOSE_SOCKET.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	this.comm.closeSocket(name);
		    	
		    	callbackContext.success();
		    	
		    	return true;
		    	
		    } else if(ACTION_CREATE_SERVER_SOCKET.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	int port = (int) args.get("port");
		    	
		    	this.comm.createServerSocket(name, port);
		    	
		    	callbackContext.success();
		    	
		    	return true;
		    	
		    } else if(ACTION_SERVER_SOCKET_LISTEN.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	
		    	String clientName = this.comm.serverSocketListen(name);
		    	
		    	callbackContext.success(clientName);
		    	
		    	return true;
		    	
		    } else if (ACTION_SEND_SERVER_SOCKET_DATA.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	String clientName = args.getString("clientName");
		    	String dataToSend = args.getString("data");
		    	
		    	this.comm.serverSocketSendData(name, clientName, dataToSend);
		    	
		    	callbackContext.success();
		    	
		    	return true;
		    	
		    } else if (ACTION_READ_SERVER_SOCKET_DATA.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	String clientName = args.getString("clientName");
		    	String dataRead = "";
		    	
		    	dataRead = this.comm.serverSocketReadData(name, clientName);
		    	
		    	callbackContext.success(dataRead);
		    	
		    	return true;
		    	
		    } else if (ACTION_SERVER_SOCKET_DROP_CLIENT.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	String clientName = args.getString("clientName");
		    	
		    	this.comm.serverSocketDropClient(name, clientName);
		    	
		    	callbackContext.success();
		    	
		    	return true;
		    	
		    } else if (ACTION_CLOSE_SERVER_SOCKET.equals(action)) {
		    	
		    	String name = args.getString("name");
		    	this.comm.closeSocket(name);
		    	
		    	callbackContext.success();
		    	
		    	return true;
		    	
		    } else if (ACTION_HTTP_GET.equals(action)) {
		    	
		    	String request = args.getString("request");
		    	String response = "";
		    	
		    	response = this.comm.sendHTTPGet(request);
		    	
		    	callbackContext.success(response);
		    	
		    	return true;
		    	
		    } else if (ACTION_HTTP_POST.equals(action)) {
		    	
		    	String request = args.getString("request");
		    	String params = args.getString("parameters");
		    	String response = "";
		    	
		    	response = this.comm.sendHTTPPost(request, params);
		    	
		    	callbackContext.success(response);
		    	
		    	return true;
		    	
		    } else {

			    callbackContext.error("Invalid action");
			    return false;
		    }

		} catch(Exception e) {
		    System.err.println("Exception: " + e.getMessage());
		    callbackContext.error(e.getMessage());
		    return false;
		} 
	 
	}
	
}
