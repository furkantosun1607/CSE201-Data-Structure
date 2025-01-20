import java.util.Date;

public class Lab01_20220808025 {
    public static void main(String[] args) {
        String asd="furkantosun";
        asd=asd.substring(0,1).toUpperCase()+asd.substring(1).toLowerCase();
        System.out.println(asd);


    }
}
class Circle2 {
    static int count=0;
    int radius = 1;
    color color = new color();
    Date dateOfcreation;
    Circle2(int radius, String color){
        this(radius);
        this.radius = radius;
        this.color.name=color;
        count++;
    }
    Circle2(int radius){

    }


    public String toString(){
        return  String.format("Circle with %d radius"+"with %s color",radius,color);
    }
    double GetArea(){
        return Math.PI*radius*radius;


    }
    static void  display(Circle2[] circles){
        for (Circle2 c:circles) {
            System.out.println(c);

        }
    }
    public int getRadius(){

        System.out.println("ACCESSSED");
        return radius;
    }




}


class color{
    String name="Blue";
}

class Employee{
    static int[] employees = new int[8];
    static int count;
    private int id = -1;
    static String[] Employee = new String[8];
    private String name = "Default Employee";
    private  double salary =23217.53;
    private int level=0;

    Employee(String name, double salary, int level){
        this.name=name;
        this.salary=salary;
        this.level=level;
    }

    public double getSalary() {
        return salary;
    }

    public static int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

}
