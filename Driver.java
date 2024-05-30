import java.io.File;

public class Driver {
public static void main(String [] args) {

    File myfile = new File("equation.txt");
    
    Polynomial q = new Polynomial();
    Polynomial p = new Polynomial(myfile);


    System.out.println("No args: " + q.evaluate(5));
    System.out.println("Text file equation: " + p.evaluate(3));

    double [] c1 = {6,5};
    int[] e1 = {0,3};


    Polynomial p1 = new Polynomial(c1, e1);
    double [] c2 = {-2,-9};
    int[] e2 = {1,4};
    Polynomial p2 = new Polynomial(c2, e2);
    Polynomial m = p1.multiply(p2);
    Polynomial s = p1.add(p2);

    System.out.println("s(add: 0.1) = " + s.evaluate(0.1) + "\nWriting multiple in written.txt");

    m.saveToFile("written.txt");



    if(s.hasRoot(1))
    System.out.println("1 is a root of s");
    else
    System.out.println("1 is not a root of s");
}

}