import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
public class Edmond_Karp {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		int[][] mAdy = new  int[n][n];
		ArrayList<Integer>[] lAdy = new ArrayList[n];
		
		for (int i = 0; i < mAdy.length; i++) {
			lAdy[i] = new ArrayList<>();
			Arrays.fill(mAdy[i], Integer.MAX_VALUE);
		}
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			mAdy[a][b] = c;
			mAdy[b][a] = 0;
			
			lAdy[a].add(b);
			lAdy[b].add(a);
		}
		
		int s = Integer.parseInt(in.readLine());
		int t = Integer.parseInt(in.readLine());
		
		int max = maxFlow(lAdy, mAdy, s, t);
		
		System.out.println(max);
	}

	public static int maxFlow(ArrayList<Integer>[] lAdy, int[][] mAdy, int s, int t) {
		int flow = 0;
		int[] pred;
		do {
			Queue<Integer> q = new LinkedList<>();
			q.add(s);

			pred = new int[mAdy.length];
			Arrays.fill(pred, -1);
			pred[s] = s;
			LOOP:
				while(!q.isEmpty()) {
					int cur = q.poll();
					for (int e : lAdy[cur]) {
						if(mAdy[e][cur] == Integer.MAX_VALUE ) continue;
						if(pred[e] == -1 && e != s && (mAdy[cur][e] > 0? mAdy[cur][e] + mAdy[e][cur] > 0: -mAdy[cur][e] > 0)) {
							pred[e] = cur;
							q.add(new Integer(e));

							if(e == t) break LOOP;
						}
					}
				}

			if(pred[t] != -1) {
				int df = Integer.MAX_VALUE;
				for(int l = t, e = pred[l]; l != s; l = e, e = pred[l])
					df = Math.min(df, mAdy[e][l] > 0? mAdy[e][l] + mAdy[l][e]: -mAdy[e][l]);
				for(int l = t, e = pred[l]; l != s; l = e, e = pred[l]) {
					if(mAdy[e][l] > 0) mAdy[l][e] -= df;
					else mAdy[e][l] += df;
				}
				flow -= df;
			}
		}while(pred[t] != -1);
		
		return -flow;
	}
	
	static double edmondsKarp(double[][]capacity, int v1, int v2) {
		int n = capacity.length, lAdy[][] = new int[n][], ants[] = new int[n], queue[] = new int[n],v,u;
		double f = 0d,flow[][] = new double[n][n],m, minCap[] = new double[n]; List h[] = new List[n]; 
		for(u = 0; u < n; u++) h[u] = new ArrayList<Integer>();
		for(u = 0; u < n; u++) for(v = 0; v < n; v++) if(capacity[u][v] > 1e-10) {h[u].add(v); h[v].add(u);}
		for(u = 0; u < n; u++) lAdy[u] = toArr(h[u]);
		for(; (m = bfsEK(capacity, flow, lAdy, ants, minCap, queue, v1, v2)) > 1e-10; f+=m)
			for(v = v2, u = ants[v]; v!=v1 ; v = u, u = ants[v]) {flow[u][v] += m; flow[v][u] -= m;}
		return f;
	}
	
	static double bfsEK(double[][] capacity, double[][] flow, int[][] lAdy,
			int[] ants, double[] minCap, int[] queue, int v1, int v2) {
		int i, t = 0, u; double z; Arrays.fill(ants, -1);ants[v1] = -2; minCap[v1] = Double.POSITIVE_INFINITY;
		for(queue[t++] = v1, i = 0; i < t; i++)
			for(int v: lAdy[u=queue[i]])if((z=capacity[u][v] - flow[u][v])>1e-10 && ants[v] == -1)
			{ants[v] = u; minCap[v] = Math.min(minCap[u], z); if(v == v2) return minCap[v2]; queue[t++] = v;}
		return 0d;
	}
	
	static int[] toArr(List<Integer> p) {int r[] = new int[p.size()], i = 0; for(int x:p) r[i++] = x; return r;}
	
	
	
}

/*
7 11
0 1 3
0 3 3
1 2 4
2 0 3
2 3 1
2 4 2
3 5 6
3 4 2
4 6 1
4 1 1
5 6 9
0
6

5 7
0 1 20
0 2 15
1 2 18
1 4 8
2 4 3
2 3 10
3 4 12
0
4

6 10
0 1 100
0 2 100
1 2 1
1 3 100
1 4 1
2 3 1
2 4 100
3 1 4
3 5 100
4 5 100
0
5

6 9
0 1 10
0 3 10
1 2 4
1 3 2
1 4 8
2 5 10
3 4 9
4 5 10
4 2 6
0
5

6 9
0 1 16
0 2 13
1 3 12
2 1 4
2 4 14
3 5 20
3 2 9
4 3 7
4 5 4
0
5

8 15
0 1 10
0 2 5
0 3 15
1 4 9
1 5 15
1 2 4
2 5 8
2 3 4
3 6 16
4 5 15
4 7 10
5 6 15
5 7 10
6 2 6
6 7 10
0
7




 */
