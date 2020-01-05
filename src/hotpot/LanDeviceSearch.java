package hotpot;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

public class LanDeviceSearch {
	private DatagramSocket socket;
	private int port;
	private boolean startScanDevice;
	private Thread receiveThread;
	private String serverAddress = null;
	
	public LanDeviceSearch(int port) throws Exception
	{
		socket = new DatagramSocket(port);
        socket.setBroadcast(true);
        socket.setSoTimeout(1000);
		this.port = port;
	}
	
	protected void finalize() throws Throwable {
		startScanDevice = false;
		socket.close();
	    super.finalize();
	}
	
    public void BroadcastMessage(byte[] message)
    {
        try
        {
            DatagramPacket packet;
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface networkInterface = en.nextElement();
                List<InterfaceAddress> list = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress:
                        list)
                {
                    if (interfaceAddress.getAddress().isLoopbackAddress())
                        continue;   //Ignore loopback ip
                    if (interfaceAddress.getBroadcast() == null)
                        continue;   //Ignore No Broadcast IP
                    packet = new DatagramPacket(message, message.length, interfaceAddress.getBroadcast(), port);
                    socket.send(packet);
                }
            }
        }catch (Exception e)
        {
        }
    }
    
    public void autoBroadcast(byte[] message, Integer interval)
    {
    	new Thread(() ->  {
    		while(true)
    		{
        		try
        		{
        			BroadcastMessage(message);
        			Thread.sleep(interval);
        		}catch (Exception e)
        		{
        			e.printStackTrace();
        			return;
        		}
    		}
    	}).start();
    }
    
    public void startReceiveUdp()
    {
    	if (startScanDevice)
    		return;
    	startScanDevice = true;
    	receiveThread = new Thread(() -> 
    	{
    		while (startScanDevice)
    		{
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                    String hostIP = packet.getSocketAddress().toString();	//komal-desktop/127.0.1.1:1313
                    if (hostIP.indexOf("/") >= 0)
                    	hostIP = hostIP.substring(hostIP.indexOf("/") + 1);
                    if (hostIP.indexOf(":") >= 0)
                        hostIP = hostIP.substring(0, hostIP.indexOf(":"));
                    serverAddress = hostIP;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
    		}
    	});
    	receiveThread.start();
    }
    
    public void stopReceiveUdp()
    {
    	startScanDevice = false;
    }
    
    public boolean isReceiveUdp()
    {
    	if (serverAddress == null)
    		return false;
    	else
    		return true;
    }
    
    public String popServerIP()
    {
    	String result = serverAddress;
    	serverAddress = null;
    	return result;
    }

}
