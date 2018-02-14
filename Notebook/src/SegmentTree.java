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
		segmentTree[p] = segmentTree[2*p+1] + segmentTree[2*p+2];
	}
	
	public long queryOfRange(int i, int j) {
		return queryOfRange(i,j,0,n-1,0);
	}

	private long queryOfRange(int i, int j, int k, int l, int p) {
		if(k >= i && l <= j) return segmentTree[p];
		if(j < k || i > l) return 0;
		return queryOfRange(i, j, k ,(k+l)/2, p * 2 + 1) + queryOfRange(i, j, (k+l)/2 + 1,l, p * 2 + 2);  
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
			segmentTree[p] = segmentTree[p*2 + 1] + segmentTree[p*2 + 2];
			return;
		}
	}
}