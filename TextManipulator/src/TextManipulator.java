
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Text Manipulator
// Files:           TextManipulator.java
// Course:          CS 200, Fall 2017
//
// Author:          Jack O'Keefe
// Email:           jokeefe2@wisc.edu
// Lecturer's Name: Jim
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    N/A
// Partner Email:   N/A
// Lecturer's Name: N/A
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do.  If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons:         N/A
// Online Sources:  N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// Manipulates text file in every ludicrous way imaginable!

public class TextManipulator {

	/**
	 * Matches the case of the original string to that of the template as follows.
	 *
	 * 1) If the length of template is the same or longer than the length of the
	 * original, the returned string will match the case of the prefix of the
	 * template up to the length of the original string. For example: template:
	 * XXxxxXxX original: azertYU returns: AZertYu
	 *
	 * 2) If the length of the template is shorter than the length of the original,
	 * the prefix of the returned string up to the length of the template will match
	 * the case of the template. The remaining characters should all be lower case.
	 * For example: template: WxCv original: azertYU returns: AzErtyu
	 *
	 * @param template
	 *            Template of which characters should be which case.
	 * @param original
	 *            String to change the case of, based on the template.
	 * @return A string containing the characters of original with the case matching
	 *         that of template.
	 */
	public static String matchCase(String template, String original) {
		String builder = ""; // starts with empty string
		original = original.toLowerCase(); // now, either the word will be moved to upper case based on template, or
											// will be lower case
		if (template.length() < original.length()) { // if template is shorter than input string
			for (int i = 0; i < template.length(); ++i) { // template is shorter, so only go up to template length
				if (Character.isUpperCase(template.charAt(i))) { // goes through template character by character, checks
																	// if character is upper case
					builder = builder + Character.toUpperCase(original.charAt(i)); // adds upper case character to the
																					// builder string
				} else {
					builder = builder + original.charAt(i); // adds lower case character to the builder string
				}
			}
			for (int i = template.length(); i < original.length(); ++i) { // for characters starting where template
																			// leaves off
				builder = builder + original.charAt(i); // add the rest of the characters
			}
		} else { // if template is same length or greater
			for (int i = 0; i < original.length(); ++i) { // input string is shorter, so only go up to input string
				if (Character.isUpperCase(template.charAt(i))) { // goes through template character by character, checks
																	// if character is upper case
					builder = builder + Character.toUpperCase(original.charAt(i)); // adds upper case character to the
																					// builder string
				} else {
					builder = builder + original.charAt(i); // adds lower case character to the builder string
				}
			}
		}
		original = builder;
		return original; // return correctly cased word
	}

	/**
	 * Translates a word according to the data in wordList then matches the case.
	 * The parameter wordList contains the mappings for the translation. The data is
	 * organized in an ArrayList containing String arrays of length 2. The first
	 * cell (index 0) contains the word in the original language, called the key,
	 * and the second cell (index 1) contains the translation.
	 *
	 * It is assumed that the items in the wordList are sorted in ascending order
	 * according to the keys in the first cell.
	 *
	 * @param word
	 *            The word to translate.
	 * @param wordList
	 *            An ArrayList containing the translation mappings.
	 * @return The mapping in the wordList with the same case as the original. If no
	 *         match is found in wordList, it returns a string of Config.LINE_CHAR
	 *         of the same length as word.
	 */
	public static String translate(String word, ArrayList<String[]> wordList) {
		int a = word.length(); // store word length
		char b = word.charAt(0); // store first character
		if (!Character.isLetter(b)) { // if first character isn't a letter
			return word; // return the input string
		} else {
			for (int i = 0; i < wordList.size(); ++i) { // for the size of the dictionary
				if (word.equalsIgnoreCase(wordList.get(i)[0])) { // if the word matches any of the first column words
					word = matchCase(word, wordList.get(i)[1]); // change the word to its translation and match case
					return word; // return the word
				}
			}
		}
		word = ""; // if return has not occurred yet, the word has not been found. Make a string
					// builder
		for (int i = 0; i < a; ++i) { // for the length of the word
			word = word + Config.LINE_CHAR; // add a dashed line to the string builder
		}
		return word; // return the word
	}

