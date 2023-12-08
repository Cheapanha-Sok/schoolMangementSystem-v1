package schoolManagements;


import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Enroll {
    // Declare 3 private instance variable
    private String studentID;
    private String departmentID;
    // Contractor with 3 parameter
    public Enroll(String studentID, String departmentID) {
        this.studentID = studentID;
        this.departmentID = departmentID;

    }
    // Method to check if an enroll ID already exists in the file
    boolean isIdExists(String studentID ,String departmentID) {
        boolean isExists = false;
        try {
        BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Enroll.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(studentID) && data[1].equals(departmentID)) {
                    isExists = true;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return isExists;
    }
    // Method to read student details
    boolean readEnroll() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter student ID: ");
        studentID = input.nextLine();
        System.out.print("Enter department ID: ");
        departmentID = input.nextLine();
        if (isIdExists(studentID,departmentID)){
            System.out.println("This id already exist !");
            return false;
        }else
            return true;
    }
    // Method to enroll student into department
    public void addEnroll() {
        try {
            if (readEnroll()) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Enroll.txt", true);
                fileWriter.write(studentID + ", " + departmentID + "\n");
                System.out.println("Student enrolled into department successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to enroll student.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Method to remove student from department
    public void removeStudentFromDept(String studentID, String deptID) {
        try {
            File originalFile = new File("Text_file_data/Enroll.txt");
            File tempFile = new File("Temp/Enroll.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] enrollData = line.split(", ");
                if (enrollData[0].equals(studentID) && enrollData[1].equals(deptID)) {
                    found = true;
                } else {
                    writer.write(line + "\n");
                }
            }
            reader.close();
            writer.close();
            if (found) {
                originalFile.delete();
                tempFile.renameTo(originalFile);
                System.out.println("Remove successful.");
            } else {
                System.out.println("Student with ID " + studentID + " not found in department " + deptID);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Method to display all students studying at a given department
    public void displayAllStudentStudyAtDept(String deptIDToDisplay) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Enroll.txt"))) {
            String line;
            System.out.printf("| %10s |\n", "StudentId");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[1].equals(deptIDToDisplay)) {
                    found = true;
                    System.out.printf("| %10s |%n", data[0]);
                }
            }
            reader.close();
            if (!found) {
                System.out.println("Not found this department id !");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Method to display all departments studied by a given student
    public void displayAllDeptStudiesByStudent(String studentID) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Enroll.txt"))) {
            String line;
            System.out.printf("| %10s |\n","DepartmentId");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(studentID)) {
                    found = true;
                    System.out.printf("| %10s |%n", data[1]);
                }
            }
            reader.close();
            if (!found) {
                System.out.println("Not found this student id !");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class Enroll_student_to_dep {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        Enroll enroll = new Enroll("", "");
        try {
            // User input
            while (!isExit) {
                System.out.println(" --".repeat(20));
                System.out.println("a. Enroll student into department");
                System.out.println("b. Remove student from department");
                System.out.println("c. Display all student study at given department");
                System.out.println("d. Display all department studies by given student");
                System.out.println("e. Exit");
                System.out.println(" --".repeat(20));
                System.out.print("Enter your choice: ");
                char choice = input.next().toLowerCase().charAt(0);
                input.nextLine();

                switch (choice) {
                    case 'a' -> enroll.addEnroll();
                    case 'b' -> {
                        System.out.print("Enter student ID to remove: ");
                        String idTodelete = input.nextLine();
                        System.out.print("Enter department to remove: ");
                        String deptTodelete = input.nextLine();
                        enroll.removeStudentFromDept(idTodelete, deptTodelete);
                    }
                    case 'c' -> {
                        System.out.print("Enter department ID: ");
                        String deptIDToDisplay = input.nextLine();
                        enroll.displayAllStudentStudyAtDept(deptIDToDisplay);
                    }
                    case 'd' -> {
                        System.out.print("Enter student name: ");
                        String studentNameToDisplay = input.nextLine();
                        enroll.displayAllDeptStudiesByStudent(studentNameToDisplay);
                    }
                    case 'e' -> {
                        System.out.println("Exit program.");
                        isExit= true;
                    }
                    default -> System.out.println("Invalid choice. Try again");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Try again");
        }
    }
}
