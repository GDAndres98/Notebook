import java.util.Arrays;
import java.io.*;

public class Criba_De_Eratostenes {
	public static void main(String[] args) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		long time = System.currentTimeMillis();
		int [] a = cribaEratostenes(1000000000);
		System.out.println(System.currentTimeMillis() - time);
		System.out.println(a.length);
		//out.write(Arrays.toString(a));
		out.flush();
	}

	private static int[] cribaEratostenes(int n) {
		boolean [] num = new boolean[n];
		for (int i = 3; i * i < n; i+= 2) {
			if(!num[i]){
				for (int j = 2; i*j < n; j++)
					num[i*j] = true;
			}
		}
		int[] primos = new int[n];
		primos[0] = 2;
		int j = 1;
		for (int i = 3; i < num.length; i+= 2)
			if (!num[i]) primos[j++] = i;
		return Arrays.copyOf(primos, j);
	}
	
	
}