	/**
	 * Converts a string to simplified Pig Latin then matching the case. The rules
	 * for simplified Pig Latin are as follows: 1) If the word begins with a vowel
	 * (a, e, i, o, u, or y), then the string "way" is appended. 2) If the word
	 * begins with a consonant (any letter that is not a vowel as defined above),
	 * then the group of consonants at the beginning of the word are moved to the
	 * end of the string, and then the string "ay" is appended. 3) If the word
	 * begins with any other character, the original string is returned. Note 1: 'y'
	 * is always considered a vowel. Note 2: An apostrophe that is not the first
	 * character of a word is treated as a consonant.
	 *
	 * Some examples: Pig -> Igpay Latin -> Atinlay Scram -> Amscray I'd -> I'dway
	 * you -> youway crypt -> yptcray
	 *
	 * @param word
	 *            The word to covert to simplified Pig Latin.
	 * @return The simplified Pig Latin of the parameter word with matching case.
	 */
	public static String pigLatin(String word) {
		if (word.isEmpty()) { // if input string is empty
			return word; // return the empty string
		} else {
			char a = word.charAt(0); // store the first character
			String original = word; // store the input string
			String builder = ""; // create a string builder for beginning of word
			String ending = ""; // create a string builder for end of word
			String vowels = "aAeEiIoOuUyY"; // store string containing all vowels
			int j = 1; // start counter at 1
			if (Character.isLetter(a) || a == '\'') { // if the first character is a letter of an apostrophe
				switch (a) { // run cases for specific first character
				case 'a':
				case 'A':
				case 'e':
				case 'E':
				case 'i':
				case 'I':
				case 'o':
				case 'O':
				case 'u':
				case 'U':
				case 'y':
				case 'Y': // if the first character is a vowel
					builder = word + "way"; // add "way" to the end of the word
					word = matchCase(original, builder); // match the casing of the input string
					break;
				default: // for all consonants and apostrophes
					ending = ending + a; // begin building the ending with the consonant
					while (vowels.contains(word.substring(j, j + 1)) == false) { // while the next characters in the
																					// string are consonants or
																					// apostrophes
						ending = ending + word.charAt(j); // add next letter to ending
						++j; // increment counter
					}
					for (int i = j; i < word.length(); ++i) { // for the remaining characters
						builder = builder + word.charAt(i); // build string character by character
					}
					builder = builder + ending + "ay"; // finish by ending "ay" at the end
					word = matchCase(original, builder); // match case of the input string
					break;
				}
			}
		}
		return word; // return the word
	}

	/**
	 * Reverses a String, then matches the case. For example: aZErty returns yTReza
	 *
	 * @param word
	 *            The String to reverse.
	 * @return The reverse of word with matching case.
	 */
	public static String reverse(String word) {
		String builder = ""; // create builder string that begins as empty
		for (int i = word.length(); i > 0; --i) { // for the length of the input string
			builder = builder + word.charAt(i - 1); // add character by character to builder string, beginning with the
													// last character
		}
		word = matchCase(word, builder); // match casing of input string
		return word; // return the new string
	}

	/**
	 * Builds a new ArrayList of Strings that contains the items of the ArrayList
	 * passed in, arrL, but in reverse order.
	 *
	 * @param arrL
	 *            The ArrayList of Strings to reverse.
	 * @return A new ArraList of Strings that is the reverse of arrL.
	 */
	public static ArrayList<String> reverse(ArrayList<String> arrL) {
		ArrayList<String> revArrL = new ArrayList<String>(); // create new array list
		for (int i = arrL.size(); i > 0; --i) { // for the length of the input array
			revArrL.add(arrL.get(i - 1)); // add to new array, string by string, beginning with the input array's last
											// string
		}
		return revArrL; // return the new array
	}

