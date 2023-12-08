package schoolManagements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class logIN{
    // Method for check account id and password and return boolean value
    static boolean login(String username, String password) {
        boolean isCorrect = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Account.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[1].equals(username) && data[3].equals(password)) {
                    isCorrect = true;
                    System.out.println("Hi " + data[2] + ", " +data[1]);
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return isCorrect;
    }
    static boolean isValidated(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        return login(username, password);
    }
}
