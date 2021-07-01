import java.util.Scanner;
import java.io.*;
// Project 1 - A simple lexical analyzer - Input: A source code .txt file - Output: All recognized tokens and errors
// By: Luke Fetchko
// CSCI U530
// Dr. Ai
public class LexicalAnalyzer {

    public static void main(String[] args) throws IOException {
    	// Get file name
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter file name:");
        String file = scan.nextLine();
        scan.close();
        
        // Open new scanner to scan file
        Scanner scan1 = new Scanner(new File(file));
        // Line number variable
        int lineNum = 1;
        // String containing all recognized operators
        String ops = "=()+-*/,;";
        
        // Loop while scanner has next token and consume line into String
        while (scan1.hasNext()) {
        	String line = scan1.nextLine();
        	// Loop through String of input line
        	for (int i = 0; i < line.length(); i++) {
        		// If operator is found, print line number, index, and operator
        		if(ops.contains(Character.toString(line.charAt(i)))) {
        			String operator = Character.toString(line.charAt(i));
        			System.out.println("Line" + lineNum + ": " + i + " operator: " + operator);
        		// Else if a letter is found, first check for keywords which must follow this format, otherwise call processLetter
        		} else if (Character.isLetter(line.charAt(i))) {
        			
        			if (line.charAt(i) == 'i' && line.charAt(i+1) == 'n' && line.charAt(i+2) == 't' && line.charAt(i+3) == ' ') {
        				System.out.println("Line" + lineNum + ": " + i + " keyword: " + "int");
        				i += 3;
        			}else if (line.charAt(i) == 'd' && line.charAt(i+1) == 'o' && line.charAt(i+2) == 'u' && line.charAt(i+3) == 'b' && line.charAt(i+4) == 'l' && line.charAt(i+5) == 'e' && line.charAt(i+6) == ' ') {
        				System.out.println("Line" + lineNum + ": " + i + " keyword: " + "double");
        				i += 6;
        			} else if (line.charAt(i) == 'S' && line.charAt(i+1) == 't' && line.charAt(i+2) == 'r' && line.charAt(i+3) == 'i' && line.charAt(i+4) == 'n' && line.charAt(i+5) == 'g' && line.charAt(i+6) == ' ') {
        				System.out.println("Line" + lineNum + ": " + i + " keyword: " + "String");
        				i += 6;
        			} else {
        				int num = processLetter(line, i , lineNum);
        				if (num >= 2) {
        					i = i + (num-1);
        				}
        			}
        			
        		// Else if a number is found, call processNumber	
        		} else if(Character.isDigit(line.charAt(i))) {
        			int number = processNumber(line,i,lineNum);
        			i += (number-1);
        		// Else if a opening quote is found, call processString
        		} else if (line.charAt(i) == '"') {
        			int num1 = processString(line,i,lineNum);
        			i += (num1-1);
        		// Else if character at index i is not a space, then print error since lexeme is not recognized or expected there	
        		}else if (line.charAt(i) != ' ') {
        			System.out.println("Line" + lineNum + ": " + i + " error: " + line.charAt(i) + " not recognized or expected");
        		}
        		
        	}
        	// Increment line number variable
        	lineNum++;
        }
        // Close scanner object
        scan1.close();
 }
    // Method to process String starting from one index past opening quotes.
    // Checks if closing quotes are found, if so then it is valid String,
    // otherwise no closing quotes found and it is an error.
    // prints String constant or error and returns an integer of the String's length plus two to account for quotes.
	private static int processString(String s, int in, int lineNum) {
		// TODO Auto-generated method stub
		int end = 0;
		String str = "";
		boolean secondQuotes = false;
		for (int i = in + 1; i < s.length(); i++) {
			if (s.charAt(i) == '"') {
				secondQuotes = true;
				end = i;
				str = s.substring(in+1, end);
				break;
			} else if (i == s.length()-1){
				str = s.substring(in);
			}
		}
		if (secondQuotes) {
			System.out.println("Line" + lineNum + ": " + in + " string constant: \"" + str + "\"");
			return str.length() +2;
		} else {
			System.out.println("Line" + lineNum + ": " + in + " error: " + str);
			return str.length() + 2;
		}
	}
	// Method to process numbers of length two, e.g. XX or X.X,
	// checks if one index past start is decimal, if so then we store decimal number, 
	// if not then we know it is integer constant
	// returns integer representing length of String which contains the numbers recognized
	// prints integer constant or double constant
	private static int processNumber(String s, int in, int lineNum) {
		// TODO Auto-generated method stub
		int end = 0;
		String str = "";
		boolean decimal = false;
		for (int i = in + 1; i <s.length();i++) {
			if (s.charAt(i) == '.') {
				decimal = true;
				end = i + 2;
				str = s.substring(in,end);
				break;
			} else if (Character.isDigit(s.charAt(i))) {
				end = i + 1;
				str = s.substring(in, end);
				break;
				
			}
		}
		if (!decimal) {
			System.out.println("Line" + lineNum + ": " + in + " int constant: " + str);
			return str.length();
		} else {
			System.out.println("Line" + lineNum + ": " + in + " double constant: " + str);
			return str.length();
		}
	
		
	}
	// Method to process letters that are not key words. Loops until a character that is not a letter or digit is found.
	// Once that character is found we know that it marks the end of the identifier.
	// prints identifier String and returns integer representing length of identifier
	private static int processLetter(String s, int in, int lineNum) {
		int end = 0;
		String str = "";
		for (int i = in; i< s.length(); i++) {
			if (!Character.isLetterOrDigit(s.charAt(i))){
				end = i;
				str = s.substring(in,end);
				break;
				
			}
			
		}
		System.out.println("Line" + lineNum + ": " + in + " identifier: " + str);
		return str.length();

	}
		
}
    