import java.util.HashMap;
import java.util.Map;

public class CountOfWords {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "I love India I love India I love India I love India I love India I love India I love India I love India";

		String[] array = s.split(" ");
		Map<String, Integer> stringMap = new HashMap<String, Integer>();

		for (String word : array) {

			if (stringMap.containsKey(word)) {

				stringMap.put(word, stringMap.get(word) + 1);

			} else {
				stringMap.put(word, 1);
			}
		}

		for (Map.Entry<String, Integer> map : stringMap.entrySet()) {

			System.out.println(map.getKey() + " : " + map.getValue());

		}

	}

}
