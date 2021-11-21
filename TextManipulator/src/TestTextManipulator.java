
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class contains a few methods for testing methods in the TextManipulator
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 *
 * @author Jack O'Keefe
 *
 */
public class TestTextManipulator {

	/**
	 * This is the main method that runs the various tests.
	 *
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// testing the main loop, matchCase, translate, pigLatin, reverse(String),
		// reverse(ArrayList<String>), and manipulate.
		// 4 tests of each method will be run by checking their output. Results of the
		// output will be printed.
		testMatchCase();
		testTranslate();
		testPigLatin();
		testReverseString();
		testReverseArrayList();
		testManipulate();
	}

	/**
	 * This is intended to run some tests on the matchCase method. 1. Tests if the
	 * same word is being output after calling matchCase 2. Tests if correct casing
	 * is applied to output when template length is greater than or equal to the
	 * length of the original text 3. Tests if correct casing is applied to output
	 * up to the length of the template for when the template length is less than
	 * the original 4. Tests if all casing of the output past the length of the
	 * template is lower case
	 */
	private static void testMatchCase() {
		boolean error = false; // initialize error as false
		String original = "testRUN";
		String template = "XxXxXxXxX"; // template is longer than input String
		String changed = TextManipulator.matchCase(template, original); // changed should now be "TeStRuN"
		if (!original.equalsIgnoreCase(changed)) { // if not what's expected (ignoring case)
			System.out.println("	testMatchCase 1: word content has been incorrectly changed");
			error = true; // there is an error
		}
		if (!changed.equals("TeStRuN")) { // if not what's expected
			System.out
					.println("	testMatchCase 2: word casing incorrectly changed (template length >= original length)");
			error = true; // there is an error
		}
		template = "XXXX"; // template is shorter than input string
		changed = TextManipulator.matchCase(template, original); // changed should now be "TESTrun"
		if (!changed.substring(0, 4).equals("TEST")) { // if capitalizing has not been implemented
			System.out
					.println("	testMatchCase 3: word casing incorrectly changed (template length < original length)");
			error = true; // there is an error
		}
		if (!changed.substring(4, 7).equals("run")) { // if rest of word not changed to lower case
			System.out
					.println("	testMatchCase 4: word casing of original past the template not changed to lower case");
			error = true; // there is an error
		}
		if (error) { // error occurred
			System.out.println("testMatchCase: Failed");
		} else { // errorr did not occurr
			System.out.println("testMatchCase: Passed");
		}
		return; // exit method
	}

	/**
	 * This is intended to run some tests on the translate method. 1. Tests if
	 * method returns translated string 2. Tests if method returns translated string
	 * when case does not match 3. Tests if method returns translated string with
	 * same casing as input string 4. Tests if method returns the line character for
	 * every character in the input string
	 * 
	 * @param template
	 * @param original
	 * @return
	 */
	private static void testTranslate() {
		boolean error = false; // initialize error as false
		ArrayList<String[]> wordList = new ArrayList<String[]>(1);
		String[] sampleDict = { "hello", "hola" };
		wordList.add(sampleDict);
		String test = TextManipulator.translate("hello", wordList); // test should now be "hola"
		if (!test.equals("hola")) { // if not what's expected
			System.out.println("	testTranslate 1: fails to translate word");
			error = true; // an error has occurred
		}
		test = TextManipulator.translate("Hello", wordList); // test should now be "Hola"
		if (!test.equalsIgnoreCase("hola")) { // if not what's expected (ignoring case)
			System.out.println("	testTranslate 2: fails to translate word");
			error = true; // an error has occurred
		}
		if (!test.equals("Hola")) { // if not what's expected
			System.out.println("	testTranslate 3: fails to match case of word to translate");
			error = true; // an error has occurred
		}
		test = TextManipulator.translate("Hey", wordList); // test should now be "---"
		if (!test.equals("" + Config.LINE_CHAR + Config.LINE_CHAR + Config.LINE_CHAR)) { // if not what's expected
			System.out.println("	testTranslate 4: fails to change word into line character");
			error = true; // an error has occurred
		}
		if (error) { // if error occurred
			System.out.println("testTranslate: Failed");
		} else { // if error did not occur
			System.out.println("testTranslate: Passed");
		}
		return; // exit method
	}

