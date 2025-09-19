
import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        String log = "";
        for (int i = 0; i < 1000; i += 1) {
            double numberOne = StdRandom.uniform();
            double numberZero = StdRandom.uniform();
            Integer x = i;
            if (numberZero < 0.5 && numberOne < 0.5) {
                studentArrayDeque.addFirst(x);
                arrayDequeSolution.addFirst(x);
                log = log + "addFirst(" + x + ")\n";
                assertEquals(log,studentArrayDeque.get(0),arrayDequeSolution.get(0));
                //callTrace.addLast("addFirst");
            }
            if (numberZero >= 0.5 && numberOne < 0.5) {
                studentArrayDeque.addLast(x);
                arrayDequeSolution.addLast(x);
                log = log + "addLast(" + x + ")\n";
                assertEquals(log,studentArrayDeque.get(studentArrayDeque.size() - 1),arrayDequeSolution.get(arrayDequeSolution.size() - 1));
                //callTrace.addLast("addLast");
            }
            if (numberZero < 0.5 && numberOne >= 0.5) {
                if (studentArrayDeque.isEmpty() || arrayDequeSolution.isEmpty()){continue;}
                Integer student = studentArrayDeque.removeFirst();
                Integer array = arrayDequeSolution.removeFirst();
                log = log + "removeFirst()\n";
                assertEquals(log,student,array);
            }
            if (numberZero >= 0.5 && numberOne >= 0.5) {
                if (studentArrayDeque.isEmpty() || arrayDequeSolution.isEmpty()){continue;}
                Integer student = studentArrayDeque.removeLast();
                Integer array = arrayDequeSolution.removeLast();
                log = log + "removeLast()\n";
                assertEquals(log,student,array);
            }

        }
    }
}
