/**
 * 
 */
package anagram_checker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Program which firstly requires the user to enter a suitable username Then
 * provides the user with several options including - create a new user, anagram
 * checker, view saved words, delete saved file, and to quit The main anagram
 * checker allows user to input two different words, and will check if the words
 * are anagrams. The program stores the user's inputs and the result of the
 * anagram checker, as well as the user identifier in the form of a csv file
 * 
 * 
 * Assumptions: The anagrams have to be single words with no special characters
 * 
 * 
 */
public class Main {

	private static Scanner scanner = new Scanner(System.in);
	protected static ArrayList<User> userInfo = new ArrayList<User>();

	// Methods

	/**
	 * Displays menu with different methods which are called to carry out the
	 * displayed actions based on user selection keep running until the user selects
	 * option 5 to quit the program.
	 * 
	 */
	public static void userMenu() {

		int menuSelector;
		menuSelector = 0;
		boolean menuChoiceIsNumber;

		do {
			System.out.println("Enter the number corresponding to your choice from the menu...");

			System.out.println("1. Create new user");
			System.out.println("2. Anagram Checker");
			System.out.println("3. View saved words");
			System.out.println("4. Delete saved file");
			System.out.println("5. Quit");

			do {
				menuChoiceIsNumber = false;
				try {

					menuSelector = scanner.nextInt();
					menuChoiceIsNumber = true;

				} catch (InputMismatchException InputMismatchException) {
					System.err.println("Please enter a number shown on the menu");
					scanner.next();
					menuChoiceIsNumber = false;
				}
			} while (!menuChoiceIsNumber);

			switch (menuSelector) {

			case 1:
				System.out.print("Create new user \n");
				System.out.print("Please input a username \n");
				User user = inputUsername();

				break;

			case 2:

				String text1, text2;

				do {
					System.out.println("Please enter two different words to check if they are anagrams.... \n");
					System.out.println("Please enter the first word....");
					text1 = inputText();

					System.out.println("Please enter the second....");
					text2 = inputText();

					checkWords(text1, text2);
					// saveToUser(user, text1, text2);
					// anagram(text1, text2);
					if (text1.equals(text2)) {
						System.out.println("You entered the same string twice....");
					}

				} while (text1.equals(text2));

				break;

			case 3:

				System.out.println("Load previous data \n");

				if (previousSaveCheck() == false) {
					System.out.println("No previous save file detected");
					break;
				} else {

					readSavedFile();
				}
				break;

			case 4:
				try {
					File f = new File("SavedUser.csv");
					if (f.delete()) {
						System.out.println(f.getName() + " deleted");
					} else {
						System.out.println("failed");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 5:

				System.out.println("Thanks for using the Anagram checker");
				scanner.close();
				break;

			default:
				System.err.println("Please enter a number shown on the menu\n");
			}
		} while (menuSelector != 5);

	}

	/**
	 * checks the new user's username against those of users in ArrayList of users
	 * 
	 * @param nameToBeChecked - String enetered by user
	 * @return the value of boolean flag
	 */
	public static boolean userNameCheckDuplicate(String nameToBeChecked) {
		boolean flag;
		flag = false;

		for (User user : userInfo) {
			if (nameToBeChecked.equalsIgnoreCase(user.getUserName())) {
				flag = true;
				break;
			}

		}

		return flag;
	}

	/**
	 * Creates a user object and allows username to be assigned to user. Validates
	 * the name entered via the userNameCheckDuplicate, checkNum and
	 * checkIfWhitespace and nameCheckLength methods.
	 * 
	 * @return - created user with a unique username that fulfills requirements ( no
	 *         whitespace, no numbers/special characters)
	 * @throws - InputMismatchException if the entered text contains special characters.
	 */
	public static User inputUsername() {

		String userName;
		userName = null;
		// Part 1 entering username
		User user = new User(userName);

		do {

			try {

				userName = scanner.nextLine();

				if (userNameCheckDuplicate(userName) == false && checkNum(userName) == false
						&& checkIfWhitespace(userName) == false && !userName.isBlank()) {
					user.setUserName(userName);
					userInfo.add(user);
					System.out.println(userName + " is a valid username \n");
					break;
				} else if (userNameCheckDuplicate(userName) == true) {
					System.err.println("Duplicate name detected! Please try again.");
				} else if (checkIfWhitespace(userName) == true) {
					System.err.println("Sorry, username contains whitespace! Please enter again.");
				} else if (checkNum(userName) == true) {
					System.err.println("Sorry, username cannot contain numbers");
				}

			} catch (InputMismatchException e) {
				System.err.println("Sorry, you must only use characters!");
			}

		} while (userNameCheckDuplicate(userName) == true || checkIfWhitespace(userName) == true
				|| checkNum(userName) == true || userName.isBlank());
		return user;

	}

	/**
	 * takes entered username as a parameter and checks if contains any numbers if
	 * contains number(s), boolean is flagged true and returned else, boolean
	 * remains false
	 * 
	 * @param userName - entered username
	 * @return - boolean flag
	 */
	public static boolean checkNum(String userName) {
		boolean flag;
		flag = false;

		if (userName.matches(".*\\d.*")) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * takes entered username as a parameter and checks if contains any whitespace
	 * if contains whitespace, boolean is flagged true and returned else, boolean
	 * remains false
	 * 
	 * @param userName
	 * @return boolean indicating if there is any white space in string
	 */
	public static boolean checkIfWhitespace(String userName) {

		boolean flag;
		flag = false;
		for (int loop = 0; loop < userName.length(); loop++) {

			if (userName.charAt(loop) == ' ') {
				flag = true;
				break;
			} else {
				flag = false;
			}

		}

		return flag;

	}

	/**
	 * Checks if entered text contains any whitespace or numbers with checkNum and
	 * checkIfWhitespace methods and returns validated string
	 * @throws InputMismatchException if the entered text contains special characters.)
	 */
	public static String inputText() {
		String text;
		text = null;

		do {

			try {
				text = scanner.nextLine();
				if (checkNum(text) == false && checkIfWhitespace(text) == false && !text.isBlank()) {
					break;
				} else if (checkIfWhitespace(text) == true) {
					System.err.println("Sorry, text contains whitespace! Please enter again.");
				} else if (checkNum(text) == true) {
					System.err.println("Sorry, text cannot contain numbers");
				}

			} catch (InputMismatchException e) {
				System.err.println("Sorry, no special characters");
			}

		} while (checkIfWhitespace(text) == true || checkNum(text) == true || text.isBlank());

		return text;

	}

	/**
	 * Method to compare two strings and check against stored pairs of words in
	 * SavedUser.csv file. If words are found in the file, the result of their
	 * previous comparison is displayed. If the two entered words are the same, the
	 * user is notified and asked to input again. Otherwise, if the words are
	 * different and have not been previously input, the words are saved to user
	 * object with the saveToUser method, and appended to SavedUser.csv file with
	 * saveUserWriteToFile() method
	 * 
	 * @param text1 - first word input by user
	 * @param text2 - second word input by user
	 */
	public static void checkWords(String text1, String text2) {
		for (User user : userInfo) {
			if ((text1.equalsIgnoreCase(user.getText1()) || text2.equalsIgnoreCase(user.getText1()))
					&& (text1.equalsIgnoreCase(user.getText2()) || text2.equalsIgnoreCase(user.getText2()))) {
				System.out.println("Words have been tried before");
				System.out.println("The words " + text1 + " " + text2 + " | anagrams: " + user.isAnagram());
				break;
			} else if (text1.equals(text2)) {
				System.out.println("You entered the same string twice....");
				break;
			} else {

				saveToUser(user, text1, text2);
				System.out.printf("Text1: %10s | Text2: %10s | Anagram:  %10s\n\n", text1, text2, user.isAnagram());
				saveUserWriteToFile();
				break;
			}

		}

	}

	/**
	 * Sets the text1 and text2 instance vars of user as the first and second words
	 * input by the user
	 * 
	 * @param user  - current user
	 * @param text1 - first word input by user
	 * @param text2 - second word input by user
	 */
	public static void saveToUser(User user, String text1, String text2) {
		user.setText1(text1);
		user.setText2(text2);
		user.setAnagram(false, text1, text2);

	}

	/*
	 * Checks if there is a previous 'SavedUSer.csv' file
	 * 
	 * @return the value of the boolean flag
	 */
	public static boolean previousSaveCheck() {

		File file = new File("SavedUser.csv");
		boolean flag = false;

		if (file.exists()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Saves the username, inputted words, and result of anagram checker to a csv file called
	 * 'SavedUSer.csv'
	 * @throws IOException if an I/O error occurs.
	 */
	public static void saveUserWriteToFile() {

		File file = new File("SavedUser.csv");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {

			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (User userToSave : userInfo) {
				bufferedWriter.write(userToSave.getUserName() + "," + userToSave.getText1() + ","
						+ userToSave.getText2() + "," + userToSave.isAnagram());

			}
			bufferedWriter.write("\n");

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads in a previous save file
	 * @throws FileNotFoundException if the file is not found.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void readSavedFile() {
		File file = new File("SavedUser.csv");
		String line;
		String[] previousUserInfo;
		String userName, text1, text2, isAnagram;

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			line = bufferedReader.readLine();

			do {

				previousUserInfo = line.split(",");
				line = bufferedReader.readLine();
				userName = previousUserInfo[0];
				text1 = previousUserInfo[1];
				text2 = previousUserInfo[2];
				isAnagram = previousUserInfo[3];
				Boolean.parseBoolean(isAnagram);

				User user = new User(userName);
				user.setText1(text1);
				user.setText2(text2);
				user.isAnagram();
				userInfo.add(user);

				String lineOut = String.format("Username: %10s | Text1: %10s | Text2: %10s | Anagram:  %10s\n",
						userName, text1, text2, isAnagram);
				System.out.println(lineOut);
			} while (line != null);

			bufferedReader.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the Anagram Checker...");
		System.out.println("Please input a username \n");
		User user = inputUsername();
		userMenu();

	}

}
