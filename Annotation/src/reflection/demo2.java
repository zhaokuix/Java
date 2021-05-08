package reflection;

/**
 * Class类对象的获取方式
 */
public class demo2 {
    public static void main(String[] args) throws ClassNotFoundException {
        Student student = new Student("student");
        //通过类直接获取Class对象
        Class<Student> c1 = Student.class;

        //通过forName方法获取
        Class<?> c2 = Class.forName("reflection.Student");

        //通过对象获取
        Class<? extends Student> c3 = student.getClass();

        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());

        //jdk的包装类可以通过TYPE获取
        Class<Integer> type = Integer.TYPE;
        System.out.println(type);

        //获取父级Class对象
        Class<?> p1 = c1.getSuperclass();

    }
}

class Person{
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Student extends Person{

    public Student(String name) {
        super(name);
    }
}
class Teacher extends Person{
    public Teacher(String name) {
        super(name);
    }
}
