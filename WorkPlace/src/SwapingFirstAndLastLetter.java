
public class SwapingFirstAndLastLetter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = "I am Udhaya Kumar working in Cognizant";

		String[] words = s.split(" ");
		StringBuilder result = new StringBuilder();

		for (String word : words) {

			if (word.length() > 1) {

				char[] wordChar = word.toCharArray();
				char temp = wordChar[0];
				wordChar[0] = wordChar[wordChar.length - 1];
				wordChar[wordChar.length - 1] = temp;

				result.append(" ").append(String.valueOf(wordChar));

			} else {

				result.append(" ").append(word);

			}

		}

		System.out.println("Result: " + result.toString());

	}

}
