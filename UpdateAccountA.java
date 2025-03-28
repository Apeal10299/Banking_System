import java.io.*;
import java.util.Random;
import java.util.Scanner;

class AccountInfo extends Accountfeatures {
    protected String AccountHolder;
    protected String AccountNumber;
    protected float balance;
    protected double interestRate;

    public AccountInfo(String accountHolder, String accountNumber, float balance, double interestRate) {
        super(accountHolder, accountNumber, balance, interestRate);
        this.AccountHolder = accountHolder;
        this.AccountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public void info() {
        System.out.println("\n=====================================");
        System.out.println("         Account Information         ");
        System.out.println("=====================================");
        System.out.println("| Account Holder: " + AccountHolder);
        System.out.println("| Account Number: " + AccountNumber);
        System.out.println("| Balance: " + balance);
        System.out.println("=====================================");
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("\033[32m+" + amount + " deposited successfully.\033[0m New Balance: " + balance);
            updateBalanceInFile();
        } else {
            System.out.println("\033[31mInvalid deposit amount.\033[0m");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("\033[32m-" + amount + " withdrawn successfully.\033[0m New Balance: " + balance);
            updateBalanceInFile();
        } else {
            System.out.println("\033[31mInsufficient Balance.\033[0m");
        }
    }

    public void addInterest() {
        if (interestRate > 0 && balance > 0) {
            double interest = balance * (interestRate / 100);
            balance += interest;
            System.out.println("\033[32mInterest of " + interest + " added. New balance: " + balance + "\033[0m");
            updateBalanceInFile();
        } else {
            System.out.println("\033[31mInterest rate is 0% or balance is insufficient, no interest added.\033[0m");
        }
    }

    private void updateBalanceInFile() {
        File inputFile = new File("accounts.csv");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder updatedContent = new StringBuilder();
            String line;
            reader.readLine(); // Skip the header row

            while ((line = reader.readLine()) != null) {
                String[] accountDetails = line.split(",");
                if (accountDetails.length >= 6) {
                    String savedAccountNumber = accountDetails[3];

                    if (savedAccountNumber.equals(AccountNumber)) {
                        accountDetails[4] = String.valueOf(balance);
                        accountDetails[5] = String.valueOf(interestRate);
                    }
                    updatedContent.append(String.join(",", accountDetails)).append("\n");
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
            writer.write("Username,Password,User Name,Account Number,Balance,InterestRate\n"); // Re-add header
            writer.write(updatedContent.toString());
            writer.close();

            System.out.println("\033[32mAccount balance updated successfully.\033[0m");
        } catch (IOException e) {
            System.out.println("\033[31mError updating the CSV file.\033[0m");
        }
    }

    public void mode() {
        Scanner scan = new Scanner(System.in);
        boolean isLoggedIn = true;

        while (isLoggedIn) {
            System.out.println("=========== Menu ===========");
            System.out.println("| 1. Deposit               |");
            System.out.println("| 2. Withdraw              |");
            System.out.println("| 3. Balance               |");
            System.out.println("| 4. Balance with Interest |");
            System.out.println("| 5. Account Information   |");
            System.out.println("| 6. Log Out               |");
            System.out.println("| 7. Exit                  |");
            System.out.println("=============================");
            System.out.print("Choose Your option: ");
            int choose = scan.nextInt();
            switch (choose) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double deposit_amount = scan.nextDouble();
                    deposit(deposit_amount);
                    break;
                case 2:
                    System.out.println("You have " + balance + " in your account.");
                    System.out.print("Enter amount to withdraw: ");
                    double withdraw_amount = scan.nextDouble();
                    withdraw(withdraw_amount);
                    break;
                case 3:
                    System.out.println("\033[34mYour balance is " + balance + "\033[0m");
                    break;
                case 4:
                    addInterest();
                    System.out.println("\033[34mYour balance after interest is: " + balance + "\033[0m");
                    break;
                case 5:
                    info();
                    break;
                case 6:
                    Scanner roti = new Scanner(System.in);
                    System.out.print("Do you really want to Log Out? (Yes/No): ");
                    String choice = roti.nextLine();
                    if (choice.equalsIgnoreCase("Yes")) {

                        clear_screen();
                        System.out.println("1. Menu Section \n2. Log In");
                        System.out.print("Enter your choice: ");
                        int casee = roti.nextInt();
                        switch (casee) {
                            case 1:
                                menu_section();
                                break;
                            case 2:
                                login();
                                break;
                        
                            default:
                                break;
                        }
                    } else {
                        mode();
                    }
                    break;
                case 7:
                    System.out.println("\033[33mHave a Good Day || Bye! :) \033[0m");
                    isLoggedIn = false;
                    break;
                default:
                    System.out.println("\033[31mInvalid option. Try again...\033[0m");
                    break;
            }
        }
    }
}

