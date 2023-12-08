package schoolManagements;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
class Department{
    // Declare 5 instance private variable
    private String deptId;
    private String deptName;
    private String headName;
    private String officeNo;
    private String faculty_id;
    // contractor with 5 parameter
    Department(String deptId , String deptName , String headName , String officeNo ,String faculty_id){
        this.deptId=deptId;
        this.deptName=deptName;
        this.headName = headName;
        this.officeNo=officeNo;
        this.faculty_id= faculty_id;
    }
    // method for check the id is exit in text file or not
    boolean idIsExist(String id) {
        boolean isExists = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Department.txt"));
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
            System.out.println("Error :" + e.getMessage());
        }
        return isExists;
    }
    // method for read department by user input
    boolean readDepartment() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter department id: ");
            deptId = input.nextLine();
            if (idIsExist(deptId)) {
                System.out.println("This id is already exist. Please change to another id.");
                return false;
            }
            System.out.print("Enter the department name: ");
            deptName = input.nextLine();
            System.out.print("Enter the head name: ");
            headName = input.nextLine();
            System.out.print("Enter the office number: ");
            officeNo = input.nextLine();
            System.out.println("Enter the faculty id");
            faculty_id = input.nextLine();
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Error :" + e.getMessage());
            return false;
        }
    }
    // method for add department into text file
    void addDepartment() {
        try {
            if (readDepartment()) {
                FileWriter fileWriter = new FileWriter("Text_file_data/Department.txt", true);
                fileWriter.write(deptId + ", " + deptName + ", " + headName + ", " + officeNo + ", "+ faculty_id+"\n");
                System.out.println("Department added successfully.");
                fileWriter.close();
            } else {
                System.out.println("Failed to add department.");
            }
        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }
    }
    // method for search department by id
    void searchDepartment(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Department.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(id)) {
                    System.out.printf("| %10s | %20s | %10s | %10s |\n", "departmentID", "headName", "officeNo" ,"facultyId");
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
    // method for delete department by id
    void deleteDepartment(String id) {
        try {
            File tempFile = new File("Temp/Department.txt");
            File originalFile = new File("Text_file_data/Department.txt");
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
            writer.close();
            reader.close();

            originalFile.delete();

            tempFile.renameTo(originalFile);
            System.out.println("Remove successful");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // method for update department by id
    void updateDepartment(String id, String newFacultyName, String newDeanName, String newOffice ,String faculty_id) {
        try {
            File tempFile = new File("Temp/Department.txt");
            File originalFile = new File("Text_file_data/Department.txt");
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    line = id + ", " + "Faculty of " + newFacultyName + ", " + newDeanName + ", " + newOffice + ", " +faculty_id ;
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
    // method for display all department belong to faculty
    public void displayAllDepartment(String idToDisplay) {
        boolean isFound = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text_file_data/Department.txt"));
            System.out.printf("| %-15s |%-15s |\n", "Department_id","Department_name");
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(", ");
                if (data[data.length-1].equals(idToDisplay)) {
                    System.out.printf("| %-15s |%-15s |\n",data[0], data[2]);
                    isFound = true;
                }
            }
            reader.close();
            if (!isFound){
                System.out.println("Not found this id!");
            }
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
    }
}

class manageDepartment{
    public static void main(String[] args) {
        Department department = new Department("","","","","");
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        String id;
        try {
            while (!isExit) {
                System.out.println(" --".repeat(20));
                System.out.println("a. Add a new department");
                System.out.println("b. Search a department by id");
                System.out.println("c. Update a department");
                System.out.println("d. Delete a department by id");
                System.out.println("e. Display all department belong to faculty");
                System.out.println("f. Exit");
                System.out.println(" --".repeat(20));
                System.out.print("Enter your choice :");
                char choice = input.next().charAt(0);
                input.nextLine();
                switch (choice) {
                    case 'a' -> department.addDepartment();
                    case 'b' -> {
                        System.out.print("Enter id to search :");
                        id = input.nextLine();
                        department.searchDepartment(id);
                    }
                    case 'c' -> {
                        System.out.print("Which department id you want to update? ");
                        id = input.nextLine();
                        if (department.idIsExist(id)) {
                            System.out.print("Enter new department name: ");
                            String faculty_name = input.nextLine();
                            System.out.print("Enter new head name: ");
                            String dean_name = input.nextLine();
                            System.out.print("Enter new office number: ");
                            String office_no = input.nextLine();
                            System.out.print("Enter new faculty id: ");
                            String faculty_id = input.nextLine();
                            department.updateDepartment(id, faculty_name, dean_name, office_no ,faculty_id);
                        } else {
                            System.out.println("Not found this id.");
                        }
                    }
                    case 'd' -> {
                        System.out.print("Enter id to delete :");
                        id = input.nextLine();
                        department.deleteDepartment(id);
                    }
                    case 'e' -> {
                        System.out.print("Enter id of faculty :");
                        String faculty_name = input.nextLine();
                        department.displayAllDepartment(faculty_name);
                    }
                    case 'f' -> {
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
