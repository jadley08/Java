public class Rotation {

	public static void main(String[] args) {
		int[] arr0 = {1,2,3,4,5};
		System.out.println(numRotationsSlow(arr0) == 0);
		int[] arr1 = {5,1,2,3,4};
		System.out.println(numRotationsSlow(arr1) == 1);
		int[] arr2 = {4,5,1,2,3};
		System.out.println(numRotationsSlow(arr2) == 2);
		int[] arr3 = {3,4,5,1,2};
		System.out.println(numRotationsSlow(arr3) == 3);
		int[] arr4 = {2,3,4,5,1};
		System.out.println(numRotationsSlow(arr4) == 4);
	}

	public static String arrToString(int[] arr) {
		String res = "{";

		for (int i = 0; i < arr.length; i++) {
			res += arr[i];
			if (i != arr.length - 1) {
				res += ",";
			}
		}

		return res + "}";
	}

	// given an array of sorted ints that were rotated to the right k times -> return k
	public static int numRotationsSlow(int[] rotatedArr) {
		int cnt = 0;
		int rotatedArrLastIdx = rotatedArr.length - 1;

		while (rotatedArr[rotatedArrLastIdx] < rotatedArr[0]) {
			rotatedArr = rotateLeft(rotatedArr);
			cnt++;
		}

		return cnt;
	}

	private static int[] arrCpy(int[] arr) {
		int[] cpy = new int[arr.length];

		for (int i = 0; i < arr.length; i++)
			cpy[i] = arr[i];

		return cpy;
	}

	private static boolean arrayEqual(int[] arr1, int[] arr2) {
		if (arr1.length != arr2.length)
			return false;

		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i])
				return false;
		}

		return true;
	}

	private static int[] rotateLeft(int[] arr) {
		int arrlen = arr.length;
		int tmp = arr[0];

		for (int i = 0; i < arrlen - 1; i++)
			arr[i] = arr[i + 1];

		arr[arrlen - 1] = tmp;

		return arr;
	}
}
