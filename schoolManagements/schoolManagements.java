package schoolManagements;

import java.util.InputMismatchException;
import java.util.Scanner;

class schoolManagements {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isCorrect = false;
        boolean isExit = false;
        try {
            while (!isCorrect) {
                if (logIN.isValidated()) {
                    System.out.println("Login successful!");
                    isCorrect = true;
                    while (!isExit) {
                        System.out.println("----------University Management System----------");
                        System.out.println("a. Manage Faculty");
                        System.out.println("b. Manage Department");
                        System.out.println("c. Manage Students");
                        System.out.println("d. Enroll Students into Department");
                        System.out.println("e. Manage Courses");
                        System.out.println("f. Manage Teachers");
                        System.out.println("g. Assign Courses to Teacher");
                        System.out.println("h. Create Teachers and Students Account");
                        System.out.println("i. Exit");
                        System.out.print("Enter your choice: ");
                        char choice = input.next().toLowerCase().charAt(0);
                        switch (choice) {
                            case 'a' -> manageFaculty.main(args);
                            case 'b' -> manageDepartment.main(args);
                            case 'c' -> manageStudent.main(args);
                            case 'd' -> Enroll_student_to_dep.main(args);
                            case 'e' -> manageCourse.main(args);
                            case 'f' -> manageTeacher.main(args);
                            case 'g' -> assignCourse.main(args);
                            case 'h' -> createAccount.main(args);
                            case 'i' -> {
                                System.out.println("Exit program");
                                isExit = true;
                            }
                            default -> System.out.println("Invalid choice");
                        }
                    }
                }
                else System.out.println("Login failed. Please check your username and password.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

