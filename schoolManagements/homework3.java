package schoolManagements;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student{
    // Declare 11 private instance variable
    private String studentID;
    private String studentName;
    private String gender;
    private String dob;
    private String phoneNo;
    private String address;
    private String year;
    private String generation;
    private String degree;
    private String accountId;

    // Contractor with 11 parameter
    Student(String studentID , String studentName , String gender , String dob ,
            String phoneNo , String address , String year ,String generation
            , String degree ,String accountId ){
        this.studentID = studentID;
        this.studentName= studentName;
        this.gender = gender;
        this.dob= dob;
        this.phoneNo = phoneNo;
        this.address = address;
        this.year= year;
        this.generation = generation;
        this.degree= degree;
        this.accountId = accountId;

    }
    // method for check id exist in text file or not
    boolean idIsExist(String id) {
        boolean isExists = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Student.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(id)) {
                    isExists = true;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error :" +e.getMessage());
        }
        return isExists;
    }
    // method for read student by user input
    boolean readStudent() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter student id: ");
            studentID = input.nextLine();
            if (idIsExist(studentID)) {
                System.out.println("This id is already exist. Please change to another id.");
                return false;
            }
            System.out.print("Enter the student name: ");
            studentName = input.nextLine();
            System.out.print("Enter the gender: ");
            gender = input.nextLine();
            System.out.print("Enter the date of birth: ");
            dob = input.nextLine();
            System.out.print("Enter the phone number: ");
            phoneNo = input.nextLine();
            System.out.print("Enter the address: ");
            address= input.nextLine();
            System.out.print("Enter the student year :");
            year = input.nextLine();
            System.out.print("Enter the generation: ");
            generation = input.nextLine();
            System.out.print("Enter the degree: ");
            degree = input.nextLine();
            accountId = null;
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Error :" +e.getMessage());
            return false;
        }
    }
    // method for add student into text file
    void addStudent() {
        try {
            if (readStudent()) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Student.txt", true);
                fileWriter.write(studentID + ", " + studentName + ", " + gender + ", " +
                        dob + ", "+ phoneNo+ ", "+ address + ", "+year+", "+ generation + ", "+ degree + ", " +accountId+"\n");
                System.out.println("Student added successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (IOException e) {
            System.out.println("Error :" +e.getMessage());
        }
    }
    // method for search student by id
    void searchStudent(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Student.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(id)) {
                    System.out.printf("| %10s | %20s | %10s | %10s | %15s |\n", "studentID", "studentName", "gender", "dob", "phoneNo");
                    System.out.printf("| %10s | %20s | %10s | %10s | %15s |%n", id, data[1], data[2], data[3], data[4]);
                    found = true;
                    break;
                }
            }
            reader.close();
            if (!found) {
                System.out.println("Student not found.");
            }
        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }
    }
    // method for delete student by id
    void deleteStudent(String id) {
        try  {
            File originalFile = new File("Text_file_data/Student.txt");
            File tempFile = new File("Temp/Student.txt");
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
    // method for update student by id
    void updateStudent(String id, String newStudentName, String newGender, String newDob ,String newPhoneNo , String newAddress , String newYear ,
                       String newGeneration , String newDegree ,String newAccountId) {
        try {
            File originalFile = new File("Text_file_data/Student.txt");
            File tempFile = new File("Temp/Student.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    line = id + ","+ newStudentName + "," + newGender + "," + newDob + ", " +newPhoneNo + ", " +newAddress
                            + ", " +newYear + ", " + newGeneration + ", " + newDegree + ", " +newAccountId;
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
class manageStudent {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Student student = new Student("","", "", "", "", "", "", "", "", "");
        boolean isExit = false;
        String id;
        try {
            while (!isExit) {
                System.out.println(" --".repeat(20));
                System.out.println("a. Add a new student");
                System.out.println("b. Search a student by id");
                System.out.println("c. Update a student");
                System.out.println("d. Delete a student by id");
                System.out.println("e. Exit");
                System.out.println(" --".repeat(20));
                System.out.print("Enter your choice :");
                char choice = input.next().toLowerCase().charAt(0);
                input.nextLine();
                switch (choice) {
                    case 'a' -> student.addStudent();
                    case 'b' -> {
                        System.out.print("Enter id to search :");
                        id = input.nextLine();
                        student.searchStudent(id);
                    }
                    case 'c' -> {
                        System.out.print("Which student id you want to update? ");
                        id = input.nextLine();
                        if (student.idIsExist(id)){
                            System.out.print("Enter new student name: ");
                            String studentName = input.nextLine();
                            System.out.print("Enter new gender: ");
                            String gender = input.nextLine();
                            System.out.print("Enter new date of birth: ");
                            String dob = input.nextLine();
                            System.out.println("Enter new phone number: ");
                            String phoneNo = input.nextLine();
                            System.out.print("Enter new address: ");
                            String address = input.nextLine();
                            System.out.print("Enter new student year :");
                            String year = input.nextLine();
                            System.out.print("Enter new generation: ");
                            String generation = input.nextLine();
                            System.out.print("Enter new degree: ");
                            String degree = input.nextLine();
                            System.out.print("Enter new account id: ");
                            String accountId = input.nextLine();
                            student.updateStudent(id,studentName,gender,dob,phoneNo,address,year,generation,degree,accountId);
                        }else
                            System.out.println("Not found this id.");
                    }
                case 'd' -> {
                    System.out.println("Enter id to delete :");
                    id = input.nextLine();
                    student.deleteStudent(id);
                }
                case 'e' -> {
                    System.out.println("Exit program .");
                    isExit = true;
                }
                default -> System.out.println("Invalid input .Try again !");
            }
        }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input .Try again !");
        }
    }
}
