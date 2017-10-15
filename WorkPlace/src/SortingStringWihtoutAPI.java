
public class SortingStringWihtoutAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String word = "hgfedcba";
		char[] charArray = word.toCharArray();
		char temp=0;
		for (int i = 0; i < charArray.length; i++) {

			for (int j = 0; j < charArray.length; j++) {
				if(charArray[j]>charArray[i]){				
				temp = charArray[i];
				charArray[i] = charArray[j];
				charArray[j] = temp;
				}
			}

		}

		System.out.println(charArray);
		
	}

}
