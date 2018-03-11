
public class Distancia_Entre_Punto_Segmento {
	public static void main(String[] args) {
		double[] punto1 = new double[2];
		double[] punto2 = new double[2];
		
		double[] punto3 = new double[2];
		
		//segmento
		punto1[0] = 1; 
		punto1[1] = 0;
		
		punto2[0] = 2;
		punto2[1] = 0;
		
		// punto
		punto3[0] = 0;
		punto3[1] = 0;
		
		
		double[] dis = distanciaPuntoSegmento(punto1, punto2, punto3);
		
		System.out.println(dis[0] + "  " + dis[1] + "  " + dis[2]);
	}

	private static double[] distanciaPuntoSegmento(double[] A, double[] B, double[] C) {
		double u = calcParametro(A,B,C);
		if(u <= 1 && u >= 0) {
			double[] P = new double[2];
			P[0]= A[0] + u*(B[0] - A[0]);
			P[1]= A[1] + u*(B[1] - A[1]);
			
			return new double[] {distaciaPuntoPunto(C,P), P[0], P[1]};
		}
		else {
			if(u > 1) return new double[] {distaciaPuntoPunto(B,C), B[0], B[1]};
			else return new double[] {distaciaPuntoPunto(A,C), A[0], A[1]};
		}
	}

	private static double distaciaPuntoPunto(double[] a, double[] b) {
		return Math.sqrt(Math.pow(b[1] - a[1], 2) + Math.pow(b[0] - a[0], 2));
	}

	private static double calcParametro(double[] a, double[] b, double[] c) {
		return ((c[0] - a[0])*(b[0] - a[0]) + (c[1] - a[1])*(b[1] - a[1])) 
				/ (Math.pow(b[0] - a[0],2) + Math.pow(b[1] - a[1], 2));
	}
	
	
}
