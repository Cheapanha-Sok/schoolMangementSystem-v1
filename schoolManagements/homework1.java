package schoolManagements;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Faculty {
    // Declare 4 private instance variable
    private String facultyID;
    private String facultyName;
    private String deanName;
    private String officeNo;

    // Contractor with 4 parameter
    Faculty(String facultyID, String facultyName, String deanName, String officeNo) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        this.deanName = deanName;
        this.officeNo = officeNo;
    }

    // method for check id exist in text file or not
    boolean idIsExist(String id) {
        boolean isExists = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Faculty.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(id)) {
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

    // method for read faculty by user input
    boolean readFaculty() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter id: ");
            facultyID = input.nextLine();
            if (idIsExist(facultyID)) {
                System.out.println("This id is already exist. Please change to another id.");
                return false;
            }
            System.out.print("Enter the faculty name: ");
            facultyName = input.nextLine();
            System.out.print("Enter the dean name: ");
            deanName = input.nextLine();
            System.out.print("Enter the office number: ");
            officeNo = input.nextLine();
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Error :" + e.getMessage());
            return false;
        }
    }

    // method for add faculty into text file
    void addFaculty() {
        try {
            if (readFaculty()) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Faculty.txt", true);
                fileWriter.write(facultyID + ", " + "Faculty of " + facultyName + ", " + deanName + ", " + officeNo + "\n");
                System.out.println("Faculty added successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to add faculty.");
            }
        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }
    }

    // method for search faculty by id
    void searchFaculty(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Faculty.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(id)) {
                    System.out.printf("| %10s | %20s | %10s | %10s |\n", "facultyID", "facultyName", "deanName", "officeNo");
                    System.out.printf("| %10s | %20s | %10s | %10s |%n", id, data[1], data[2], data[3]);
                    found = true;
                    break;
                }
            }
            reader.close();
            if (!found) {
                System.out.println("Faculty not found.");
            }
        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }
    }

    // method for delete faculty by id
    void deleteFaculty(String id) {
        try {
            File originalFile = new File("Text_file_data/Faculty.txt");
            File tempFile = new File("Temp/Faculty.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(id)) {
                    continue;
                }
                writer.write(line + "\n");
            }
            reader.close();
            writer.close();
            originalFile.delete();

            tempFile.renameTo(originalFile);
            System.out.println("Remove successful");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    // method fir update faculty by id
    void updateFaculty(String id, String newFacultyName, String newDeanName, String newOffice) {
        try {
            File originalFile = new File("Text_file_data/Faculty.txt");
            File tempFile = new File("Temp/Faculty.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(id)) {
                    line = id + ", " + "Faculty of " + newFacultyName + ", " + newDeanName + ", " + newOffice;
                }
                writer.write(line + "\n");
            }
            reader.close();
            writer.close();
            originalFile.delete();

            tempFile.renameTo(originalFile);
            System.out.println("Update successful");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class manageFaculty {
    public static void main(String[] args) {
        Faculty faculty = new Faculty("", "", "", "");
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        String id;
        try {
            while (!isExit) {
                System.out.println(" --".repeat(20));
                System.out.println("a. Add a new faculty");
                System.out.println("b. Search a faculty by id");
                System.out.println("c. Update a faculty");
                System.out.println("d. Delete a faculty by id");
                System.out.println("e. Exit");
                System.out.println(" --".repeat(20));
                System.out.print("Enter your choice: ");
                char choice = input.next().toLowerCase().charAt(0);
                input.nextLine();
                switch (choice) {
                    case 'a'->
                        faculty.addFaculty();
                    case 'b' -> {
                        System.out.print("Enter id to search: ");
                        id = input.nextLine();
                        faculty.searchFaculty(id);
                    }
                    case 'c'-> {
                        System.out.println("Which id you want to update? ");
                        id = input.nextLine();
                        if (faculty.idIsExist(id)) {
                            System.out.print("Enter new faculty_name: ");
                            String faculty_name = input.nextLine();
                            System.out.print("Enter new dean_name: ");
                            String dean_name = input.nextLine();
                            System.out.print("Enter new office_no: ");
                            String office_no = input.nextLine();
                            faculty.updateFaculty(id, faculty_name, dean_name, office_no);
                        } else {
                            System.out.println("Not found this id.");
                        }
                    }
                    case 'd'->{
                        System.out.print("Enter id to delete :");
                        id = input.nextLine();
                        if (faculty.idIsExist(id)){
                            faculty.deleteFaculty(id);
                        }else
                            System.out.println("Not found this id");
                    }
                    case 'e' ->{
                        System.out.println("Exit program");
                        isExit = true;
                    }
                    default -> System.out.println("Invalid input. Try again!");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Try again!");
        }
    }
}
