package com.example.myapplication;

import android.util.Log;

public class ClientAgent {
    
    private int sock;
    private static boolean ongoing = true;
    
    static {
	System.loadLibrary("native-lib");
    }

    public interface ClientAgentListener {
	public void onTestingEnded();
    }

    private native int init(String hostname, int port);
    private native void sendData(int sock, String data);
    private native String recvData(int sock);
    private native void disconnect(int sock);
    
    // public void initConnection(String hostname, int port) {
    // 	sock = init(hostname, port);
    // 	// sendData(sock, "Hello World");
    // 	// disconnect(sock);
    // }

    public void startSending(final String hostname,
			     final int port,
			     final int interval,
			     final ClientAgentListener listener) {
	ongoing = true;
	Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    sock = init(hostname, port);
		    sendData(sock, "u");
		    long stime = System.currentTimeMillis();
		    byte[] bytes = new byte[1440];
		    while (ongoing) {
		    	String sdata = new String(bytes);
		    	sendData(sock, sdata);
			if (System.currentTimeMillis() - stime > interval * 1000) {
			    ongoing = false;
			}
		    }
		    listener.onTestingEnded();
		    disconnect(sock);
		}
	    });
	t.start();
    }

    public void startReceiving(final String hostname,
			       final int port,
			       final int interval,
			       final ClientAgentListener listener) {
	ongoing = true;
	Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    sock = init(hostname, port);
		    sendData(sock, "d");
		    long stime = System.currentTimeMillis();		    
		    while (ongoing) {
		    	recvData(sock);
			if (System.currentTimeMillis() - stime > interval * 1000) {
			    ongoing = false;
			}			
		    }
		    listener.onTestingEnded();		    
		    disconnect(sock);
		}
	    });
	t.start();
    }

    public void disconnect() {
	ongoing = false;
    }
}