	/**
	 * The method that displays the main program menu and reads the user's menu
	 * choice. The full menu has the following format where the (assuming that
	 * Config.MISSING_CHAR is '-'):
	 * 
	 * --------------------------------------------------------------------------------
	 * Text Manipulator Program
	 * --------------------------------------------------------------------------------
	 * Current input file: --- Current output file: --- Current dictionary: ---
	 * Current mode: Interleaved Current mods: TPWL
	 * --------------------------------------------------------------------------------
	 * Main menu: 1) Display Output 2) Save Output Manipulations: T)ranslate P)ig
	 * latin W)ord reverse L)ine reverse Configuration: I)nput file. O)utput file.
	 * D)ictionary file. M)ode Toggle. H)ide/show Menu. Q)uit Enter action:
	 *
	 * Notes: - The lines consist of 80 Config.LINE_CHAR characters. - The input
	 * file, output file and dictionary names are 3 Config.LINE_CHAR characters if
	 * appropriate value in files is null, otherwise display the appropriate value
	 * in files. - The Current mode displays "Interleaved" when modeBoth is true and
	 * "Modified" when false. - The Current mods displays (in the following order)
	 * 'T' if translate is toggled, 'P' if Pig Latin is toggled, 'W' if word reverse
	 * is toggled, and 'L' if line reverse is toggled. - The manipulation and
	 * configuration options are preceded by a single tab. - There is no new line
	 * following the final "Enter action: " prompt.
	 *
	 * @param sc
	 *            The reference to the Scanner object for reading input from the
	 *            user.
	 * @param files
	 *            A string array containing the input file name, the output file
	 *            name, and the dictionary file name in that exact order. The
	 *            entries may be null. The length of the array is Config.NUM_FILES.
	 *            The input file name is at index Config.FILE_IN, the output file
	 *            name is at index Config.FILE_OUT, and the dictionary file name is
	 *            at index Config.FILE_DICT.
	 * @param modFlags
	 *            A boolean array where the values are true if the given
	 *            modification is set to be applied.
	 * @param modeBoth
	 *            True if the display command will alternate lines from the modified
	 *            text and the original text.
	 * @param showMenu
	 *            If true the entire menu is shown, otherwise only the "Enter
	 *            Action: " line is shown.
	 * @return The first character of the line inputed by the user.
	 */
	public static char promptMenu(Scanner sc, String[] files, boolean[] modFlags, boolean modeBoth, boolean showMenu) {
		char action = 0; // initialize action character

		if (showMenu) { // if boolean showMenu is true
			for (int i = 0; i < 80; ++i) { // 80 times total
				System.out.print(Config.LINE_CHAR); // print the line character
			}
			System.out.println("");// print new line

			System.out.println("Text Manipulator Program");

			for (int i = 0; i < 80; ++i) { // 80 times total
				System.out.print(Config.LINE_CHAR); // print the line character
			}
			System.out.println(""); // print new line

			System.out.print("Current input file: ");
			if (files[Config.FILE_IN] == null) { // if input file has not been initialized
				for (int i = 0; i < 3; ++i) {
					System.out.print(Config.LINE_CHAR); // print out 3 line characters
				}
				System.out.println("");
			} else { // if input file has been initialized
				System.out.println(files[Config.FILE_IN]); // print input file name
			}

			System.out.print("Current output file: "); // if output file has not been initialized
			if (files[Config.FILE_OUT] == null) {
				for (int i = 0; i < 3; ++i) {
					System.out.print(Config.LINE_CHAR); // print out 3 line characters
				}
				System.out.println("");
			} else { // if output file has been initialized
				System.out.println(files[Config.FILE_OUT]); // print output file name
			}

			System.out.print("Current dictionary: ");
			if (files[Config.FILE_DICT] == null) { // if dictionary file has not been initialized
				for (int i = 0; i < 3; ++i) {
					System.out.print(Config.LINE_CHAR); // print out 3 line characters
				}
				System.out.println("");
			} else { // if dictionary file has been initialized
				System.out.println(files[Config.FILE_DICT]); // print dictionary file name
			}

			System.out.print("Current mode: ");
			if (modeBoth) { // if modeBoth is true
				System.out.println("Interleaved");
			} else { // if not true
				System.out.println("Modified");
			}

			System.out.print("Current mods: ");
			if (modFlags[Config.MOD_TRANS]) { // if translation is true
				System.out.print("T");
			}
			if (modFlags[Config.MOD_PIG]) { // if pig latin is true
				System.out.print("P");
			}
			if (modFlags[Config.MOD_REV_WORD]) { // if reverse word is true
				System.out.print("W");
			}
			if (modFlags[Config.MOD_REV_LINE]) { // if reverse lien is true
				System.out.print("L");
			}
			System.out.println("");

			for (int i = 0; i < 80; ++i) { // 80 times
				System.out.print(Config.LINE_CHAR); // print line character
			}
			System.out.println("");

			System.out.println("Main menu:");
			System.out.println("1) Display Output");
			System.out.println("2) Save Output");
			System.out.println("Manipulations:");
			System.out.println("	T)ranslate");
			System.out.println("	P)ig latin");
			System.out.println("	W)ord reverse");
			System.out.println("	L)ine reverse");
			System.out.println("Configuration:");
			System.out.println("	I)nput file.");
			System.out.println("	O)utput file.");
			System.out.println("	D)ictionary file.");
			System.out.println("	M)ode Toggle.");
			System.out.println("	H)ide/show Menu.");
			System.out.println("Q)uit");
		}
		boolean entered = false;
		do {
			System.out.print("Enter action: ");
			String input = sc.nextLine(); // take next input
			if (input.isEmpty()) { // if nothing is in input, entered is still false
			} else {
				action = input.charAt(0); // take first character
				entered = true; // line entered, entered is true
			}
		} while (entered == false); // loop while nothing has been entered

		return action; // return the action character
	}

	/**
	 * Prompts the user for a new file name. The prompt should be: "Enter file name
	 * [curFileName]: ", where curFileName is the value from the parameter of the
	 * same name. There should not be a new line following the prompt.
	 *
	 * @param sc
	 *            The reference to the Scanner object for reading input from the
	 *            user.
	 * @param curFileName
	 *            The current file name.
	 * @return The value input by the user excluding any leading or trailing white
	 *         space. If the input is an empty string, then curFileName is returned.
	 */
	public static String updateFileName(Scanner sc, String curFileName) {
		System.out.print("Enter file name [" + curFileName + "]: ");
		String newFileName = sc.nextLine(); // take next input
		if (newFileName == null || newFileName.isEmpty() == true) { // if nothing is entered
			return curFileName; // keep original file name
		} else {
			newFileName = newFileName.trim(); // trim the string
			return newFileName; // return the new file name
		}
	}

