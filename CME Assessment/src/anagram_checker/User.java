/**
 * 
 */
package anagram_checker;

import java.util.Arrays;

/**
 * 
 */
public class User {

	//instance vars
	private String userName;
	private String text1;
	private String text2;
	private boolean anagram;
	
	//default constructor
	public User() {
		
	}
	


	
	/**
	 * Constructor with args - creates a user with a username
	 * @param username
	 */
	public User (String userName) {
		this.userName = userName;
	}
	
	//getters and setters
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the text1
	 */
	public String getText1() {
		return text1;
	}

	/**
	 * @param text1 the text1 to set
	 */
	public void setText1(String text1) {
		this.text1 = text1;
	}

	/**
	 * @return the text2
	 */
	public String getText2() {
		return text2;
	}

	/**
	 * @param text2 the text2 to set
	 */
	public void setText2(String text2) {
		this.text2 = text2;
	}


	public boolean isAnagram() {
		return anagram;
	}


	/**
	 * compares two words input by the user,
	 * and returns either a true or false depending on whether the words are
	 * anagrams
	 * @param anagram
	 * @param text1 
	 * @param text2
	 */
	public void setAnagram(boolean anagram, String text1, String text2) {
		
	        anagram = false;
			text1.toUpperCase();
			text2.toUpperCase();
			char [] array1 = text1.toCharArray();
			char [] array2 = text2.toCharArray();
			//sort first array
			Arrays.sort(array1);
			Arrays.sort(array2);
			if(Arrays.equals(array1, array2)) {
				anagram = true;
			} else {
				anagram = false;
			}
	        
		this.anagram = anagram;
	}
	
	
	
	
}
