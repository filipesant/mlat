package br.com.icea.mlat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.HashMap;


public class SRBCReceiver implements Runnable {

	public final static int 	DEFAULT_PORT    = 60000;
	public final static int 	MAX_PACKET_SIZE = 2048;
	public final static int 	DEFAULT_RADAR   = 1;
	
	private DatagramSocket 	ds = null;
	private boolean 		bStopped = false;
	private int 			iPort = DEFAULT_PORT;
	private int 			iRadarNo = DEFAULT_RADAR;
	private ICEAMsg			ICEAMsg = new ICEAMsg();
	
	private Map<Integer, Track> trackList = new HashMap<Integer, Track>();
	
	public SRBCReceiver(int p_iPort, int p_iRadarNo) {
		if ( -1 != p_iPort)
			this.iPort = p_iPort;
		
		if ( -1 != p_iRadarNo)
			this.iRadarNo = p_iRadarNo;
	}
	
	public boolean createSocket() {
		
		boolean returnCode = true;
		
		try {

			this.ds = new DatagramSocket(this.iPort);
			this.ds.setBroadcast(true);
			
			System.out.println("SRBCReceiver listening on port: " + this.ds.getLocalPort());
			
			
		} catch (SocketException e) {
			e.printStackTrace();
			this.ds = null;
			returnCode = false;
		}
		
		return returnCode;
	}
	
	public void run() {
		
		if (!createSocket()) {
			System.out.println("Erro na criação do Socket!");
			return;
		}
		
		while (!this.bStopped) {
			try {

				byte[] buffer = new byte[MAX_PACKET_SIZE];
				
				DatagramPacket dp = new DatagramPacket(buffer,MAX_PACKET_SIZE);
		
				// Receive request
				this.ds.receive(dp);
				
				byte[] data = dp.getData();
				
				/*System.out.println("\nPacket received:" +
				"\nFrom host: " + dp.getAddress() +
				"\nHost port: " + dp.getPort() +
				"\nLength: " + dp.getLength() +
				"\nContaining:\n\t" + toHexString(data, dp.getLength()));*/
				
				if (ICEAMsg.decode(data, this.iRadarNo)) {
					
					Track track = ICEAMsg.getTrack();
					
					System.out.println(track.toString());

					// TODO : Barreto aqui !!!!
					if (trackList.containsKey(track.getTrkNoAsInteger())) {
						//Atualiza os dados da pista
						Track oldTrack = trackList.get(track.getTrkNoAsInteger());
						oldTrack.update(track);
						trackList.put(track.getTrkNoAsInteger(), oldTrack);
						
					}
					else {
						// Adiciona o dado da pista
						trackList.put(track.getTrkNoAsInteger(), track);
					}
				};
				
			} catch (Exception e) {

				e.printStackTrace();
				
			}
			
		}
		
	}

	private String toHexString(byte[] data, int len) {
		
		StringBuilder str = new StringBuilder();
		
	
		for (int i = 0; i < len; i++) {
			
			str.append(String.format("%02X ", data[i]));
			
		}
		
		return str.toString();
		
	}
	
	public void finished() {
		
		this.bStopped = true;
		
		if (null != this.ds) {
			this.ds.close();
			this.ds = null;
			System.out.println("Closing datagram socket!");
		}
		
	}

}
