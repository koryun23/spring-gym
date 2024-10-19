package org.example.service.core;

public interface AuthenticatorService {

    boolean authFail(String username, String password);

    boolean authSuccess(String username, String password);
}