	/**
	 * Opens and reads the contents of the dictionary file specified in fileName.
	 * The file is assumed to be a text file encoded in UTF-8. It is assumed that
	 * there is one translation mapping per line. Each line contains a key and its
	 * translation separated by a tab. Note: The dictionary file is assumed to be
	 * sorted by the keys in ascending order.
	 *
	 * For each line in the dictionary file, an entry is added into wordList. The
	 * entry is a String array of length 2, where the value of index 0 is the key
	 * and the value of index 1 is the translation.
	 *
	 * When opening the file, any FileNotFoundException is caught and the error
	 * message "Exception: File 'fileName' not found." followed by a new line is
	 * output, where fileName is the name of the file that the method attempted to
	 * open.
	 *
	 * @param fileName
	 * @param wordList
	 *            Reference to ArrayList to contain the translation mappings.
	 * @throws IOException
	 *             if an I/O error occurs when closing the file.
	 *             FileNotFoundException is caught when opening the file.
	 */
	public static void readDictFile(String fileName, ArrayList<String[]> wordList) throws IOException {
		String text = null; // initialize text
		FileInputStream fileByteStream = null; // initialize file stream
		Scanner inFS = null; // initialize scanner
		try { // try to open file
			fileByteStream = new FileInputStream(fileName);
			inFS = new Scanner(fileByteStream);
		} catch (IOException e) { // catch FileNotFound exception
			System.out.println("Exception: File '" + fileName + "' not found.");
			return; // exit method
		} catch (Exception e) { // catch nullPointer exception
			return; // exit method
		}
		while (inFS.hasNextLine()) { // while scanner isn't empty
			text = inFS.nextLine(); // text is next line
			String[] list = text.split("\\s"); // split string by spaces
			wordList.add(list); // add each word into wordList array
		}
		try { // Done with file, so try to close it
			fileByteStream.close();
		} catch (IOException e) { // catch exception
			inFS.close(); // close scanner
			return; // exit method
		}
		inFS.close(); // close scanner
		return; // exit method
	}

	/**
	 * Opens and reads the contents of the input file specified in fileName. The
	 * input file is read line by line. Each line is split into words and punction
	 * (excluding the apostrophe) and stored in an ArrayList of Strings. These
	 * ArrayLists representing the line are stored in an ArrayList of ArrayLists of
	 * Strings. Specifically, they are put in the ArrayList fileByLine that is
	 * passed in as a parameter.
	 *
	 * For example, a file containing the following: Lorem ipsum dolor sit amet,
	 * consectetur adipiscing elit. Don'ec elementum tortor in mauris consequat
	 * vulputate.
	 *
	 * Would produce an ArrayList of ArrayLists containing 2 ArrayLists of Strings.
	 * The first ArrayList would contain: "Lorem", "ipsum", "dolor", "sit", "amet",
	 * ",", "consectetur", "adipiscing", "elit", ".", "Don'ec", "elementum",
	 * "tortor", "in", "mauris" The second Arraylist would contain: "consequat",
	 * "vulputate", "."
	 *
	 * Note 1: The text file is assumed to be UTF-8. Note 2: There are no assumption
	 * about the length of the file or the length of the lines. Note 3: All single
	 * quotes (') are assumed to be apostrophes.
	 *
	 * When opening the file, any FileNotFoundException is caught and the error
	 * message "Exception: File 'fileName' not found." followed by a new line is
	 * output, where fileName is the name of the file that the method attempted to
	 * open.
	 *
	 * @param fileName
	 *            The name of the input text file to parse.
	 * @param fileByLine
	 *            Reference to ArrayList to contain the contents of the file line by
	 *            line, where each line is an ArrayList of Strings.
	 * @throws IOException
	 *             if an I/O error occurs when closing the file.
	 *             FileNotFoundException is caught when opening the file.
	 */
	public static void readInputFile(String fileName, ArrayList<ArrayList<String>> fileByLine) throws IOException {
		String text = null; // initialize text
		FileInputStream fileByteStream = null; // initialize file stream
		Scanner inFS = null; // initialize scanner
		try { // try to open file
			fileByteStream = new FileInputStream(fileName);
			inFS = new Scanner(fileByteStream);
		} catch (IOException e) { // catch FileNotFound exception
			System.out.println("Exception: File '" + fileName + "' not found.");
			return; // exit method
		} catch (Exception e) { // catch nullPointer exception
			return; // exit method
		}
		int i = 0;
		int a = 0; // initialize variables
		while (inFS.hasNextLine()) { // while scanner isn't empty
			text = inFS.nextLine(); // text is next line of string
			fileByLine.add(new ArrayList<String>()); // add new array list in array list
			String[] words = text.split("\\s"); // split text by spaces
			for (int k = 0; k < words.length; ++k) {
				fileByLine.get(i).add(words[k]); // add words into array
			}
			for (int k = 0; k < fileByLine.get(i).size(); ++k) { // for size of array
				for (int j = 33; j < 48; ++j) { // parse through punctuation
					if (fileByLine.get(i).get(k).indexOf((char) j) > -1 && j != 39) { // if any of the words contains
																						// the specified punctuation
						a = fileByLine.get(i).get(k).indexOf((char) j); // find where it is
						String punctuation = String.valueOf((char) j); // store punctuation as string
						String replace = fileByLine.get(i).get(k).substring(0, a); // trim word of punctuation
						fileByLine.get(i).set(k, replace); // replace original word in array list
						fileByLine.get(i).add(k + 1, punctuation.toString()); // add punctuation after that
						++k; // move up counter
					}
				}
			}
			++i; // move up counter
		}
		try {
			fileByteStream.close(); // done with file, so try to close it
			inFS.close(); // close scanner
		} catch (IOException e) { // catch exception
			inFS.close(); // close scanner
			return; // exit method
		}
		return; // exit method
	}

