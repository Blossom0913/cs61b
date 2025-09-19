
import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        ArrayList<String> callTrace = new ArrayList<String>(); // function call stack
        for (int i = 0; i < 10; i += 1) {
            double numberOne = StdRandom.uniform();
            double numberZero = StdRandom.uniform();
            Integer x = i;
            if (numberZero < 0.5 && numberOne < 0.5) {
                studentArrayDeque.addFirst(x);
                arrayDequeSolution.addFirst(x);
                assertEquals("addFirst faild,student's: " + studentArrayDeque.get(0) +
                        " not equal to" + arrayDequeSolution.get(0) + "!\n",studentArrayDeque.get(0),arrayDequeSolution.get(0));
                callTrace.addLast("addFirst");
            }
            if (numberZero >= 0.5 && numberOne < 0.5) {
                studentArrayDeque.addLast(x);
                arrayDequeSolution.addLast(x);
                assertEquals("addLast faild,student's: " + studentArrayDeque.get(studentArrayDeque.size() - 1) +
                        " not equal to" + arrayDequeSolution.get(arrayDequeSolution.size() - 1) + "!\n",studentArrayDeque.get(studentArrayDeque.size() - 1),arrayDequeSolution.get(arrayDequeSolution.size() - 1));
                callTrace.addLast("addLast");
            }
            if (numberZero < 0.5 && numberOne >= 0.5) {
                if (studentArrayDeque.isEmpty() || arrayDequeSolution.isEmpty()){continue;}
                Integer student = studentArrayDeque.removeFirst();
                Integer array = arrayDequeSolution.removeFirst();
                assertEquals("removeFirst failed, student's: " + student + "not equal to: " + array + "!\n",student,array);
                callTrace.addLast("removeFirst");
            }
            if (numberZero >= 0.5 && numberOne >= 0.5) {
                if (studentArrayDeque.isEmpty() || arrayDequeSolution.isEmpty()){continue;}
                Integer student = studentArrayDeque.removeLast();
                Integer array = arrayDequeSolution.removeLast();
                assertEquals("removeLast failed, student's: " + student + "not equal to: " + array + "!\n",student,array);
                callTrace.addLast("removeLast");
            }

        }
    }
}
