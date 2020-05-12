package javascripts;

public class PalindromeCheck {

	public static void main(String[] args) {
		
		
		// Reversing the string and looping across it 
		String original = "malayalam"; 
		String reverse = ""; 
		
		for(int i = original.length()-1; i >= 0; i--) { 
			reverse = reverse + original.charAt(i); 
			
		}
		
		// Checking the conditions here  
		if (reverse.equals(original)) { 
			System.out.println("Given text is a Palindrome."); 
		} else { 
			System.out.println("Not a Palindrome.");
		}
		
		
		
		// Using String buffer to mutate the String 
		String str = "Example"; 
		StringBuffer stb = new StringBuffer(str); 
		
		// Reversing the String using StringBuffer 
		// Reverse method used here will return StringBuffer so we are converting this to string again 
		String rev = stb.reverse().toString(); 
		
		// Comparing both the strings and using Ternary operator for results 
		System.out.println(str.equals(rev)?"\nGiven text is a Palindrome" : "\nNot a Palindrome");
		
		
	}

}
