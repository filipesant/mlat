package br.com.icea.mlat;


public class Track {

	private	byte  	btTCord;		// tipo de coordenada
	private	boolean bSPI;			// flag do pulso especial de identificação SPI
	private	boolean bReal;			// flag que indica se a pista é real
	private	boolean bSimulada;		// flag que indica se a pista é simulada
	private	boolean bSintese;		// flag que indica se é uma pista de síntese 
	private	boolean bTeste;			// flaq que indica se a pista é de teste
	private	byte	btQual;			// qualidade da pista [0 - 7]
	private	byte	btDetc;			// número de falhas de detecções (atualizações perdidas)
	private	short	siTrkNo;		// número da pista
	private byte	btTrack;		// tipo da pista (primária, secundária, associada ou cancelada)
	private byte	btAux;			// 
	private	double 	dX;				// posição X em NM
	private	double 	dY;				// posição Y em NM
	private	double 	dZ;				// posição Z (não utilizado)
	private short  	siDX;			// posição futura no eixo X em s
	private short  	siDY;			// posição futura no eixo Y em s
	private short 	siNivel;		// nível de voo (centenas de ft) 
	private short 	siVel;			// velocidade no solo (kt)
	private double  dProa;			// proa (graus)
	private char  	cTMode;			// modo do código transponder [A]
	private String  sSSR;			// código ssr (octal)
	private String 	sCSign;			// matrícula da aeronave
	
	public Track() {
		
		this.btTCord 	= 0;
		this.bSPI     	= false;
		this.bReal     	= false;
		this.bSimulada 	= true;
		this.bSintese 	= false;
		this.bTeste 	= false;
		this.btQual 	= 0;
		this.btDetc 	= 0;
		this.siTrkNo 	= -1;
		this.btTrack 	= -1;
		this.btAux 		= 0;
		this.dX 		= 0.0;
		this.dY 		= 0.0;
		this.dZ 		= 0.0;
		this.siDX 		= 0;
		this.siDY 		= 0;
		this.siNivel 	= 0;
		this.siVel 		= 0;
		this.dProa 		= 0;
		this.cTMode 	= 'A';
		this.sSSR 		= "";
		this.sCSign 	= "";
		
	}
	
	/**
	 * @return the btTCord
	 */
	public byte getTCord() {
		return btTCord;
	}
	
	/**
	 * @param btTCord the btTCord to set
	 */
	public void setTCord(byte p_btTCord) {
		this.btTCord = p_btTCord;
	}
	
	/**
	 * @return the bSPI
	 */
	public boolean isSPI() {
		return bSPI;
	}
	
	/**
	 * @param bSPI the bSPI to set
	 */
	public void setSPI(boolean p_bSPI) {
		this.bSPI = p_bSPI;
	}
	
	/**
	 * @return the bReal
	 */
	public boolean isReal() {
		return bReal;
	}
	
	/**
	 * @param bReal the bReal to set
	 */
	public void setReal(boolean p_bReal) {
		this.bReal = p_bReal;
	}
	
	/**
	 * @return the bSimulada
	 */
	public boolean isSimulada() {
		return bSimulada;
	}
	
	/**
	 * @param bSimulada the bSimulada to set
	 */
	public void setSimulada(boolean p_bSimulada) {
		this.bSimulada = p_bSimulada;
	}
	
	/**
	 * @return the bSintese
	 */
	public boolean isSintese() {
		return bSintese;
	}
	
	/**
	 * @param bSintese the bSintese to set
	 */
	public void setSintese(boolean p_bSintese) {
		this.bSintese = p_bSintese;
	}
	
	/**
	 * @return the bTeste
	 */
	public boolean isTeste() {
		return bTeste;
	}
	
	/**
	 * @param bTeste the bTeste to set
	 */
	public void setTeste(boolean p_bTeste) {
		this.bTeste = p_bTeste;
	}
	
	/**
	 * @return the btQual
	 */
	public byte getQual() {
		return btQual;
	}
	
	/**
	 * @param btQual the btQual to set
	 */
	public void setQual(byte p_btQual) {
		this.btQual = p_btQual;
	}
	
	/**
	 * @return the btDetc
	 */
	public byte getDetc() {
		return btDetc;
	}
	
	/**
	 * @param btDetc the btDetc to set
	 */
	public void setDetc(byte p_btDetc) {
		this.btDetc = p_btDetc;
	}
	
	/**
	 * @return the siTrkNo
	 */
	public short getTrkNo() {
		return siTrkNo;
	}
	
	/**
	 * @return the siTrkNo as Integer
	 */
	public Integer getTrkNoAsInteger() {
		return new Integer(this.siTrkNo);
	}

	/**
	 * @param siTrkNo the siTrkNo to set
	 */
	public void setTrkNo(short p_siTrkNo) {
		this.siTrkNo = p_siTrkNo;
	}
	
	/**
	 * @return the btTrack
	 */
	public byte getTrack() {
		return btTrack;
	}
	
	/**
	 * @param btTrack the btTrack to set
	 */
	public void setTrack(byte p_btTrack) {
		this.btTrack = p_btTrack;
	}

