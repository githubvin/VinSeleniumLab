package javascripts;

public class CharOccurrence {

	public static void main(String[] args) {
		
		
		// One method is to convert to char array and looping through it 
		String text = "You have no choice other than following me!"; 
		
		char[] ch = text.toCharArray(); 
		
		int count = 0; 
		
		for (int i = 0; i < ch.length; i++) { 
			if (ch[i] == 'o') {
				count++; 
			}
		} 
		
		System.out.println("Number of occurrence of o in the given text: " + count);
		
		// Using Regex [^o] to replace the char and taking the count 
		
		String Str = "You have no choice other than following me!";
		String replacedStr = Str.replaceAll("[^o]", ""); 
		System.out.println("\nNumber of occurrence of o is: " + replacedStr.length());
		
		
		// Another method is we can use HashMap to take the count of the desired character  
		

	}

}
