package schoolManagements;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Account {
    // Declare 5 private instance variable
    private String accountID;
    private String username;
    private String role;
    private String password;
    private String phoneNumber;
    // Contractor with 5 parameter
    Account(String accountID, String username, String role, String password, String phoneNumber) {
        this.accountID = accountID;
        this.username = username;
        this.role = role;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    // method for the account id is existed or not
    boolean isIdExists(String accountID) {
        boolean isExists = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Account.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(accountID)) {
                    isExists = true;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error :" + e);
        }
        return isExists;
    }
    // Method for read account
    boolean readAccountInput(String choice) {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter account id: ");
            accountID = input.nextLine();
            if (isIdExists(accountID)) {
                System.out.println("This id is already exist. Please change to another id.");
                return false;
            }
            System.out.print("Enter the user name: ");
            username = input.nextLine();
            System.out.print("Enter the password: ");
            password = input.nextLine();
            System.out.print("Enter the phone number: ");
            phoneNumber = input.nextLine();
            if (choice.equals("a")) {
                role = "teacher";
            } else {
                role = "student";
            }
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Error :" + e);
            return false;
        }
    }
    // Method for create account for teacher
    void createTeacherAccount() {
        Scanner input = new Scanner(System.in);
        try {
            if (readAccountInput("a")) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Account.txt", true);
                fileWriter.write(accountID + ", " + username + ", " + role + ", " + password + ", " + phoneNumber + "\n");
                System.out.print("Enter teacher id :");
                String id = input.nextLine();
                assignToRole("Teacher", id);
                System.out.println("Teacher account created successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to create teacher account.");
            }
        } catch (IOException e) {
            System.out.println("Error :" + e);
        }
    }
    // Method for create account for student
    void createStudentAccount() {
        Scanner input = new Scanner(System.in);
        try {
            if (readAccountInput("b")) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Account.txt", true);
                fileWriter.write(accountID + ", " + username + ", " + role + ", " + password + ", " + phoneNumber + "\n");
                System.out.print("Enter student id :");
                String id = input.nextLine();
                assignToRole("Student", id);
                System.out.print("Student account created successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to create student account.");
            }
        } catch (IOException e) {
            System.out.println("Error :" + e);
        }
    }
    // Method for assign to role by account id
    void assignToRole(String role, String id) {
        try {
            File originalFile = new File("Text_file_data/" + role + ".txt");
            File tempFile = new File("Temp/" + role + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(id)) {
                    parts[parts.length - 1] = accountID; // Update the last element in the array
                    line = String.join(", ", parts); // Join the updated array back to a line
                }
                writer.write(line + "\n");
            }
            reader.close();
            writer.close();
            originalFile.delete();
            tempFile.renameTo(originalFile);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
class createAccount {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        Account account = new Account("", "", "", "", "");
        try {
            while (!isExit) {
                System.out.println(" --".repeat(20));
                System.out.println("a. Create account for teacher");
                System.out.println("b. Create account for student");
                System.out.println("c. Exit");
                System.out.println(" --".repeat(20));
                System.out.print("Enter your choice: ");
                char choice = input.next().toLowerCase().charAt(0);
                input.nextLine();
                switch (choice) {
                    case 'a' -> account.createTeacherAccount();
                    case 'b' -> account.createStudentAccount();
                    case 'c' -> {
                        System.out.println("Exit program.");
                        isExit = true;
                    }
                    default -> System.out.println("Invalid input. Try again!");
                }
            }
        } catch (InputMismatchException e){
            System.out.println("Error :" +e);
        }
    }
}
