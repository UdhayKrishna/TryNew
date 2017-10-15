
public class ReverseNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int num = 1234;
		int rev = 0;
		while (num > 0) {

			int rem = num % 10;
			rev = rem + (rev * 10);
			num = num / 10;
			 
		}

	}

}
