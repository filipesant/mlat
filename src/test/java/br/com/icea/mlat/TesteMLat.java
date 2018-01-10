package br.com.icea.mlat;

import java.util.ArrayList;
import java.util.List;

public class TesteMLat {
	
	public static void main(String[] args) {
		
		List<Track> trackList = new ArrayList<Track>();		
		List<Double> timeList = new ArrayList<Double>();
		
		Track track1 = new Track();
		track1.setX(1); 
		track1.setY(1);
		track1.setZ(1);
		
		Track track2 = new Track();
		track2.setX(10); 
		track2.setY(10);
		track2.setZ(1);
		
		Track track3 = new Track();
		track3.setX(1); 
		track3.setY(10);
		track3.setZ(1);
		
		trackList.add(track1);
		trackList.add(track2);
		trackList.add(track3);
		
		timeList.add(7.0);
		timeList.add(3.0);
		timeList.add(3.0);
				
		Multilateration mlat = new Multilateration(trackList, timeList);
		System.out.println(mlat.estimatedPosition());
		
//		System.out.println();
		
	}

}