	/**
	 * This runs some tests on the pigLatin method. 1. Tests if method returns the
	 * pig latin translation to the input string when input string begins with a
	 * consonant 2. Tests if method returns the pig latin translation with the
	 * correct casing of the input string 3. Tests if method returns the pig latin
	 * translation to the input string when input string begins with a vowel 4.
	 * Tests if method treats an apostrophe as a consonant
	 */
	private static void testPigLatin() {
		boolean error = false; // initialize error as false
		String test = TextManipulator.pigLatin("Scram"); // test should now be "Amscray"
		if (!test.equalsIgnoreCase("amscray")) { // if not what's expected (ignoring case)
			System.out.println(
					"	testPigLatin 1: failed to translate input string into pig latin (starting with consonant)");
			error = true; // an error has occurred
		}
		if (!test.equals("Amscray")) { // if not what's expected
			System.out.println("	testPigLatin 2: failed to return string with same casing as input string");
			error = true; // an error has occurred
		}
		test = TextManipulator.pigLatin("apple"); // test should now be "appleway"
		if (!test.equals("appleway")) { // if not what's expected
			System.out
					.println("	testPigLatin 3: failed to translate input string into pig latin (starting with vowel)");
			error = true; // an error has occurred
		}
		test = TextManipulator.pigLatin("R'cardo"); // test should now be "ardor'cay"
		if (!test.equalsIgnoreCase("Ardor'cay")) { // if not what's expected
			System.out.println("	testPigLatin 4: failed to treat apostrophe as consonant");
			error = true; // an error has occurred
		}
		if (error) { // if an error occurred
			System.out.println("testPigLatin: Failed");
		} else { // if error did not occur
			System.out.println("testPigLatin: Passed");
		}
	}

	/**
	 * This runs some tests on the reverse (string) method. 1. Tests if method
	 * returns the input string reversed 2. Tests if method returns the input string
	 * reversed with same casing as input string 3. Tests if method can handle empty
	 * input string 4. Tests if method can handle new line input string
	 */
	private static void testReverseString() {
		boolean error = false; // initialize error as false
		String test = TextManipulator.reverse("Abc"); // test should now be "Cba"
		if (!test.equalsIgnoreCase("cba")) { // if not what's expected (ignoring case)
			System.out.println("	testReverse (String) 1: failed to reverse input string");
			error = true; // an error has occurred
		}
		if (!test.equals("Cba")) { // if not what's expected
			System.out.println("	testReverse (String) 2: failed to match case of input string");
			error = true; // an error has occurred
		}
		test = TextManipulator.reverse(""); // test should now be ""
		if (!test.equals("")) { // if not what's expected
			System.out.println("	testReverse (String) 3: failed to handle empty string");
			error = true; // an error has occurred
		}
		test = TextManipulator.reverse("\n"); // test should now be "\n"
		if (!test.equals("\n")) { // if not what's expected
			System.out.println("	testReverse (String) 4: failed to handle new line input");
			error = true; // an error has occurred
		}
		if (error) { // if an error occurred
			System.out.println("testReverse (String): Failed");
		} else { // if error did not occur
			System.out.println("testReverse (String): Passed");
		}
	}

