public class PASSWORD {

    public Boolean checkUserName(String username) {
        return username!= null && username.contains("_") && username.length() <= 5;
    }

    public Boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return password.matches(passwordPattern);
    }

    public Boolean checkCellPhoneNumber(String cellNumber) {
        String cellPattern = "^\\+27\\d{9}$";
        return cellNumber!= null && cellNumber.matches(cellPattern);
    }

    public String getUserNameMessage(String username) {
        if (checkUserName(username)) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
    }

    public String getPasswordMessage(String password) {
        if (checkPasswordComplexity(password)) {
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
    }

    public String getCellMessage(String cellNumber) {
        if (checkCellPhoneNumber(cellNumber)) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
    }
}