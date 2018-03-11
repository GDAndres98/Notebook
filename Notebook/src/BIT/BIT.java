package BIT;
//Bynary Indexed Tree
//Fenwick Tree
//Implementation: Marlon Estupinan
public class BIT {
	private long[] tree;
	private int maxVal;
	public BIT(long[] arr) {
		maxVal = arr.length;
		tree = new long[arr.length + 1];
		for(int i = 0; i < tree.length - 1; i++)
			update(i, arr[i]);
	}
	
	public long read(int idx) {
		idx++;
		long sum = 0;
		while(idx > 0) {
			sum += tree[idx];
			idx -= (idx & -idx);
		}
		return sum;
	}
	
	public long readSingle(int idx) {
		idx++;
		long sum = tree[idx];
		if(idx > 0) {
			int z = idx - (idx & -idx);
			idx--;
			while(idx != z) {
				sum -= tree[idx];
				idx -= (idx & -idx);
			}
		}
		return sum;
	}
	
	public void update(int idx, long val) {
		idx++;
		while(idx <= maxVal) {
			tree[idx] += val;
			idx += (idx & -idx);
		}
	}
}