	/**
	 * This runs some tests on the reverse (array) method. 1. Tests if method
	 * returns an array list containing all the elements in the input array list, in
	 * reverse order, when input array list size is even 2. Tests if method returns
	 * an array list containing all the elements in the input array list, in reverse
	 * order, when input array list size is odd 3. Tests if method returns an array
	 * list containing all the elements in the input array list, in reverse order,
	 * when input array list size is 1 4. Tests if method returns empty array list
	 * when empty array list is input
	 * 
	 */
	private static void testReverseArrayList() {
		boolean error = false;
		boolean tempError = false;
		ArrayList<String> test1 = new ArrayList<String>();
		ArrayList<String> test2 = new ArrayList<String>();
		ArrayList<String> test3 = new ArrayList<String>();
		ArrayList<String> test4 = new ArrayList<String>();
		ArrayList<String> arrL = new ArrayList<String>();
		arrL.add("Hello");
		arrL.add(",");
		arrL.add("World");
		ArrayList<String> revArrL = new ArrayList<String>();
		revArrL.add("World");
		revArrL.add(",");
		revArrL.add("Hello");
		test1 = TextManipulator.reverse(arrL); // test1 should now be the same as revArrL
		for (int i = 0; i < test1.size(); ++i) { // test for each string in array
			if (!(test1.get(i).equals(revArrL.get(i)))) { // if a string does not match up with what it should be
				tempError = true; // something went wrong
			}
		}
		if (tempError) { // if something went wrong
			System.out.println("	testReverse (Array) 1: failed to reverse input array (odd length)");
			error = true; // an error occurred
		}
		tempError = false;
		arrL.remove(1);
		revArrL.remove(1);
		test2 = TextManipulator.reverse(arrL); // test2 should now be the same as revArrL
		for (int i = 0; i < test2.size(); ++i) { // test for each string in array
			if (!(test2.get(i).equals(revArrL.get(i)))) { // if a string does not match up with what it should be
				tempError = true; // something went wrong
			}
		}
		if (tempError) { // if something went wrong
			System.out.println("	testReverse (Array) 2: failed to reverse input array (even length)");
			error = true; // an error occurred
		}
		tempError = false;
		arrL.remove(1);
		revArrL.remove(0);
		test3 = TextManipulator.reverse(arrL); // test3 should now be the same as revArrL
		for (int i = 0; i < test3.size(); ++i) { // test for each string in array
			if (!(test3.get(i).equals(revArrL.get(i)))) { // if a string does not match up with what it should be
				tempError = true; // something went wrong
			}
		}
		if (tempError) { // if something went wrong
			System.out.println("	testReverse (Array) 3: failed to handle array list of lenth 1");
			error = true; // an error occurred
		}
		if (error) { // if an error occurred
			System.out.println("testReverse (Array): Failed");
		} else { // if an error did not occur
			System.out.println("testReverse (Array): Passed");
		}
		tempError = false;
		arrL.clear();
		revArrL.clear();
		tempError = false;
		test4 = TextManipulator.reverse(arrL); // test4 should be blank
		for (int i = 0; i < test4.size(); ++i) { // test for each subArray
			if (!(test4.get(i).equals(revArrL.get(i)))) { // if a subArray does not match up with what it should be
				tempError = true; // something went wrong
			}
		}
		if (tempError) { // if something went wrong
			System.out.println("	testReverse (Array) 3: failed to handle empty array list");
			error = true; // an error occurred
		}
	}