	/**
	 * @return the btTrack as String 
	 */
	public String getTrackAsString() {
		String sTrack = "NUL";
		
		switch(this.getTrack()) {
			case 0: sTrack = "ASS"; break;
			case 1: sTrack = "CAN"; break;
			case 2: sTrack = "INI"; break;
			case 3: sTrack = "PSR"; break;
			case 4: sTrack = "SSR"; break;
			default: sTrack = "NUL"; break;
		}

		return sTrack;
		
	}
	
	/**
	 * @return the btAux
	 */
	public byte getAux() {
		return this.btAux;
	}
	
	/**
	 * @param btAux the btAux to set
	 */
	public void setAux(byte p_btAux) {
		this.btAux = p_btAux;
	}
	
	/**
	 * @return the btAux as String
	 */
	public String getAuxAsString() {
		
		String sAux = "Indefinido";
		
		switch(this.getAux()) {
			case 0: sAux = "NUL"; break;
			case 1: sAux = "COM"; break;
			case 2: sAux = "EMG"; break;
			case 3: sAux = "HIJ"; break;
			default: sAux = "NUL"; break;
		}
		
		return sAux;
	}
	
	/**
	 * @return the dX
	 */
	public double getX() {
		return this.dX;
	}
	
	/**
	 * @param dX the dX to set
	 */
	public void setX(double p_dX) {
		this.dX = p_dX;
	}
	
	/**
	 * @return the dY
	 */
	public double getY() {
		return this.dY;
	}
	
	/**
	 * @param dY the dY to set
	 */
	public void setY(double p_dY) {
		this.dY = p_dY;
	}
	
	/**
	 * @return the dZ
	 */
	public double getZ() {
		return this.dZ;
	}
	
	/**
	 * @param dZ the dZ to set
	 */
	public void setZ(double p_dZ) {
		this.dZ = p_dZ;
	}
	
	/**
	 * @return the siDX
	 */
	public short getDX() {
		return this.siDX;
	}
	
	/**
	 * @param siDX the siDX to set
	 */
	public void setDX(short p_siDX) {
		this.siDX = p_siDX;
	}
	
	/**
	 * @return the siDY
	 */
	public short getDY() {
		return this.siDY;
	}
	
	/**
	 * @param siDY the siDY to set
	 */
	public void setDY(short p_siDY) {
		this.siDY = p_siDY;
	}
	
	/**
	 * @return the siNivel
	 */
	public short getNivel() {
		return this.siNivel;
	}
	
	/**
	 * @param siNivel the siNivel to set
	 */
	public void setNivel(short p_siNivel) {
		this.siNivel = p_siNivel;
	}
	
	/**
	 * @return the siVel
	 */
	public short getVel() {
		return this.siVel;
	}
	
	/**
	 * @param siVel the siVel to set
	 */
	public void setVel(short p_siVel) {
		this.siVel = p_siVel;
	}
	
	/**
	 * @return the dProa
	 */
	public double getProa() {
		return this.dProa;
	}
	
	/**
	 * @param dProa the dProa to set
	 */
	public void setProa(double p_dProa) {
		this.dProa = p_dProa;
	}
	
	/**
	 * @return the cTMode
	 */
	public char getTMode() {
		return this.cTMode;
	}
	
	/**
	 * @param cTMode the cTMode to set
	 */
	public void setTMode(char p_cTMode) {
		this.cTMode = p_cTMode;
	}
	
	/**
	 * @return the sSSR
	 */
	public String getSSR() {
		return this.sSSR;
	}
	
	/**
	 * @param sSSR the sSSR to set
	 */
	public void setSSR(String p_sSSR) {
		this.sSSR = p_sSSR;
	}
	
	/**
	 * @return the sCSign
	 */
	public String getCSign() {
		return this.sCSign;
	}
	
	/**
	 * @param sCSign the sCSign to set
	 */
	public void setCSign(String p_sCSign) {
		this.sCSign = p_sCSign;
	}
	
	public String toString() {
		
		String track = "Número: " + getTrkNo() + " SSR: " + getSSR() +
				"\nNivel: " + getNivel() +
				"\nVel: " + getVel() + 
				"\nProa : " + getProa();
		
		return track;
	}

	public void update(Track p_Track) {

		this.setTCord(p_Track.getTCord());
		this.setSPI(p_Track.isSPI());
		this.setReal(p_Track.isReal());
		this.setSimulada(p_Track.isSimulada());
		this.setSintese(p_Track.isSintese());
		this.setTeste(p_Track.isTeste());
		this.setQual(p_Track.getQual());
		this.setDetc(p_Track.getDetc());
		this.setTrkNo(p_Track.getTrkNo());
		this.setTrack(p_Track.getTrack());
		this.setAux(p_Track.getAux());
		this.setX(p_Track.getX());
		this.setY(p_Track.getY());
		this.setZ(p_Track.getZ());
		this.setDX(p_Track.getDX());
		this.setDY(p_Track.getDY());
		this.setNivel(p_Track.getNivel());
		this.setVel(p_Track.getVel());
		this.setProa(p_Track.getProa());
		this.setTMode(p_Track.getTMode());
		this.setSSR(p_Track.getSSR());
		this.setCSign(p_Track.getCSign());
		
	}
}
