package br.com.icea.mlat;

import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public class Multilateration {

	private List<Track> trackList;
	private List<Double> m_timeList;

	private GeoUtils geoUtils = new GeoUtils();

	public Multilateration(List<Track> trackList, List<Double> timeList) {
		this.trackList = trackList;
		this.m_timeList= timeList;
	}

	public List<Track> getPositionList() {
		return trackList;
	}

	public List<Double> getTimeList() {
		return m_timeList;
	}

	public Track estimatedPosition(){

		double[][] matrixData = new double[4][4];

		int index = 0;

		for (Track track : trackList) {

			double x = track.getX(); 
			double y = track.getY();
			double z = track.getZ();

			double Am = -2 * x ;
			double Bm = -2 * y;
			double Cm = -2 * z;

			double time = m_timeList.get(index);

			double Dm = geoUtils.getA() * geoUtils.getA() + (Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)) - Math.pow(time, 2);

			matrixData[index][0] = Am;
			matrixData[index][1] = Bm;
			matrixData[index][2] = Cm;
			matrixData[index][3] = Dm;

			index++;
		}

		RealMatrix realMatrix = new Array2DRowRealMatrix(matrixData);
		SingularValueDecomposition svd = new SingularValueDecomposition(realMatrix);
		RealMatrix v = svd.getVT();
		
		double[] row = v.getRow(3);
		double[] result = new double[v.getColumnDimension()];
		
		for(int i = 0; i < row.length ; i++) {
			result[i] = row[i]/row[3];			
		}

		Track resultTrack = new Track();
		resultTrack.setX(result[0]);
		resultTrack.setY(result[1]);
		resultTrack.setZ(result[2]);

		return resultTrack;

	}

}
