package by.training.sokolov.oop.simple_objects.task3;

public class Test {
    public static void main(String[] args) {
        task3();
    }

    static void task3() {
        System.out.println("3. Создайте класс с именем Student, содержащий поля: \n" +
                "- фамилия и инициалы, \n" +
                "- номер группы, \n" +
                "- успеваемость (массив из пяти элементов). \n" +
                "Создайте массив из десяти элементов такого типа. \n" +
                "1) Добавьте возможность вывода фамилий и номеров групп студентов, \n" +
                "имеющих оценки, равные только 9 или 10.\n");

        Student[] students = createStudentsArray();

        outputExcellentStudents(students);
    }

    private static Student[] createStudentsArray() {
        Student[] students = new Student[5];

        for (int i = 0; i < students.length; i++) {
            Student newStudent = new Student();
            students[i] = newStudent;
        }
        return students;
    }

    public static void outputExcellentStudents(Student[] students) {
        boolean excellent = true;

        for (Student student : students) {
            if (isExcellentMarks(student)) {
                Student.outputStudentInfo(student);
            }
        }
    }

    private static boolean isExcellentMarks(Student student) {

        for (int mark : student.getMarks()) {
            if (mark < 9) {
                return false;
            }
        }
        return true;
    }
}