	/**
	 * Opens and writes (overwrites if the file already exits) the modified contents
	 * of the input file specified contained in modFileByLine to a file called
	 * filename. modFileByLine is an ArrayList containing one ArrayList of String
	 * for each output line.
	 *
	 * When producing the output file, each line should be terminated by a new line.
	 * Each non-punctuation should be Moreover, the spacing around the punctuation
	 * should be as follows: - Excluding, double quotes ("), no space between the
	 * preceding string and the punctuation and a space following. - Double quotes
	 * (") will be treated as pairs: - the first double quote will be preceded by a
	 * space and will not have a space following. - the next double quote will not
	 * be preceded by space and will have a space following.
	 *
	 * If modFileByLine is an ArrayList of ArrayLists contains 2 ArrayLists of
	 * Strings, such that: - The first ArrayList contains: "Lorem", "ipsum", "\"",
	 * "dolor", "sit", "\"", "amet", ",", "consectetur", "adipiscing", "elit", ".",
	 * "Don'ec", "elementum", "tortor", "in", "mauris" - The second Arraylist
	 * contains: "consequat", "vulputate", "."
	 *
	 * The output to the file would be: Lorem ipsum "dolor sit" amet consectetur
	 * adipiscing elit. Don'ec elementum tortor in mauris consequat vulputate.
	 *
	 * Note 1: The output to the file is UTF-8.
	 *
	 * When opening the file, any FileNotFoundException is caught and the error
	 * message "Exception: File 'fileName' not found." followed by a new line is
	 * output, where fileName is the name of the file that the method attempted to
	 * open.
	 *
	 * @param fileName
	 *            The name of the input text file to parse.
	 * @param modFileByLine
	 *            Reference to ArrayList to contain the modified contents of the
	 *            file line by line, where each line is an ArrayList of Strings.
	 * @throws IOException
	 *             if an I/O error occurs when closing the file.
	 *             FileNotFoundException is caught when opening the file.
	 */
	public static void saveToFile(String fileName, ArrayList<ArrayList<String>> modFileByLine) throws IOException {
		boolean first = true; // initialize quotation boolean
		FileOutputStream fileByteStream = null; // initialize file stream
		PrintWriter outFS = null; // initialize file writer
		try { // try to open file
			fileByteStream = new FileOutputStream(fileName);
			outFS = new PrintWriter(fileByteStream);
		} catch (IOException e) { // catch FileNotFound exception
			System.out.println("Exception: File '" + fileName + "' not found.");
			return; // exit method
		} catch (Exception e) { // catch nullPointer exception
			return; // exit method
		}
		for (int i = 0; i < modFileByLine.size(); ++i) { // for size of array
			for (int j = 0; j < modFileByLine.get(i).size(); ++j) { // for size of subArray
				outFS.print(modFileByLine.get(i).get(j)); // print out string
				if (j == modFileByLine.get(i).size() - 1) { // if last string in subArray
					outFS.println("");
				} else if (modFileByLine.get(i).get(j).equals("\"") && first) { // if first quotation mark
					first = false; // boolean is now false
				} else {
					outFS.print(" "); // print a space after string
					if (modFileByLine.get(i).get(j).equals("\"") && !first) { // if second quotation mark
						first = true; // boolean is now true
					}
				}
			}
		}
		outFS.close(); // close scanner
		try { // try to close scanner
			fileByteStream.close();
		} catch (IOException e) { // catch exception
			return; // exit method
		}
		return; // exit method
	}

