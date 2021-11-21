/**
 * This class contains the constants used in the TextManipulator program. These constants may be changed
 * when testing and so your program should use the constants but not the values.
 * 
 * @author Marc Renault
 *
 */
public class Config {

    /** 
     * The number of files and the indexes for the different file names.
     */
    public static final int NUM_FILES = 3;
    public static final int FILE_IN = 0; //Input file
    public static final int FILE_OUT = 1; //Output file
    public static final int FILE_DICT = 2; //Dictionary file

    /**
     * The number of text modifications and the indexes for the different modifications.
     */
    public static final int NUM_MODS = 4;
    public static final int MOD_TRANS = 0; //Translation
    public static final int MOD_PIG = 1; //Pig Latin
    public static final int MOD_REV_WORD = 2; //Reverse word
    public static final int MOD_REV_LINE = 3; //Reverse line

    /**
     * The character used for lines.
     */
    public static final char LINE_CHAR = '-';

}
