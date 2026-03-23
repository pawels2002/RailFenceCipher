/**
 * The package contains model for encoding and decoding 
 */
package com.pawel.sosin.model;

/**
 * Represents a record of an operation performed using a Rail Fence Cipher.
 * This record stores details about the operation type, the number of rails used,
 * the input text, and the resulting output text.
 * 
 * @param operation the type of operation encode or decode
 * @param rails the number of rails
 * @param input the input text given by the user to encode/decode
 * @param output the output given by the application after decoding or encoding
 * 
 * @author Pawel Sosin
 * @version 1.0
 */
public record HistoryRecord(String operation, int rails, String input, String output) { }