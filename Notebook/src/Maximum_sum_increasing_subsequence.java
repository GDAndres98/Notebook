import java.util.Arrays;
import java.util.Random;

public class Maximum_sum_increasing_subsequence {
	public static void main(String[] args) {
		
		long[] s = new long[1000000];
		
		Random z = new Random(System.currentTimeMillis());
		for(int i = 0; i < s.length; i++) {
			long x = z.nextLong();
			while(x < 0) x = z.nextLong();
			s[i] = x;
		}
		
		long time = System.currentTimeMillis();
		long r = MSIS(s, Arrays.copyOf(s, s.length));
		System.out.println("Time: " + (System.currentTimeMillis() - time));
		System.out.println(r);
	}

	private static long MSIS(long[] s, long[] order) {
		Arrays.sort(order);
		
		int[] rank = new int[s.length];
		for(int i = 0; i < s.length; i++)
			rank[i] = Arrays.binarySearch(order, s[i]);
		
		long[] a = new long[s.length];
		SegmentTree st = new SegmentTree(a);
		
		st.updateValue(rank[0], s[0]);
		a[rank[0]] = s[0];
		long max = -1;
		for(int i = 1; i < a.length; i++) {
			long maxind = st.queryOfRange(0, rank[i] - 1);
			st.updateValue(rank[i], s[i] + maxind);
			a[rank[i]] = s[i] + maxind;
			max = Math.max(s[i] + maxind, max);
		}
		
		return max;
	}
	
}
class SegmentTree {
	private long[] segmentTree;
	private long[] arr;
	private int n;
	
	public SegmentTree(long[] arr) {
		int x = (int) (Math.ceil(Math.log(arr.length) / Math.log(2)));
        int m = 2 * (int) Math.pow(2, x) - 1;
        n = arr.length;
		segmentTree = new long[m];
		this.arr = arr;
		constructionSegmentTree(0, n-1, 0);
	}

	private void constructionSegmentTree(int i,int j, int p) {
		if(i == j) {
			segmentTree[p] = arr[i];
			return;
		}
		int m = (i + j)/2;
		constructionSegmentTree(i, m, 2*p + 1); 
		constructionSegmentTree(m + 1, j, 2*p + 2);
		segmentTree[p] = Math.max(segmentTree[2*p+1],segmentTree[2*p+2]);
	}
	
	public long queryOfRange(int i, int j) {
		return queryOfRange(i,j,0,n-1,0);
	}

	private long queryOfRange(int i, int j, int k, int l, int p) {
		if(k >= i && l <= j) return segmentTree[p];
		if(j < k || i > l) return 0;
		return Math.max(queryOfRange(i, j, k ,(k+l)/2, p * 2 + 1), queryOfRange(i, j, (k+l)/2 + 1,l, p * 2 + 2));  
	}
	
	
	public void updateValue(int i, long value) {
		update(i, 0, n-1, 0, value);
	}

	private void update(int i, int k, int l, int p, long value) {
		if(k == l) {
			if(k == i)
				segmentTree[p] = value;
			return;
		}
		if(k <= i && i <= l) {
			update(i, k, (k+l)/2, p*2+1, value);
			update(i, (k+l)/2+1, l, p*2+2, value);
			segmentTree[p] = Math.max(segmentTree[p*2 + 1], segmentTree[p*2 + 2]);
			return;
		}
	}
}

