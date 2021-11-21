//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           DrawHalfArrow
// Files:           DrawHalfArrow.java
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

import java.util.Scanner; 

public class DrawHalfArrow {
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      
	  int arrowBaseHeight = 0;
      int arrowBaseWidth  = 0;
      int arrowHeadWidth = 0;

      
      System.out.print("Enter arrow base height: ");
      arrowBaseHeight = scnr.nextInt();

      System.out.print("Enter arrow base width: ");
      arrowBaseWidth = scnr.nextInt();

      System.out.print("Enter arrow head width: ");
      arrowHeadWidth = scnr.nextInt();
      
      while (arrowHeadWidth <= arrowBaseWidth) { //make sure arrow head width is larger than base width
    	   // Prompt user for a valid arrow head value
    	    System.out.print("Enter arrow head width: ");
    	      arrowHeadWidth = scnr.nextInt();
    	}
      System.out.println("");

      for(int i=1; i<=arrowBaseHeight; ++i) { //draw arrow base
    	  	for(int j=1; j<=arrowBaseWidth; ++j) {
    	  		System.out.print("*");
    	  	}
    	  	System.out.println("");
      }

      for(int i=arrowHeadWidth; i > 0; --i) {//draw arrow head
    	  	for(int j=i; j > 0; --j) {
    	  		System.out.print("*");
    	  	}
    	  	System.out.println("");
      }
      scnr.close();
      
   }
}