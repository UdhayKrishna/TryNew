
public class PatternPrint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int rows = 8;

		for (int i = 1; i <= rows; i++) {

			for (int k = i; k >= 1; k--) // creates left half.

			System.out.print(k + " ");

			for (int j = 1; j <= (rows - i) * 2; j++) // create initial spacing.
				System.out.print(" ");

			for (int j = 1; j <= (rows - i) * 2; j++) // create initial spacing.
				System.out.print(" ");

			for (int k = 1; k <= i; k++)// creates right half.
				System.out.print(" " + k);

			System.out.println();
		}

	}

}
