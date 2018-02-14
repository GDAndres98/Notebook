import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Kruskal {


	ArrayList<int[]> kruskal(ArrayList<Integer>[] lAdy, final double[][] mAdy){
		initSet(lAdy.length);
		
		int c = 0;
		for(ArrayList<Integer> l:lAdy)
			c+=l.size();
		int[][] aristas = new int[c][];
		c=0;
		for (int u = 0; u < lAdy.length; u++) 
			for(int v:lAdy[u])
				aristas[c++]=new int[] {u,v};
		Arrays.sort(aristas,new Comparator<int[]>(){
			@Override
			public int compare(int[] o1,int[] o2) {
				double val1=mAdy[o1[0]][o1[1]],val2=mAdy[o2[0]][o2[1]];
				return val1<val2?-1:(val1>val2?1:0);
			}
		});

		ArrayList<int[]> res=new ArrayList<int[]>();
		for(int i=0; i<aristas.length;i++)
			if(!isSameSet(aristas[i][0], aristas[i][1])) {
				unionSet(aristas[i][0], aristas[i][1]);
				res.add(aristas[i]);
				res.add(new int[] {aristas[i][1], aristas[i][0]});
			}

		return res;

	}
	public static int[]set;
	public static int _numDisjointSets;
	public static void initSet(int size) {
		set = new int[size];
		_numDisjointSets = size;
		for ( int i = 0; i < size; i++)
			set[i]=i;

	}
	public static int findSet(int i) {
		return set[i] == i ? i : findSet(set[i]);
	}
	public static boolean isSameSet(int i, int j) {
		return (findSet(i)== findSet(j));
	}
	public static void unionSet(int i, int j) {
		if(!isSameSet(i, j))
			_numDisjointSets--;
		set[findSet(i)] = findSet(j);
	}




}