	/**
	 * Prints out the modified file (and possibly interleaved with the original
	 * file) to the screen.
	 *
	 * If modeBoth is false, then the contents of modFileByLine is output line by
	 * line. Each word is output followed by a space except for the last word. Each
	 * line is terminated with a new line character.
	 *
	 * For the non-interleaved mode, consider the following example: modFileByLine
	 * contains 2 ArrayList of Strings: 1: "Où", "est", "la", "bibliothèque", "?" 2:
	 * "Aucune", "idée", "."
	 *
	 * The output would be: Où est la bibliothèque ? Aucune idée .
	 *
	 * Otherwise, modeBoth is true, then the contents of modFileByLine is
	 * interleaved with fileByline. Lines are printed out in order. First, a line
	 * from modFileByLine is output followed by a new line character followed by the
	 * corresponding line in fileByLine. In order to better match up the
	 * corresponding words in the corresponding lines, the short word is appended
	 * with spaces to the length of the longer word. Between each word adjusted for
	 * length is an additional space.
	 *
	 * For the interleaved mode, consider the following example:
	 * 
	 * modFileByLine contains 2 ArrayList of Strings: 1: "Où", "est", "la",
	 * "bibliothèque", "?" 2: "Aucune", "idée", "." fileByLine contains 1 ArrayList
	 * of Strings: 1: "Where", "is", "the", "library", "?" 2: "No", "idea", "."
	 *
	 * The output would be: Où est la bibliothèque ? Where is the library ? Aucune
	 * idée . No idea .
	 * 
	 * @param fileByLine
	 *            An ArrayList of ArrayList of Strings containing the original
	 *            content.
	 * @param modFileByLine
	 *            An ArrayList of ArrayList of Strings containing the modified
	 *            content.
	 * @param modeBoth
	 *            Flag to indicate if the original file should be interleaved.
	 * @throws Exception
	 *             Throws an Exception with the message "Number of lines in modified
	 *             version differs from original." if the size of fileByLine
	 *             differes from modFileByLine.
	 * @throws Exception
	 *             Throws an Exception with the message "Number of words on line i
	 *             in modified version differs from original.", where i should be
	 *             the 0-based line index where the size of the ArrayList at index i
	 *             in fileByLine differes from the ArrayList at index i in
	 *             modFileByLine.
	 */
	public static void display(ArrayList<ArrayList<String>> fileByLine, ArrayList<ArrayList<String>> modFileByLine,
			boolean modeBoth) throws Exception {
		int i = 0;
		int j;
		int k;
		int l;
		int a; // initialize variables
		if (!modeBoth) { // if boolean is false
			for (i = 0; i < modFileByLine.size(); ++i) { // for size of array
				for (j = 0; j < modFileByLine.get(i).size(); j++) // for size of subArray
					if (j != modFileByLine.get(i).size() - 1) { // as long as not the last string in subArray
						System.out.print(modFileByLine.get(i).get(j)); // print out string
						System.out.print(" "); // print out space
					} else { // last string in subArray
						System.out.print(modFileByLine.get(i).get(j)); // print out string
					}
				System.out.println(""); // print new line at end of each line
			}
		} else { // if boolean is true
			try { // test if arrays are same length
				modFileByLine.get(fileByLine.size() - 1);
				fileByLine.get(modFileByLine.size() - 1);
			} catch (Exception e) { // catch outOfBounds exception
				System.out.println("Number of lines in modified version differs from original.");
				return; // exit method
			}
			try { // try to display
				for (i = 0; i < fileByLine.size(); ++i) { // for size of array
					for (j = 0; j < fileByLine.get(i).size(); ++j) { // for size of subArray
						System.out.print(modFileByLine.get(i).get(j)); // print out modified array's string
						if (modFileByLine.get(i).get(j).length() < fileByLine.get(i).get(j).length()
								&& j != modFileByLine.get(i).size() - 1) { // if string is shorter than original array's
																			// string and not the last string in
																			// subArray
							a = fileByLine.get(i).get(j).length() - modFileByLine.get(i).get(j).length();
							for (k = 0; k < a; ++k) { // for however shorter the string is
								System.out.print(" "); // print a space
							}
						} else {
						}
						if (j == fileByLine.get(i).size() - 1) { // if last string in subArray
							System.out.println("");
							for (l = 0; l < fileByLine.get(i).size(); ++l) { // for size of subArray
								System.out.print(fileByLine.get(i).get(l)); // print original array's string
								if (fileByLine.get(i).get(l).length() < modFileByLine.get(i).get(l).length()
										&& l != fileByLine.get(i).size() - 1) { // if string is shorter than modified
																				// array's string
									a = modFileByLine.get(i).get(l).length() - fileByLine.get(i).get(l).length();
									for (k = 0; k < a; ++k) { // for however shorter string is
										System.out.print(" "); // print a space
									}
								}
								if (l != fileByLine.get(i).size() - 1) { // if not the last string in subArray
									System.out.print(" "); // print a space
								} else { // if last string in subArray
									System.out.println("");
								}
							}
						} else { // if not last string in subArray
							System.out.print(" "); // print a space
						}
					}
					System.out.println("");
				}
			} catch (Exception e) { // catche exception
				System.out
						.println("Number of words on line " + (i + 1) + " in modified version differs from original."); // print
																														// out
																														// where
																														// error
																														// occurred
				return; // exit method
			}
		}
		return; // exit method
	}

