package application.tests;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 28.12.2010
 * Time: 0:09:25
 * To change this template use File | Settings | File Templates.
 */
public class InheritanceTest {
    public static void main(String[] args) {
        Parent child = new Child();
        Receiver advancedReceiver = new AdvancedReceiver();
        advancedReceiver.receive(child);
    }
}

class Parent {
    public void outMe() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    public void outMe() {
        System.out.println("Child");
    }
}

class Receiver {
    public void receive(Parent p) {
        System.out.println("Receiver");
        p.outMe();
    }
}

class AdvancedReceiver extends Receiver{
    public void receive(Child c) {
        System.out.println("AdvancedReceiver");
        c.outMe();
    }
}