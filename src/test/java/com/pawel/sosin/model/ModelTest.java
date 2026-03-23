/*
 * This package contains unit tests for validating its encoding and decoding functionalities.
 */
package com.pawel.sosin.model;

import com.pawel.sosin.exception.RailFenceCipherException;
import com.pawel.sosin.exception.InvalidTextException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * This class contains unit tests for the Model class, specifically for the Rail Fence Cipher encoding 
 * and decoding functionalities.
 * 
 * @author Pawel Sosin
 * @version 1.0
 */
public class ModelTest {
    /**
     * The instance of the Model class under test.
     */
    private Model model;
    
    /**
     * Empty constructor
     */
    public ModelTest(){};
    
    /**
     * Sets up a new Model instance before each test.
     */
    @BeforeEach
    public void setUp() {
        model = new Model();
    }
    
    /**
     * Tests the encoding function with simple valid inputs.
     * @param text the input string to encode.
     * @param rails the number of rails for the Rail Fence Cipher.
     * @param result the expected encoded result.
     */
    @ParameterizedTest
    @CsvSource({"Hello,2,Hloel", "Hello World,3,HolelWrdlo", "This is a private MESSAGE,5,TrShpiESiavMAssaeGitE"})
    public void testEncodingWithSimpleInput(String text, int rails, String result) {
        try{
            assertEquals(model.encode(text, rails), result, "Obtained results differ from expected values.");
        }catch(RailFenceCipherException | InvalidTextException e){
            fail();
        }
    }
    
    /**
     * Tests the encoding function when the number of rails is extremely high.
     * The result should be the input text with spaces removed.
     * @param text the input string to encode.
     * @param rails the number of rails for the Rail Fence Cipher.
     * @param result the expected encoded result (input text without spaces).
     */
    @ParameterizedTest
    @CsvSource({"Hello,50,Hello", "Hello World,100,HelloWorld", "This is a private MESSAGE,5000,ThisisaprivateMESSAGE"})
    public void testEncodingWithHighNumberOfRails(String text, int rails, String result) {
        try{
            assertEquals(model.encode(text, rails), result, "Obtained results should be the same as input but without spaces.");
        }catch(RailFenceCipherException | InvalidTextException e){
            fail();
        }
    }
    
    
    /**
    * Tests the encoding function when the number of rails is equal to the length of the text.
    * The result should be the input text with spaces removed.
    * @param text the input string to encode.
    * @param rails the number of rails for the Rail Fence Cipher.
    * @param result the expected encoded result (input text without spaces)
    */
    @ParameterizedTest
    @CsvSource({"Hello,5,Hello", "Hello World,10,HelloWorld", "This is a private MESSAGE,21,ThisisaprivateMESSAGE"})
    public void testEncodingWithTheNumberOfRailsEqualToTheTextLength(String text, int rails, String result) {
        try{
            assertEquals(model.encode(text, rails), result, "Obtained results should be the same as input but without spaces.");
        }catch(RailFenceCipherException | InvalidTextException e){
            fail();
        }
    }
    
