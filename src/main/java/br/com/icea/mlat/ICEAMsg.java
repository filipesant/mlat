package br.com.icea.mlat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class ICEAMsg {

	public final static byte    MSG_RADAR_SEL = 0x0C;
	public final static byte    MSG_RADAR_EXT = 0x0D;
	
	public final static byte    MSG_RADAR_PRI 		= (byte) 0xFE;
	public final static byte 	MSG_RADAR_SSR 		= (byte) 0xFF;
	public final static byte    MSG_RADAR_CNL_PRI 	= (byte) 0xFA;
	public final static byte    MSG_RADAR_CNL_SSR 	= (byte) 0xFB;
	
	private	byte  	btCode;			// código da mensagem
	private	byte  	btMonograma;	// monograma do radar [A - Z]
	private	byte  	btPrcRadar;		// processador radar [0 ou 1]
	private Track   track = null;
	
	public ICEAMsg() {
		
		this.btCode = 0;
		this.btMonograma = 0;
		this.btPrcRadar = 0;
	
	}
	
	/**
	 * @return the btCode
	 */
	public byte getCode() {
		return btCode;
	}
	
	/**
	 * @param btCode the btCode to set
	 */
	public void setCode(byte p_btCode) {
		this.btCode = p_btCode;
	}
	
	/**
	 * @return the btMonograma
	 */
	public byte getMonograma() {
		return this.btMonograma;
	}
	
	/**
	 * @param btMonograma the btMonograma to set
	 */
	public void setMonograma(byte p_btMonograma) {
		this.btMonograma = p_btMonograma;
	}
	
	/**
	 * @return the btPrcRadar
	 */
	public byte getPrcRadar() {
		return this.btPrcRadar;
	}
	
	/**
	 * @param btPrcRadar the btPrcRadar to set
	 */
	public void setPrcRadar(byte p_btPrcRadar) {
		this.btPrcRadar = p_btPrcRadar;
	}
	
	public boolean decode(byte[] p_abtData, int p_iRadarNo) {
		
		int radarNoIndex = 14 + p_iRadarNo - 1;
		int msgLenIndex = 46;
		int msgICEAIndex = 50;
		
		// Verifica se a mensagem é do radarNo
		if (p_abtData[radarNoIndex] != 0x01) 
			return false;
					
		if (MSG_RADAR_SEL != p_abtData[msgLenIndex + 2] &&
			MSG_RADAR_EXT != p_abtData[msgLenIndex + 2]) 
			return false;
				
		// O tamanho da mensagem útil ICEA
		byte btLenMsg = p_abtData[msgLenIndex];
		byte [] abtData = copyBytes(p_abtData, msgICEAIndex, btLenMsg);
		
		//for (int i=0; i < btLenMsg; i++) {
		//	abtData[i] = p_abtData[msgICEAIndex + i];
		//}
		
		
		if (MSG_RADAR_CNL_PRI == p_abtData[msgICEAIndex] ||
			MSG_RADAR_CNL_SSR == p_abtData[msgICEAIndex]) {
			
			// decodifica a mensagem de cancelamento de pista
			return handleCancelTrack(abtData);
			
		}
		
		if (MSG_RADAR_PRI == p_abtData [msgICEAIndex] ||
		    MSG_RADAR_SSR == p_abtData [msgICEAIndex]) {
			
			// decodifica a mesagem de pista primária, secundária ou associada
			return handleTrack(abtData);
			
		}
		
		return false;

	}

	private boolean handleTrack(byte[] p_abtData) {

		int index = 0;
		
		this.setCode(p_abtData[index ++]);
		this.setMonograma(p_abtData[index ++]);
		
		this.track = new Track();

		this.track.setTCord(p_abtData[index ++]);
		
		this.track.setSPI((p_abtData[index] & 0x80) == 0x80);
	    
		byte btPrcRadar = (byte) (((p_abtData[index] & 0x40) == 0x40) ? 2 : 1);
		this.setPrcRadar(btPrcRadar);
		
		this.track.setReal((p_abtData[index] & 0x20) == 0x20);
		this.track.setSimulada((p_abtData[index] & 0x10) == 0x10);
		this.track.setSintese((p_abtData[index] & 0x08) == 0x08);
		this.track.setTeste((p_abtData[index] & 0x04) == 0x04);
		
		index ++;
		
		this.track.setQual(p_abtData[index ++]);
		this.track.setDetc(p_abtData[index ++]);
		
		this.track.setTrkNo(this.getShortVal(this.copyBytes(p_abtData, index, 2)));
		index += 2;
		
		this.track.setTrack((byte)this.getIntVal(this.copyBytes(p_abtData, index, 4)));
		index += 4;
	
		this.track.setAux((byte)this.getIntVal(this.copyBytes(p_abtData, index, 4)));
		index += 4;
		
		this.track.setX(this.getDoubleVal(this.copyBytes(p_abtData, index, 8)));
		index += 8;
		
		this.track.setY(this.getDoubleVal(this.copyBytes(p_abtData, index, 8)));
		index += 8;
		
		this.track.setZ(this.getDoubleVal(this.copyBytes(p_abtData, index, 8)));
		index += 8;

		this.track.setDX(this.getShortVal(this.copyBytes(p_abtData, index, 2)));
		index += 2;
		
		this.track.setDY(this.getShortVal(this.copyBytes(p_abtData, index, 2)));
		index += 2;
		
		this.track.setNivel(this.getShortVal(this.copyBytes(p_abtData, index, 2)));
		index += 2;

		this.track.setVel(this.getShortVal(this.copyBytes(p_abtData, index, 2)));
		index += 2;

		this.track.setProa(this.getDoubleVal(this.copyBytes(p_abtData, index, 8)));
		index += 8;
		
		this.track.setTMode((char)p_abtData[index ++]);
		
		this.track.setSSR(new String(this.copyBytes(p_abtData, index, 5)).split("\0")[0]);
		index += 5;
		
		try {
			this.track.setCSign(new String(this.copyBytes(p_abtData, index, 8)).split("\0")[0]);
		} catch (Exception exception) {
			this.track.setCSign("");
		}
		index += 8;
		
		return true;

	}

	private boolean handleCancelTrack(byte[] p_abtData) {
		
		int index = 0;
		
		this.setCode(p_abtData[index ++]);
		this.setMonograma(p_abtData[index ++]);
		
		this.track = new Track();

		this.track.setTCord(p_abtData[index ++]);
		
		track.setSPI((p_abtData[index] & 0x80) == 0x80);
	    
		byte btPrcRadar = (byte) (((p_abtData[index] & 0x40) == 0x40) ? 2 : 1);
		this.setPrcRadar(btPrcRadar);
		
		this.track.setReal((p_abtData[index] & 0x20) == 0x20);
		this.track.setSimulada((p_abtData[index] & 0x10) == 0x10);
		this.track.setSintese((p_abtData[index] & 0x08) == 0x08);
		this.track.setTeste((p_abtData[index] & 0x04) == 0x04);
		
		index ++;
		
		this.track.setTrkNo(this.getShortVal(copyBytes(p_abtData, index, 2)));
		index += 2;
		
		this.track.setTrack((byte)this.getIntVal(copyBytes(p_abtData, index, 4)));
		index += 4;
		
		return true;
	}

	private double getDoubleVal(byte[] p_abtData) {
		
		ByteBuffer value = ByteBuffer.allocate(8);
		value.order(ByteOrder.LITTLE_ENDIAN);
		value.put(p_abtData);
		
		return value.getDouble(0);
		
	}
	
	private byte[] copyBytes(byte[] p_abtData, int p_iStart, int p_iLen) {
		
		byte[] abtCopy = new byte[p_iLen];
		
		for (int i = 0; i < p_iLen; i++) 
			abtCopy[i] = p_abtData[ p_iStart + i];
		
		return abtCopy;
		
	}
	
	private short getShortVal(byte[] p_abtData) {
	
		ByteBuffer value = ByteBuffer.allocate(2);
		value.order(ByteOrder.LITTLE_ENDIAN);
		value.put(p_abtData);

		return value.getShort(0);
		
	}
	
	private int getIntVal(byte [] p_abtData) {
		
		ByteBuffer value = ByteBuffer.allocate(4);
		value.order(ByteOrder.LITTLE_ENDIAN);
		value.put(p_abtData);
		
		return value.getInt(0);
	}
	
	public Track getTrack() {
		// TODO Auto-generated method stub
		return this.track;
	}
		
}
