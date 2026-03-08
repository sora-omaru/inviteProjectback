package com.omaru.wedding.exception;

//アレルギー情報のエラーメッセージ表示
public class InvalidAllergySelectionException extends RuntimeException {
    public InvalidAllergySelectionException(String message) {
        super(message);
    }
}
