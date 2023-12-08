package schoolManagements;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Teacher {
    // Declare 7 private instance variable
    private String teacherID;
    private String teacherName;
    private String gender;
    private String dob;
    private String phoneNo;
    private String address;
    private String accountId;
    // Contractor with 7 parameter
    public Teacher(String teacherID, String teacherName, String gender, String dob, String phoneNo, String address,String deptId, String accountId) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.gender = gender;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.address = address;
        this.accountId = accountId;
    }

    // Check if a teacher with the given ID already exists
    boolean isIdExists(String id) {
        boolean isExists = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Teacher.txt"));
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
            System.out.println("Error: " +e.getMessage());
        }
        return isExists;
    }

    // Read teacher details from user input
    boolean readTeacher() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter Teacher id: ");
            teacherID = input.nextLine();
            if (isIdExists(teacherID)) {
                System.out.println("This id is already exist. Please change to another id.");
                return false;
            }
            System.out.print("Enter the Teacher name: ");
            teacherName = input.nextLine();
            System.out.print("Enter the gender: ");
            gender = input.nextLine();
            System.out.print("Enter the date of birth: ");
            dob = input.nextLine();
            System.out.print("Enter the phone number: ");
            phoneNo = input.nextLine();
            System.out.print("Enter the address: ");
            address = input.nextLine();
            accountId = null;
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Error: " +e.getMessage());
            return false;
        }
    }

    // Add a new teacher to the file
    void addTeacher() {
        try {
            if (readTeacher()) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Teacher.txt", true);
                fileWriter.write(teacherID + ", " + teacherName + ", " + gender + ", " + dob + ", " + phoneNo + ", " + address + ", " + accountId +"\n");
                System.out.println("Teacher added successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to add Teacher.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Search and display a teacher by ID
    void searchTeacher(String id) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Teacher.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(id)) {
                    System.out.printf("| %10s | %20s | %10s | %10s | %15s | %20s | %15s |\n", "TeacherID", "TeacherName", "Gender", "DOB", "PhoneNo", "Address", "AccountID");
                    System.out.printf("| %10s | %20s | %10s | %10s | %15s | %20s | %15s |%n", data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " +e.getMessage());
        }
    }

    // Delete a teacher by ID
    void deleteTeacher(String id) {
        try {
            File originalFile = new File("Text_file_data/Teacher.txt");
            File tempFile = new File("Temp/Teacher.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
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
    // Update a teacher by ID
    void updateTeacher(String id, String newTeacherName, String newGender, String newDOB, String newPhoneNo, String newAddress, String newAccountId) {
        try {
            File originalFile = new File("Text_file_data/Teacher.txt");
            File tempFile = new File("Temp/Teacher.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(id)) {
                    line = id + ", " + newTeacherName + ", " + newGender + ", " + newDOB + ", " + newPhoneNo + ", " + newAddress + ", " + newAccountId;
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
    // Method for display all course by teacher id
    void displayCoursesByTeacherId(String teacherId) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Course.txt"))) {
            String line;
            System.out.printf("| %10s | %20s |\n", "CourseID", "CourseName");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[4].equals(teacherId)) {
                        found = true;
                    System.out.printf("| %10s | %20s |%n", data[0], data[1]);
                }
            }
            reader.close();
            if (!found) {
                System.out.println("No courses found for the given teacher ID.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
class manageTeacher {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("", "", "", "", "", "", "","");
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        String id;
        try {
            while (!isExit) {
                System.out.println(" --".repeat(20));
                System.out.println("a. Add a new course");
                System.out.println("b. Search a teacher by id");
                System.out.println("c. Update a teacher by id");
                System.out.println("d. Delete a teacher by id");
                System.out.println("e. Display all courses taught by a teacher");
                System.out.println("f. Exit");
                System.out.println(" --".repeat(20));
                System.out.print("Enter your choice: ");
                char choice = input.next().toLowerCase().charAt(0);
                input.nextLine();
                switch (choice) {
                    case 'a' -> teacher.addTeacher();
                    case 'b' -> {
                        System.out.print("Enter id to search: ");
                        id = input.nextLine();
                        if (teacher.isIdExists(id)){
                            teacher.searchTeacher(id);
                        }else
                            System.out.println("Teacher not found .");

                    }
                    case 'c' -> {
                        System.out.print("Which course id you want to update? ");
                        id = input.nextLine();
                        if (teacher.isIdExists(id)) {
                            System.out.print("Enter the new Teacher name: ");
                            String teacherName = input.nextLine();
                            System.out.print("Enter the new gender: ");
                            String gender = input.nextLine();
                            System.out.print("Enter the new date of birth: ");
                            String dob = input.nextLine();
                            System.out.print("Enter the new phone number: ");
                            String phoneNo = input.nextLine();
                            System.out.print("Enter the new address: ");
                            String address = input.nextLine();
                            System.out.print("Enter the new account id: ");
                            String accountId = input.nextLine();
                            teacher.updateTeacher(id, teacherName, gender, dob, phoneNo ,address ,accountId);
                        } else {
                            System.out.println("Not found this id.");
                        }
                    }
                    case 'd' -> {
                        System.out.println("Enter id to delete: ");
                        id = input.nextLine();
                        if (teacher.isIdExists(id)){
                            teacher.deleteTeacher(id);
                        }else
                            System.out.println("Not found this id.");
                    }
                    case 'e'->{
                        System.out.print("Enter Teacher ID: ");
                        id = input.nextLine();
                        teacher.displayCoursesByTeacherId(id);
                    }
                    case 'f' -> {
                        System.out.println("Exit program.");
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