	/**
	 * Performs the actions specified by the modFlags to the input file stored in
	 * the ArrayList of ArrayLists of Strings fileByLine. This method stores the
	 * modified string in a new ArrayList of ArrayLists of Strings which it returns.
	 *
	 * There are 4 modifications that may be performed. They are to be process in
	 * the following order if indicated in modFlags: 1 - Translation 2 - To Pig
	 * Latin 3 - Reverse the letters in each word 4 - Reverse the words in each line
	 *
	 * @param fileByLine
	 *            The original file stored as an ArrayList of ArrayLists of Strings.
	 * @param dict
	 *            An ArrayList of String arrays of length two. The ArrayList is
	 *            assumed to be sorted in ascending order accordings to the strings
	 *            at index 0. This will be the second argument for the translate
	 *            method.
	 * @param modFlags
	 *            A boolean area of length Config.NUM_MODS that indicates which
	 *            translation to perform by having a value of true in the
	 *            appropriate cell as follows: Index Modification
	 *            ------------------- --------------------------------
	 *            Config.MOD_TRANS Translation Config.MOD_PIG To Pig Latin
	 *            Config.MOD_REV_WORD Reverse the letters in each word
	 *            Config.MOD_REv_LINE Reverse the words in each line
	 * @return An ArrayList of ArrayLists of Strings, where each internal ArrayList
	 *         is a line which corresponds to the data in fileByLine but with the
	 *         transformations applied in the order specified above.
	 */
	public static ArrayList<ArrayList<String>> manipulate(ArrayList<ArrayList<String>> fileByLine,
			ArrayList<String[]> dict, boolean[] modFlags) {
		if (modFlags[Config.MOD_TRANS]) { // if translation mod is on
			ArrayList<ArrayList<String>> manipulated = new ArrayList<ArrayList<String>>(); // make new array
			for (int i = 0; i < fileByLine.size(); ++i) { // for size of input array
				manipulated.add(new ArrayList<String>()); // add subArray in new array
				for (int j = 0; j < fileByLine.get(i).size(); ++j) { // for size of subArray
					String word = fileByLine.get(i).get(j); // get string from input array
					if (word.isEmpty()) { // if string is empty
						manipulated.get(i).add(word); // add to new array
					} else {
						manipulated.get(i).add(translate(word, dict)); // add translated string to new array
					}
				}
			}
			fileByLine = manipulated; // input array is equal to new array
		}
		if (modFlags[Config.MOD_PIG]) { // if pig latin mod is on
			ArrayList<ArrayList<String>> manipulated = new ArrayList<ArrayList<String>>(); // make new array
			for (int i = 0; i < fileByLine.size(); ++i) { // for size of input array
				manipulated.add(new ArrayList<String>()); // add subArray in new array
				for (int j = 0; j < fileByLine.get(i).size(); ++j) { // for size of subArray
					String word = fileByLine.get(i).get(j); // get string from input array
					if (word.isEmpty()) { // if string is empty
						manipulated.get(i).add(word); // add string to new array directly
					} else {
						manipulated.get(i).add(pigLatin(word)); // add pig latin translated array to new array
					}
				}
			}
			fileByLine = manipulated; // input array is equal to new array
		}
		if (modFlags[Config.MOD_REV_WORD]) { // if reverse word mod is on
			ArrayList<ArrayList<String>> manipulated = new ArrayList<ArrayList<String>>(); // make new array
			for (int i = 0; i < fileByLine.size(); ++i) { // for size of input array
				manipulated.add(new ArrayList<String>()); // add subArray in new array
				for (int j = 0; j < fileByLine.get(i).size(); ++j) { // for size of subArray
					String word = fileByLine.get(i).get(j); // get string from input array
					if (word.isEmpty()) { // if string is empty
						manipulated.get(i).add(word); // add string to new array directly
					} else {
						char first = word.charAt(0);
						if (!Character.isLetter(first)) { // if string is punctuation
							manipulated.get(i).add(word); // add string to new array directly
						} else {
							manipulated.get(i).add(reverse(word)); // add reversed word to new array
						}
					}
				}
			}
			fileByLine = manipulated; // original array is now new array
		}
		if (modFlags[Config.MOD_REV_LINE]) { // if reverse line mod is on
			ArrayList<ArrayList<String>> manipulated = new ArrayList<ArrayList<String>>(); // make new array
			for (int i = 0; i < fileByLine.size(); ++i) { // for size of input array
				manipulated.add(reverse(fileByLine.get(i))); // add reversed subArray to new array
			}
			fileByLine = manipulated; // original array is now new array
		}
		return fileByLine; // return array
	}

