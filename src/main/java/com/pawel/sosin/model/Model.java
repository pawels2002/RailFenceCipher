/**
 * The package contains model for encoding and decoding 
 */
package com.pawel.sosin.model;
import com.pawel.sosin.exception.RailFenceCipherException;
import com.pawel.sosin.enums.Direction;
import com.pawel.sosin.exception.InvalidTextException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class with methods responsible for encoding and decoding given text with RailFenceCipher
 *
 * @author Pawel Sosin
 * @version 1.0
 */
public class Model {
    /**
     * An array containing History of operations
     */
    public ArrayList<HistoryRecord> recordList = new ArrayList<>();
    
    /**
     * Empty constructor
     */
    public Model(){};
    /**
     * Method encoding the given text with Rail Fence Cipher with given number of rails
     * 
     * @param text message to encode
     * @param rails number of rails used to encode
     * @return encoded message
     * @throws RailFenceCipherException thrown if the number of rails is less than 2
     * @throws InvalidTextException thrown if the text contains letters outside the enlglish alphabet
     */
    public String encode(String text, int rails) throws RailFenceCipherException, InvalidTextException {
        if (rails < 2)
            throw new RailFenceCipherException("Wrong number of rails");
        if(!isValidText(text))
            throw new InvalidTextException("Incorrect input. The text should contain only english letters.");
        
        // Creates a list of StringBuilder objects, one for each rail in the Rail Fence Cipher.
        List<StringBuilder> railedText = 
        // Creates an IntStream that generates numbers from 0 to (rails - 1).        
        IntStream.range(0, rails)
                // Maps each number in the stream to a new StringBuilder instance.
                 .mapToObj(i -> new StringBuilder())
                // Collects all the StringBuilder instances into a List.
                 .collect(Collectors.toList());

        int counter = 0;               // Index for the current rail
        Direction direction = Direction.DOWN;  // Start by moving down

        for (int i = 0; i < text.length(); i++) {
            // Add the current character to the correct rail
            if (Character.isWhitespace(text.charAt(i)))
                continue;
            railedText.get(counter).append(text.charAt(i));

            // Toggle direction at each diagonal boundary
            if (counter == 0) {
                direction = Direction.DOWN;
            } else if (counter == rails - 1) {
                direction = Direction.UP;
            }
            // Adjust counter based on the direction
            if (direction == Direction.DOWN) {
                counter++;
            } else {
                counter--;
            }
        }

        // Concatenate all rail strings to form the final encoded message
        StringBuilder encodedText = new StringBuilder();
        //For-each loop                                                          <------- for-each loop
        for (StringBuilder t : railedText) {
            encodedText.append(t);
        }
        HistoryRecord record = new HistoryRecord("Encode",rails,text,encodedText.toString());
        recordList.add(record);
        return encodedText.toString();
    }

    
    /**
     * Brute force decoding based on x and y variable
     * It decodes the given text with Rail Fence Cipher with given number of rails using 
     * 
     * @param text message to decode
     * @param rails number of rails used to decode
     * @return decoded message
     * @throws RailFenceCipherException thrown if the number of rails is less than 2
     * @throws InvalidTextException thrown if the text contains letters outside the enlglish alphabet
     */
    public String decodeBF(String text, int rails) throws RailFenceCipherException, InvalidTextException{
        //rail fence cipher needs at least 2 rails
        if (rails < 2)
            throw new RailFenceCipherException("Wrong number of rails");
        if(!isValidText(text))
            throw new InvalidTextException("Incorrect input. The text should contain only english letters.");
        if(rails > text.length())
            return text;
        int numberOfDiagonals;          //number of diagonals
        int numberOfEmptySpaces = 0 ;     //number of empty spaces
        // Calculate number of diagonals (x) and empty spaces (y) based on the length and rails
        for(numberOfDiagonals=0;numberOfDiagonals<text.length();numberOfDiagonals++){
            numberOfEmptySpaces = (1 * (rails + ( (rails - 1) * numberOfDiagonals))) - text.length();
            if(numberOfEmptySpaces > 0 && numberOfDiagonals > 0){
                numberOfDiagonals++;            //after calculation of y the x variable is 1 less than the number of diagonals so increment is needed
                break;
            }  
        }
        String decodedText = "";
        List<List<Character>> railedText = new ArrayList<>();
        for(int i =0 ; i < rails;i++){
           for(int j = 0 ; j <numberOfDiagonals;j++){
                //Creates an inner list with x elements, all initialized to ' ' (space).
                List<Character> rail = new ArrayList<>(Collections.nCopies(numberOfDiagonals,' '));
                railedText.add(rail);
            } 
        }
        int counter = 0;    //keeps track of characters in the encoded text
        //fill the first rail
        for(int i = 0 ; i < numberOfDiagonals ; i++){
            if(i%2==0)
                railedText.get(0).set(i,text.charAt(counter++));
        }
        //if there are more than 2 rails, handle the middle rails
        if(rails>2){
            if(numberOfDiagonals%2!=0){     //when number of diagonals is odd
                for(int i = 1 ; i < rails-numberOfEmptySpaces;i++){
                    if(i==rails-1)
                            break;
                    for(int j = 0;j<numberOfDiagonals;j++){
                        railedText.get(i).set(j, text.charAt(counter++));
                    }
                }
                for(int i = rails-numberOfEmptySpaces;i<rails-1;i++){
                    if(i==rails-1)
                            break;
                    for(int j = 0 ; j<numberOfDiagonals-1;j++){

                        railedText.get(i).set(j, text.charAt(counter++));
                    }
                }
            }
            else{       //when number of diagonals is even
                for(int i = 1 ; i < numberOfEmptySpaces;i++){
                    for(int j = 0 ; j<numberOfDiagonals-1;j++){
                        railedText.get(i).set(j, text.charAt(counter++));
                    }
                }
                for(int i = numberOfEmptySpaces;i<rails-1;i++){
                    for(int j = 0;j<numberOfDiagonals;j++){
                        railedText.get(i).set(j, text.charAt(counter++));
                    }
                }
            }
        }
        //fill the last rail
        for(int i = 0 ; i < numberOfDiagonals ; i++){
            if(i%2!=0)
                railedText.get(rails - 1).set(i, text.charAt(counter++));
        }
        //construct the final decoded text by iterating over the 2D array
        for(int i = 0 ; i<numberOfDiagonals;i++){
            for(int j = 0 ; j< rails;j++){
                if(i%2!=0){
                    if(railedText.get((rails - 1) - j).get(i) != ' ')
                        decodedText += railedText.get((rails - 1) - j).get(i);
                }
                else
                    if (railedText.get(j).get(i) != ' ')
                        decodedText += railedText.get(j).get(i);
            }
        }
    HistoryRecord record = new HistoryRecord("Decode",rails,text,decodedText);
    recordList.add(record);    
    return decodedText;
    }
    
    /**
     * Validates the given text input to ensure it contains only English letters and spaces.
     * 
     * @param text The text to validate.
     * @return true if the text contains only English letters and spaces, false otherwise.
     */
    private boolean isValidText(String text) {
        if(text == null)
            return false;
        String pattern = "[a-zA-Z]+[a-zA-Z ]*$";
        return Pattern.matches(pattern, text);
    }
}

