
public class ReverseString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = "JackHug";
		char[] sArray = s.toCharArray();

		System.out.print("Reversing through iterate : ");
		for (int i = sArray.length - 1; i >= 0; i--) {

			System.out.print(sArray[i]);
		}
		System.out.println();
		StringBuilder word = new StringBuilder(s);
		System.out.println("Reversing using String Builder reverse method : " + word.reverse());

	}

}
