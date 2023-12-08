package schoolManagements;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Course {
    // Declare 6 private instance variable
    private String courseId;
    private String courseName;
    private String credit;
    private String courseType;
    private String teacherId;
    private String departmentId;

    // Contractor with 6 parameter
    public Course(String courseId, String courseName, String credit, String courseType, String teacherId , String departmentId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.courseType = courseType;
        this.teacherId = teacherId;
        this.departmentId = departmentId;
    }
    // Check if a course with the given ID already exists
    boolean isIdExists(String courseID) {
        boolean isExists = false;
        try {
        BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Course.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(courseID)) {
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
    // Read course details from user input
    boolean readCourse() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter course id: ");
            courseId = input.nextLine();
            System.out.print("Enter the course name: ");
            courseName = input.nextLine();
            System.out.print("Enter the credit: ");
            credit = input.nextLine();
            System.out.print("Enter the course type: ");
            courseType = input.nextLine();
            System.out.print("Enter the department id: ");
            departmentId = input.nextLine();
            teacherId=null;
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Error :" +e.getMessage());
            return false;
        }
    }
    // Add a new course to the file
    void addCourse() {
        try {
            if (readCourse()) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Course.txt", true);
                fileWriter.write(courseId + ", " + courseName + ", " + credit + ", " + courseType + ", " + teacherId + ", " + departmentId + "\n");
                System.out.println("Course added successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to add course.");
            }
        } catch (IOException e) {
            System.out.println("Error :" +e.getMessage());
        }
    }

    // Search and display a course by ID
    void searchCourse(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Course.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(id)) {
                    System.out.printf("| %10s | %20s | %10s | %10s | %10s | %10s |\n", "CourseID", "CourseName", "Credit", "Type" ,"Teacher_id","Department_id");
                    System.out.printf("| %10s | %20s | %10s | %10s | %10s | %10s |%n", data[0], data[1], data[2], data[3] ,data[4] ,data[5]);
                    found = true;
                    break;
                }
            }
            reader.close();
            if (!found) {
                System.out.println("Course not found.");
            }
        } catch (IOException e) {
            System.out.println("Error :" +e.getMessage());
        }
    }

    // Delete a course by ID
    void deleteCourse(String id) {
        try {
            File originalFile = new File("Text_file_data/Course.txt");
            File tempFile = new File("Temp/Course.txt");
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
    // Update a course by ID
    void updateCourse(String id, String newCourseName, String newCredit, String newCourseType, String newTeacherId ,String newDepartmentId) {
        try {
            File originalFile = new File("Text_file_data/Course.txt");
            File tempFile = new File("Temp/Course.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(id)) {
                    line = id + ", " + newCourseName + ", " + newCredit + ", " + newCourseType + ", " + newTeacherId + ", " +newDepartmentId;
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

class manageCourse {
    public static void main(String[] args) {
        Course course = new Course("", "", "", "", "","");
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        String id;
        try {
            while (!isExit) {
                System.out.println(" --".repeat(20));
                System.out.println("a. Add a new course");
                System.out.println("b. Search a course by id");
                System.out.println("c. Update a course by id");
                System.out.println("d. Delete a course by id");
                System.out.println("e. Exit");
                System.out.println(" --".repeat(20));
                System.out.print("Enter your choice: ");
                char choice = input.next().charAt(0);
                input.nextLine();
                switch (choice) {
                    case 'a' -> course.addCourse();
                    case 'b' -> {
                        System.out.print("Enter id to search: ");
                        id = input.nextLine();
                        course.searchCourse(id);
                    }
                    case 'c' -> {
                        System.out.print("Which course id you want to update? ");
                        id = input.nextLine();
                        if (course.isIdExists(id)) {
                            System.out.print("Enter new course name: ");
                            String courseName = input.nextLine();
                            System.out.print("Enter new credit: ");
                            String credit = input.nextLine();
                            System.out.print("Enter new course type: ");
                            String courseType = input.nextLine();
                            System.out.println("Enter new teacher id: ");
                            String teacherId = input.nextLine();
                            System.out.println("Enter new department id: ");
                            String departmentId = input.nextLine();
                            course.updateCourse(id, courseName, credit, courseType, teacherId ,departmentId);
                        } else {
                            System.out.println("Not found this id.");
                        }
                    }
                    case 'd' -> {
                        System.out.println("Enter id to delete: ");
                        id = input.nextLine();
                        course.deleteCourse(id);
                    }
                    case 'e' -> {
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
