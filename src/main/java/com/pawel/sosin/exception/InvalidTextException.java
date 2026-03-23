/**
 * The package contains exceptions used in Rail Fence Cipher
 */
package com.pawel.sosin.exception;

/**
 * Custom exception which is thrown when the text is invalid
 * 
 * @author Pawel Sosin
 * @version 1.0
 */
public class InvalidTextException extends Exception {
    /**
     * Constructs a new InvalidTextException with a specified error message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public InvalidTextException(String message) {
        super(message);
    }
}