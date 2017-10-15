
public class MissingNumberInSequence {

	public static void main(String[] args) {
	
		int[] array = {1,2,3,4,5,6,7,8,9,10,12,13,14,15,16};
		int count = 16;
		int formulaValue = count*(count+1)/2;
		int sum = 0;		
		for(int num : array){			
			sum +=num;
		}
		
		System.out.println("Missing Number is : " + (formulaValue - sum) );

	}

}
