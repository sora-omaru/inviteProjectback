package com.omaru.wedding.exception;

public class InviteNotFoundException extends RuntimeException {
    public InviteNotFoundException() {
        super("Invite not found");
    }
}