    /**
     * Tests the encoding function with invalid text input containing unsupported characters.
     * Ensures that an InvalidTextException is thrown.
     * @param text the invalid input string to encode.
     * @param rails the number of rails for the Rail Fence Cipher.
     */
    @ParameterizedTest
    @CsvSource({"Hello.,2", "Hello+ World,3", "Th15 15 4 pr1vat3 M355AGe!,4", "cześć,2", "Hello Every1,5"})
    public void testEncodingWithIncorrectTextInput(String text, int rails) {
        try{
            model.encode(text, rails);
            fail("InvalidTextException should be thrown.");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
    
    
    /**
     * Tests the encoding function with invalid rail numbers (e.g., 0 or negative values).
     * Ensures that a RailFenceCipherException is thrown.
     * @param text the input string to encode.
     * @param rails the invalid number of rails (e.g., 0 or negative values)
     */
    @ParameterizedTest
    @CsvSource({"Hello,0", "Hello World,1", "Hello,-1", "Hello,-50", "Hello World,0"})
    public void testEncodingWithIncorrectNumberOfRails(String text, int rails) {
        try{
            model.encode(text, rails);
            fail("RailFenceCipherException should be thrown");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
    
    /**
     * Tests the encoding function with empty or null input.
     * Ensures that an InvalidTextException is thrown.
     * @param text the null or empty input string to encode.
     * @param rails the number of rails for the Rail Fence Cipher.
     */
    @ParameterizedTest
    @CsvSource({",2", ",3", "\n,4", "\t,15"})
    public void testEncodingWithNoInput(String text, int rails) {
        try{
            model.encode(text, rails);
            fail("InvalidTextException should be thrown");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
    
    /**
     * Tests the encoding function with input containing only spaces.
     * Ensures that an InvalidTextException is thrown.
     * @param text the input string containing only spaces to encode.
     * @param rails the number of rails for the Rail Fence Cipher.
     */
    @ParameterizedTest
    @CsvSource({" ,2", "     ,3", "      ,4", "            ,15"})
    public void testEncodingWithOnlySpaces(String text, int rails) {
        try{
            model.encode(text, rails);
            fail("InvalidTextException should be thrown");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
    
    /**
     * Tests the decoding function with simple valid inputs.
     * Ensures that the decoded result matches the expected original text.
     * @param text the encoded string to decode.
     * @param rails the number of rails for the Rail Fence Cipher.
     * @param result the expected decoded original text.
     */
    @ParameterizedTest
    @CsvSource({"Hloel,2,Hello", "HolelWrdlo,3,HelloWorld", "TrShpiESiavMAssaeGitE,5,ThisisaprivateMESSAGE"})
    public void testDecodingWithSimpleInput(String text, int rails, String result) {
        try{
            assertEquals(model.decodeBF(text, rails), result, "Obtained results differ from expected values.");
        }catch(RailFenceCipherException | InvalidTextException e){
            fail();
        }
    }
    
    /**
     * Tests the decoding function with a high number of rails.
     * The result should match the input text.
     * @param text the encoded string to decode.
     * @param rails the number of rails for the Rail Fence Cipher.
     * @param result the expected decoded text matching the input.
     */
    @ParameterizedTest
    @CsvSource({"Hello,50,Hello", "Hello World,100,Hello World", "This is a private MESSAGE,5000,This is a private MESSAGE"})
    public void testDecodingWithHighNumberOfRails(String text, int rails, String result) {
        try{
            assertEquals(model.decodeBF(text, rails), result, "Obtained results should be the same as input.");
        }catch(RailFenceCipherException | InvalidTextException e){
            fail();
        }
    }
    
    /**
     * Tests the decoding function when the number of rails equals the length of the text.
     * The result should match the input text with spaces removed.
     * @param text the encoded string to decode.
     * @param rails the number of rails for the Rail Fence Cipher.
     * @param result the expected decoded text (input without spaces).
     */
    @ParameterizedTest
    @CsvSource({"Hello,5,Hello", "Hello World,11,HelloWorld", "This is a private MESSAGE,25,ThisisaprivateMESSAGE"})
    public void testDecodingWithTheNumberOfRailsEqualToTheTextLength(String text, int rails, String result) {
        try{
            assertEquals(model.decodeBF(text, rails), result, "Obtained results should be the same as input but without spaces.");
        }catch(RailFenceCipherException | InvalidTextException e){
            fail();
        }
    }
    
    /**
    * Tests the decoding function with invalid text input containing unsupported characters.
    * Ensures that an InvalidTextException is thrown.
    * @param text the invalid encoded string to decode.
    * @param rails the number of rails for the Rail Fence Cipher.
    */
    @ParameterizedTest
    @CsvSource({"Hello.,2", "Hello+ World,3", "Th15 15 4 pr1vat3 M355AGe!,4", "cześć,2", "Hello Every1,5"})
    public void testDecodingWithIncorrectTextInput(String text, int rails) {
        try{
            model.decodeBF(text, rails);
            fail("InvalidTextException should be thrown");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
    
    /**
     * Tests the decoding function with invalid rail numbers (e.g., 0 or negative values).
     * Ensures that a RailFenceCipherException is thrown.
     * @param text the encoded string to decode.
     * @param rails the invalid number of rails (e.g., 0 or negative values
     */
    @ParameterizedTest
    @CsvSource({"Hello,0", "Hello World,1", "Hello,-1", "Hello,-50", "Hello World,0"})
    public void testDecodingWithIncorrectNumberOfRails(String text, int rails) {
        try{
            model.decodeBF(text, rails);
            fail("RailFenceCipherException should be thrown");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
    
    /**
     * Tests the decoding function with empty or null input.
     * Ensures that an InvalidTextException is thrown.
     * @param text the null or empty encoded string to decode.
     * @param rails the number of rails for the Rail Fence Cipher.
     */
    @ParameterizedTest
    @CsvSource({",2", ",3", "\n,4", "\t,15"})
    public void testDecodingWithNoInput(String text, int rails) {
        try{
            model.encode(text, rails);
            fail("InvalidTextException should be thrown");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
    
    /**
     * Tests the decoding function with input containing only spaces.
     * Ensures that an InvalidTextException is thrown.
     * 
     * @param text the encoded string containing only spaces to decode.
     * @param rails the number of rails for the Rail Fence Cipher.
     */
    @ParameterizedTest
    @CsvSource({" ,2", "     ,3", "      ,4", "            ,15"})
    public void testDecodingWithOnlySpaces(String text, int rails) {
        try{
            model.decodeBF(text, rails);
            fail("InvalidTextException should be thrown");
        }catch(RailFenceCipherException | InvalidTextException e){
        }
    }
}
