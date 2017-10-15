
public class SwapingVariableWithoutTemp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String one = "ONE";
		String two = "TWO";

		System.out.println("Before Swaping: 1." + one + " 2."  +two); 
		
		one = one + two;
		
		two = one.substring(0, one.length()-two.length());
		one = one.substring(two.length());
		
		
		System.out.println("After Swaping : 1." + one + " 2."  +two);
		
		
	}

}