	/**
	 * This is the main method for the TextManipulator program. This method contains
	 * the loop that runs the prompt. It handles the user response and calls the
	 * methods that are necessary in order to perform the actions requested by the
	 * user.
	 *
	 * The initial default behavior of the program is to show the full menu, to be
	 * in the "Modified" mode, to have no modifications selected, and to have no
	 * values for the various file names.
	 *
	 * @param args
	 *            (unused)
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // initialize scanner
		String[] files = new String[Config.NUM_FILES]; // initialize files matrix
		boolean[] modFlags = new boolean[Config.NUM_MODS]; // initialize mods matrix
		boolean modeBoth = false; // initialize that showing both is false
		boolean showMenu = true; // initialize that showing the menu is true
		boolean proceed = true; // initialize that quit has not been activated
		char action; // initialize action character
		String original; // initialize file name
		ArrayList<ArrayList<String>> fileByLine = new ArrayList<ArrayList<String>>(); // initialize input file array
		ArrayList<ArrayList<String>> modFileByLine = new ArrayList<ArrayList<String>>(); // initialize modified file
																							// array
		ArrayList<String[]> wordList = new ArrayList<String[]>(); // initialize dictionary file array
		while (proceed) { // while quit has not been activated
			action = promptMenu(sc, files, modFlags, modeBoth, showMenu); // obtain input action

			if (action == 'i' || action == 'I') {
				fileByLine.clear(); // clear input file
				files[Config.FILE_IN] = updateFileName(sc, files[Config.FILE_IN]); // update the name of input file
				try { // try to open prompted file
					readInputFile(files[Config.FILE_IN], fileByLine);
					modFileByLine = fileByLine;
				} catch (IOException e) { // catch FileNotFound exception
				}
			} else if (action == 'h' || action == 'H') {
				showMenu = !showMenu; // toggles show menu boolean
			} else if (action == '1') {
				for (int i = 0; i < 80; ++i) { // 80 times
					System.out.print(Config.LINE_CHAR); // print line character
				}
				System.out.println("");
				try { // try to display input file
					display(fileByLine, modFileByLine, modeBoth);
				} catch (Exception e) { // catch null pointer exception
				}
				for (int i = 0; i < 80; ++i) { // 80 times
					System.out.print(Config.LINE_CHAR); // print line character
				}
				System.out.println("");
			} else if (action == 't' || action == 'T') {
				modFlags[Config.MOD_TRANS] = !modFlags[Config.MOD_TRANS]; // toggles translation boolean in mods array
				if (!wordList.isEmpty()) { // if dictionary file isn't empty
					modFileByLine = manipulate(fileByLine, wordList, modFlags); // update modified file
				}
			} else if (action == 'w' || action == 'W') {
				modFlags[Config.MOD_REV_WORD] = !modFlags[Config.MOD_REV_WORD]; // toggles reverse word boolean in mods
																				// array
				modFileByLine = manipulate(fileByLine, wordList, modFlags); // update modified file
			} else if (action == 'd' || action == 'D') {
				original = files[Config.FILE_DICT]; // stores original dictionary file name
				files[Config.FILE_DICT] = updateFileName(sc, files[Config.FILE_DICT]); // updates dictionary file name
				if ((files[Config.FILE_DICT] != null) // if new file name isn't null and isn't the same as before
						|| !original.equals(files[Config.FILE_DICT])) {
					try { // try to read specified dictionary file
						readDictFile(files[Config.FILE_DICT], wordList);
					} catch (IOException e) { // catch fileNotFound exception
					}
				}
			} else if (action == 'm' || action == 'M') {
				modeBoth = !modeBoth; // toggles show both boolean
			} else if (action == '2') {
				try { // try to save modified array to output file
					saveToFile(files[Config.FILE_OUT], modFileByLine);
				} catch (IOException e) { // catch fileNotFound exception
				}
			} else if (action == 'p' || action == 'P') {
				modFlags[Config.MOD_PIG] = !modFlags[Config.MOD_PIG]; // toggles pig latin boolean in mods array
				modFileByLine = manipulate(fileByLine, wordList, modFlags); // update modified file
			} else if (action == 'l' || action == 'L') {
				modFlags[Config.MOD_REV_LINE] = !modFlags[Config.MOD_REV_LINE]; // toggles reveres line boolean in mods
																				// array
				modFileByLine = manipulate(fileByLine, wordList, modFlags); // update modified file
			} else if (action == 'o' || action == 'O') {
				files[Config.FILE_OUT] = updateFileName(sc, files[Config.FILE_OUT]); // update output file name
			} else if (action == 'q' || action == 'Q') {
				proceed = false; // exit loop
			}

			else {
				System.out.println("Unknown option.");
			}
		}
	}
}
