public class Palindrome {

	public static void main(String[] args) {
		System.out.println(isPalindrome("abcba"));
		System.out.println(isPalindrome("abba"));
		System.out.println(isPalindrome("aba"));
		System.out.println(isPalindrome("ba") == false);
		System.out.println(isPalindrome(""));
	}


	public static Boolean isPalindrome(String s) {
		int slen = s.length();

		for (int i = 0; i < slen; i++) {
			int slenMinusI = slen - 1 - i;

			if (i > slenMinusI)
				break;

			if (s.charAt(i) != s.charAt(slenMinusI))
				return false;
		}

		return true;
	}
}
