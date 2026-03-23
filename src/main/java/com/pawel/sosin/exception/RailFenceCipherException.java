/**
 * The package contains exception for Rail fence cipher application
 */
package com.pawel.sosin.exception;

/**
 *Custom exception for the Rail Fence Cipher operations
 *
 * @author Pawel Sosin
 * @version 1.0
 */
public class RailFenceCipherException extends Exception{
    /**
     * Constructor for the Rail Fence Cipher exception
     * @param message The detail message explaining the reason for the exception.
     */
    public RailFenceCipherException(String message) {
        super(message);
    }
}
