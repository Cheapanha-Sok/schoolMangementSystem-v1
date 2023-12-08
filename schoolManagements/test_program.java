package schoolManagements;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class assignCourse{
    // Method for assign course to teacher
    static void assignCourseToTeacher(String courseId, String teacherId) {
        try {
            boolean courseFound = false;
            boolean alreadyAssigned = false;
            File originalFile = new File("Text_file_data/Course.txt");
            File tempFile = new File("Temp/Course.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(courseId)) {
                    courseFound = true;
                    if (!parts[4].equals("0")) {
                        alreadyAssigned = true;
                        break;
                    }
                    parts[4] = teacherId;
                }
                writer.write(String.join(", ", parts) + "\n");
            }
            reader.close();
            writer.close();

            if (!courseFound) {
                System.out.println("Course ID not found in the record.");
            } else if (alreadyAssigned) {
                System.out.println("Course is already assigned to a teacher.");
            } else {
                originalFile.delete();
                tempFile.renameTo(originalFile);
                System.out.println("Assignment successful");

            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Method for remove course from teacher
    static void removeCourseFromTeacher(String courseId) {
        boolean courseFound = false;
        try {
            File originalFile = new File("Text_file_data/Course.txt");
            File tempFile = new File("Temp/Course.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(courseId)) {
                    courseFound = true;
                    parts[4] = "0"; // Set teacher ID to "0" to remove the assignment
                }
                writer.write(String.join(", ", parts) + "\n");
            }
            reader.close();
            writer.close();

            if (!courseFound) {
                System.out.println("Course ID not found in the record.");
            } else {
                originalFile.delete();
                tempFile.renameTo(originalFile);
                System.out.println("Teacher removed from the course successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Method for display all course by teacher id
    static void displayAllCourse(String teacherId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Course.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[4].equals(teacherId)) {
                    System.out.println(data[1]);
                    found = true;
                }
            }
            reader.close();
            if (!found) {
                System.out.println("No courses found for teacher ID: " + teacherId);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        try {
            while (!isExit) {
                System.out.println("--".repeat(20));
                System.out.println("a. Assign course to teacher");
                System.out.println("b. Remove course from teacher");
                System.out.println("c. Display course by teacher id");
                System.out.println("d. Exit");
                System.out.println("--".repeat(20));
                System.out.print("Enter your choice: ");
                char choice = input.next().charAt(0);
                input.nextLine();
                switch (choice) {
                    case 'a' -> {
                        System.out.print("Enter course id: ");
                        String id = input.nextLine();
                        System.out.print("Enter teacher id: ");
                        String teacherId = input.nextLine();
                        assignCourseToTeacher(id, teacherId);
                    }
                    case 'b' -> {
                        System.out.print("Enter course id: ");
                        String id = input.nextLine();
                        removeCourseFromTeacher(id);
                    }
                    case 'c' -> {
                        System.out.print("Enter the teacher id: ");
                        String id = input.nextLine();
                        displayAllCourse(id);
                    }
                    case 'd' -> {
                        System.out.println("Exit program.");
                        isExit = true;
                    }
                    default -> System.out.println("Invalid input. Try again!");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Error: " +e.getMessage());
        }
    }
}
