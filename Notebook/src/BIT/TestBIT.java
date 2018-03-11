package BIT;

public class TestBIT {
	public static void main(String[] args) {
		long[] a = new long[] {1,0,2,1,1,3,0,4,2,5,2,2,3,1,0,2};
		
		BIT bit = new BIT(a);
		
		System.out.println(bit.read(14));
	}
}
