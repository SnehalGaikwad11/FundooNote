package com.bridgelab.user.dto;

public class PasswordDto {
private String confirmPassword;
private String newpassword;

public String getConfirmPassword() {
	return confirmPassword;
}

public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}

public String getNewpassword() {
	return newpassword;
}

public void setNewpassword(String newpassword) {
	this.newpassword = newpassword;
}

@Override
public String toString() {
	return "PasswordDto [confirmPassword=" + confirmPassword + ", newpassword=" + newpassword + "]";
}

}