class Accountfeatures {
    private double interestRate;
    protected float balance;

    public Accountfeatures(String accountHolder, String accountNumber, float balance, double interestRate) {
        this.interestRate = interestRate;
        this.balance = balance;
    }

    void clear_screen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void createaccount() {
        Scanner scan = new Scanner(System.in);

        clear_screen();
        System.out.print("\nEnter admin username: ");
        String adminUsername = scan.nextLine();
        System.out.print("Enter admin password: ");
        String adminPassword = scan.nextLine();

        String validAdminUsername = "admin";
        String validAdminPassword = "admin123";

        if (adminUsername.equals(validAdminUsername) && adminPassword.equals(validAdminPassword)) {
            System.out.print("\nEnter your name: ");
            String accountHolder = scan.nextLine();
            System.out.print("Enter your number: ");
            String uname = scan.nextLine();

            String fixedPart = "15205";
            Random random = new Random();
            int randomPart = 100000 + random.nextInt(900000);
            String accountNumber = fixedPart + randomPart;

            System.out.println("Account Number: " + accountNumber);
            System.out.println("Username: " + uname);
            System.out.print("Password: ");
            String pname = scan.nextLine();

            System.out.print("Enter initial deposit balance: ");
            float balance = scan.nextFloat();

            System.out.print("Enter Interest Rate:");
            interestRate = scan.nextDouble();

            savedata(accountHolder, accountNumber, uname, pname, balance, interestRate);
        }
    }

    public void savedata(String accountHolder, String accountNumber, String uname, String pname, float balance, double interestRate) {
        File file = new File("accounts.csv");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter("accounts.csv", true);

            if (file.length() == 0) {
                writer.append("Username,Password,User Name,Account Number,Balance,Interest\n");
            }

            writer.append(uname + "," + pname + "," + accountHolder + "," + accountNumber + "," + balance + "," + interestRate + "\n");
            writer.close();
            System.out.println("\033[32mAccount info saved to accounts.csv successfully.\033[0m");
            menu_section();

        } catch (IOException e) {
            System.out.println("\033[31mError saving account info to CSV.\033[0m");
        }
    }

    public void login() {
        Scanner scan = new Scanner(System.in);

        clear_screen();
        System.out.println("\n----------- Log In ------------");
        System.out.print("  Username: ");
        String uname = scan.nextLine();
        System.out.print("  Password: ");
        String pname = scan.nextLine();


        AccountInfo accountInfo = verifyAccount(uname, pname);

        if (accountInfo != null) {
            System.out.println("\033[32mLog In Successfully\033[0m");
            clear_screen();
            accountInfo.mode();
        } else {
            System.out.println("\033[31mLog In Failed || Try Again...\033[0m");
            login();
        }
    }

    private AccountInfo verifyAccount(String uname, String pname) {
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.csv"))) {
            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] accountDetails = line.split(",");
                if (accountDetails.length >= 6) {
                    String savedUsername = accountDetails[0];
                    String savedPassword = accountDetails[1];
                    String accountHolder = accountDetails[2];
                    String accountNumber = accountDetails[3];
                    float balance = Float.parseFloat(accountDetails[4]);
                    Double interestRate = Double.parseDouble(accountDetails[5]);

                    if (savedUsername.equals(uname) && savedPassword.equals(pname)) {
                        return new AccountInfo(accountHolder, accountNumber, balance, interestRate);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\033[31mError reading the CSV file.\033[0m");
        }
        return null;
    }

    void menu_section(){
        Scanner scan = new Scanner(System.in);

        clear_screen();
        System.out.println("\033[34mWelcome to the Bank!\033[0m");
        System.out.println("\033[36mChoose an option:\033[0m");
        System.out.println("1. Create an Account");
        System.out.println("2. Login to Account");
        System.out.print("\033[32mEnter choice (1/2): \033[0m");
        int choice = scan.nextInt();
        scan.nextLine();  // consume the newline character

        if (choice == 1) {
            Accountfeatures account = new Accountfeatures("", "", 0, 0);
            account.createaccount();
        } else if (choice == 2) {
            System.out.println("\n\033[33mPlease log in.\033[0m");
            Accountfeatures account = new Accountfeatures("", "", 0, 0);
            account.login();
        } else {
            System.out.println("\033[31mInvalid choice. Exiting...\033[0m");
        }
    }
}

public class UpdateAccountA {
    public static void main(String[] args) {
        AccountInfo roti = new AccountInfo(null, null, 0, 0);
        roti.menu_section();
    }
}
