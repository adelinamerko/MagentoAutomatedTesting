package selenium.training.utils;

public class AccountDetails {
    private static AccountDetails accountDetails = new AccountDetails();
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private AccountDetails() {
        this.email = generateEmail();
        this.password = "AdelinaMerko1234!@#$";
        this.firstName = "Adela";
        this.lastName = "Merko";
    }

    public static AccountDetails getAccountDetails() {
        if (accountDetails == null) {
            accountDetails = new AccountDetails();
        }
        return accountDetails;
    }

    private static String generateEmail() {
        return "adelinamerko" + System.currentTimeMillis() + "@gmail.com";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
