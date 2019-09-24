import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class Anagram {

    public static void main(String[] args) {
			Scanner in = new Scanner(System.in);
			Anagram anagram = new Anagram('|', ',', '.');
			System.out.println(anagram.createAllAnagrams(in.nextLine()));
    }


		private HashSet<Character> specialChars;

		public Anagram(char ...chars) {
			specialChars = new HashSet<>();
			for (char c : chars) {
				specialChars.add(c);
			}
		}

		private HashMap<Integer,Character> getSpecialCharLocations(String input) {
			HashMap<Integer,Character> mapping = new HashMap<>();
			for (int i = 0; i < input.length(); i++) {
				char currentChar = input.charAt(i);
				if (specialChars.contains(currentChar))
					mapping.put(i, currentChar);
			}
			return mapping;
		}

		private ArrayList<Character> getAllNonSpecialChars(String input) {
			ArrayList<Character> listOfChars = new ArrayList<>();
			for (int i = 0; i < input.length(); i++) {
				char currentChar = input.charAt(i);
				if (!specialChars.contains(currentChar))
					listOfChars.add(currentChar);
			}
			return listOfChars;
		}

		private void createAllAnagramsHelper(String currentString, ArrayList<Character> availableChars, HashMap<Integer,Character> specialCharLocations, ArrayList<String> res) {
			int currentIndex = currentString.length();
			if (specialCharLocations.containsKey(currentIndex)) {
				createAllAnagramsHelper(currentString + specialCharLocations.get(currentIndex), availableChars, specialCharLocations, res);
			}
			else if (availableChars.size() == 0) {
				res.add(currentString);
			}
			else {
				for (int i = 0; i < availableChars.size(); i++) {
					ArrayList<Character> availableCharsMinusI = (ArrayList<Character>)availableChars.clone();
					availableCharsMinusI.remove(i);
					createAllAnagramsHelper(currentString + availableChars.get(i), availableCharsMinusI, specialCharLocations, res);
				}
			}
		}

		public ArrayList<String> createAllAnagrams(String input) {
			ArrayList<String> res = new ArrayList<>();
			HashMap<Integer,Character> specialCharLocations = getSpecialCharLocations(input);
			ArrayList<Character> inputChars = getAllNonSpecialChars(input);

			createAllAnagramsHelper("", inputChars, specialCharLocations, res);
			return res;
		}
}
