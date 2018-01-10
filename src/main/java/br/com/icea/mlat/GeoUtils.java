package br.com.icea.mlat;

public class GeoUtils {

	/** ellipsoid flattening */
	private double f = 1. / 298.257223563;

	/** semi-eixo maior em metros */
	private double a = 6378137.;

	/** semi-eixo menor em metros */
	private double b = a * (1. - f);
	//	b = 6356752.31424518

	/** primeira excentricidade */
	private double e = Math.sqrt((Math.pow(a,2) - Math.pow(b,2)) / Math.pow(a,2) );

	/** segunda excentricidade */
	private double e_ = Math.sqrt((Math.pow(a,2) - Math.pow(b,2)) / Math.pow(b,2));

	/**  primeira excentricidade ao quadrado*/
	private double e2 = 0.00669437999013;

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getE() {
		return e;
	}

	public void setE(double e) {
		this.e = e;
	}

	public double getE_() {
		return e_;
	}

	public void setE_(double e_) {
		this.e_ = e_;
	}

	public double getE2() {
		return e2;
	}

	public void setE2(double e2) {
		this.e2 = e2;
	}

	public double[] geoc2ecef(double lat,double lng, double alt) {

		double[] result = new double[3];

		double lat_r = Math.toRadians(lat);
		double lng_r = Math.toRadians(lng);

		double N = a / Math.sqrt(1. - (e2 * Math.sin(lat_r) * Math.sin(lat_r)));

		double x = (N + alt) * Math.cos(lat_r) * Math.cos(lng_r);
		double y = (N + alt) * Math.cos(lat_r) * Math.sin(lng_r);
		double z = (N * (1. - e2) + alt) * Math.sin(lat_r);

		result[0] = x;
		result[1] = y ;
		result[2] = z;

		return result;
	}


}
