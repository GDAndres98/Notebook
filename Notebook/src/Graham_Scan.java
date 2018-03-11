import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;
public class Graham_Scan {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(in.readLine().trim());
		double Q[][] = new double[n][2];
		StringTokenizer s;
		for (int i = 0; i < Q.length; i++) {
			s = new StringTokenizer(in.readLine(), " ");
			Q[i][0] = Integer.parseInt(s.nextToken());
			Q[i][1] = Integer.parseInt(s.nextToken());
		}
		
		double[][] S = convexHull(Q); 
		
		for (int i = 0; i < S.length; i++) {
			System.out.println(Arrays.toString(S[i]));
		}
	}

	private static double[][] convexHull(double[][] q) {
		ArrayList<double[]> P = new ArrayList<>();
		P.add(mostLeft(q));
		ArrayList<double[]> t = new ArrayList<>(Arrays.asList(q));
		t.remove(P.get(0));
		for (int i = 0; !t.isEmpty(); i++) {
			double[] menor = menorPolar(P.get(0), t);
//			if(polar(menor, P.get(0)) == polar(P.get(P.size() - 1), P.get(0)) && i > 0) 
//				P.set(P.size() - 1, mayorDis(menor, P.get(P.size() - 1), P.get(0)));
//			else
				P.add(menor);
			t.remove(menor);
		}
		
		Stack<double[]> S = new Stack<>();
		S.push(P.get(0));
		S.push(P.get(1));
		S.push(P.get(2));
		
		for (int i = 3; i < P.size(); i++) {
			while(cruz(P.get(i), S.elementAt(S.size() - 2), S.peek()) < 0)
				S.pop();
			S.push(P.get(i));
		}
		
		double[][] p = new double[S.size()][];
		for (int i = 0; !S.isEmpty(); i++) p[i] = S.pop();
		return p;
	}

	private static double[] mayorDis(double[] menor, double[] a, double[] o) {
		if(Math.pow(menor[0] - o[0], 2) + Math.pow(menor[1] - o[1], 2) >= Math.pow(a[0] - o[0], 2) + Math.pow(a[1] - o[1], 2))
			return menor;
		return a;
	}

	private static double polar(double[] d, double[] ori) {
		double x = d[0] - ori[0];
		double y = d[1] - ori[1];
		
		double ang = Math.abs(Math.atan(y / x));
		if(x > 0 && y > 0) return ang;
		if(x < 0 && y > 0) return 180 - ang;
		if(x < 0 && y < 0) return 180 + ang;
		if(x > 0 && y < 0) return 360 - ang;
		if(y == 0 && x > 0) return 0;
		if(y == 0 && x < 0) return 180;
		if(y > 0 && x == 0) return 90;
		if(y < 0 && x == 0) return 270;
		return 0;
	}

	private static double[] menorPolar(double[] obj, ArrayList<double[]> t) {
		double menor = polar(t.get(0), obj);
		double[] m = t.get(0);
		for (int i = 1; i < t.size(); i++) {
			double pol = polar(t.get(i), obj);
			if(pol < menor) {
				menor = pol;
				m = t.get(i);
			}
		}
		return m;
	}

	private static double[] mostLeft(double[][] q) {
		double[] menor = q[0];
		for (int i = 1; i < q.length; i++)
			if(q[i][1] <= menor[1])
				if(q[i][1] == menor[1]) menor = q[i][0] < menor[0]? q[i]: menor;
				else menor = q[i];
		return menor;
	}
	
	private static double cruz( double[] a, double[] b, double[] c) {
		return (c[0] - b[0]) * (a[1] - b[1]) - (c[1] - b[1]) * (a[0] - b[0]);
	}
}
