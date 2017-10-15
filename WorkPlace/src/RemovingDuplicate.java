
public class RemovingDuplicate {

	public static void main(String[] args) {

		String[] strArray = { "abc", "def", "mno", "xyz", "pqr", "xyz", "def" };
		String[] uniqueArray = new String[7]; 
		int z=0;

		for (int i = 0; i < strArray.length - 1; i++) {

			for (int j = i + 1; j < strArray.length; j++) {

				if (strArray[i].equalsIgnoreCase(strArray[j]) && (i != j)) {

					System.out.println("Duplicate String is : " + strArray[j]);
				} else {
					
					uniqueArray[z] = strArray[i];
					z++;
				}
			}
		}
		System.out.println(uniqueArray);
		
	}
}
