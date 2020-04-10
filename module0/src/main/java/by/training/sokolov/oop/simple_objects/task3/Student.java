package by.training.sokolov.oop.simple_objects.task3;

public class Student {

    private String surname;
    private String groupNumber;
    private int[] marks;

    public Student() {
        this.surname = StudentParametersGenerator.generateSurname();
        this.groupNumber = StudentParametersGenerator.generateGroupNumber();
        this.marks = StudentParametersGenerator.generateMarks();
    }

    public static void outputStudentInfo(Student student) {
        System.out.println("\n");
        System.out.println("Name:\t" + student.surname);
        System.out.println("Group:\t" + student.groupNumber);
        System.out.print("Marks:");
        for (int i = 0; i < student.marks.length; i++) {
            System.out.print(" " + student.marks[i]);
        }
    }


    public int[] getMarks() {
        return marks;
    }

}