	/**
	 * This is intended to run some tests on the manipulate method. 1. Tests if
	 * method successfully returns translated array list 2. Tests if method
	 * successfully returns pig-latin-translated array list 3. Tests if method
	 * successfully returns array list where every word is reversed 4. Tests if
	 * method successfully returns array list where every line is reversed
	 */
	private static void testManipulate() {
		boolean error = false; // initializing error as false
		boolean[] modFlags = new boolean[Config.NUM_MODS]; // making of mods matrix
		ArrayList<ArrayList<String>> fileByLine = new ArrayList<ArrayList<String>>();
		fileByLine.add(new ArrayList<String>());
		fileByLine.add(new ArrayList<String>());
		fileByLine.get(0).add("Hello");
		fileByLine.get(0).add(",");
		fileByLine.get(1).add("How");
		fileByLine.get(1).add("are");
		fileByLine.get(1).add("you");
		fileByLine.get(1).add("?"); // make of an input file array
		String[] one = new String[] { "hello", "hola" };
		String[] two = new String[] { "how", "como" };
		String[] three = new String[] { "are", "estas" };
		ArrayList<String[]> dict = new ArrayList<String[]>();
		dict.add(one);
		dict.add(two);
		dict.add(three); // making of dictionary file array
		ArrayList<ArrayList<String>> test1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> test2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> test3 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> test4 = new ArrayList<ArrayList<String>>();
		modFlags[Config.MOD_TRANS] = true; // translate mod is on
		test1 = TextManipulator.manipulate(fileByLine, dict, modFlags); // test1 should now be a translated version of
																		// the original array
		boolean tempError = false; // initializing temp error
		for (int i = 0; i < fileByLine.size(); ++i) { // for the size of array
			for (int j = 0; j < fileByLine.get(i).size(); ++j) { // for size of subArray
				if (!test1.get(i).get(j).equals(TextManipulator.translate(fileByLine.get(i).get(j), dict))) { // if a
																												// string
																												// is
																												// not
																												// what
																												// it
																												// should
																												// be
					tempError = true; // something went wrong
				}
			}
		}
		if (tempError) { // if something went wrong
			System.out.println("	testManipulate 1: failed to translate array when translate mod is true");
			error = true; // an error occurred
		}
		modFlags[Config.MOD_TRANS] = false; // resetting translate
		modFlags[Config.MOD_PIG] = true; // pig latin mod is on
		test2 = TextManipulator.manipulate(fileByLine, dict, modFlags); // test2 should now be pig latin translated
																		// version of original array
		tempError = false;
		for (int x = 0; x < test2.size(); ++x) { // for size of array
			for (int y = 0; y < test2.get(x).size(); ++y) { // for size of subArray
				if (!(test2.get(x).get(y).equals(TextManipulator.pigLatin(fileByLine.get(x).get(y))))) { // if a string
																											// isn't
																											// what it
																											// should be
					tempError = true; // something went wrong
				}
			}
		}
		if (tempError) { // if something went wrong
			System.out.println("	testManipulate 2: failed to pig latin translate array when pig latin mod is true");
			error = true; // an error occurred
		}
		modFlags[Config.MOD_PIG] = false; // resetting pig latin mod
		modFlags[Config.MOD_REV_WORD] = true; // reverse word mod is now on
		test3 = TextManipulator.manipulate(fileByLine, dict, modFlags); // test3 should now be the original array with
																		// all the words reveresed
		tempError = false;
		for (int x = 0; x < fileByLine.size(); ++x) { // for size of array
			for (int y = 0; y < fileByLine.get(x).size(); ++y) { // for size of subArray
				if (!(test3.get(x).get(y).equals(TextManipulator.reverse(fileByLine.get(x).get(y))))) { // if a string
																										// isn't what it
																										// should be
					tempError = true; // something went wrong
				}
			}
		}
		if (tempError) { // if something went wrong
			System.out.println(
					"	testManipulate 3: failed to reverse each word in array when reverse (string) mod is true");
			error = true; // an error occurred
		}
		modFlags[Config.MOD_REV_WORD] = false; // resetting reverse word mod
		modFlags[Config.MOD_REV_LINE] = true; // reverse line mod is now on
		test4 = TextManipulator.manipulate(fileByLine, dict, modFlags); // test 4 should now be the original array with
																		// its lines reversed
		tempError = false;
		for (int i = 0; i < fileByLine.size(); ++i) { // for size of array
			if (!(test4.get(i).equals(TextManipulator.reverse(fileByLine.get(i))))) { // if a subArray isn't what it
																						// should be
				tempError = true; // something went wrong
			}
		}
		if (tempError) { // if something went wrong
			System.out.println(
					"	testManipulate 4: failed to reverse each line in array when reverse (string) mod is true");
			error = true; // an error occurred
		}
		if (error) { // if an error occurred
			System.out.println("testManipulate: Failed");
		} else { // if an error did not occur
			System.out.println("testManipulate: Passed");
		}
	}
}