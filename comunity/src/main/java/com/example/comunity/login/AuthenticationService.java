package com.example.comunity.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	public boolean authenticate(String username, String password) {
		
		boolean isValidUserName = username.equalsIgnoreCase("odung");
		boolean isValidPassword = password.equalsIgnoreCase("mango");
		
		return isValidUserName && isValidPassword;
	}
}
