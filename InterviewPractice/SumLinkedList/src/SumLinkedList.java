import java.util.LinkedList;

public class SumLinkedList {
    public static void main(String[] args) {
        LinkedList<Integer> ls1 = new LinkedList<>();
        ls1.add(5);
        ls1.add(6);
        ls1.add(3);
        LinkedList<Integer> ls2 = new LinkedList<>();
        ls2.add(8);
        ls2.add(4);
        ls2.add(2);
        LinkedList<Integer> res = sumLinkedListRepr(ls1, ls2);
        LinkedList<Integer> soln = new LinkedList<>();
        soln.add(1);
        soln.add(4);
        soln.add(0);
        soln.add(5);
        assert soln.equals(res);
        LinkedList<Integer> ls3 = new LinkedList<>();
        ls3.add(1);
        ls3.add(7);
        res = sumLinkedListRepr(res, ls3);
        soln = new LinkedList<>();
        soln.add(1);
        soln.add(4);
        soln.add(2);
        soln.add(2);
        assert soln.equals(res);
        LinkedList<Integer> ls4 = new LinkedList<>();
        ls4.add(8);
        ls4.add(8);
        ls4.add(8);
        ls4.add(8);
        ls4.add(8);
        ls4.add(8);
        ls4.add(8);
        ls4.add(8);
        ls4.add(8);
        res = sumLinkedListRepr(ls4, res);
        soln = new LinkedList<>();
        soln.add(8);
        soln.add(8);
        soln.add(8);
        soln.add(8);
        soln.add(9);
        soln.add(0);
        soln.add(3);
        soln.add(1);
        soln.add(0);
        assert soln.equals(res);
    }

    private static LinkedList<Integer> addZerosToFront(LinkedList<Integer> ls, int numZeros) {
        assert numZeros > 0;

        while (numZeros > 0) {
            ls.addFirst(0);
            numZeros--;
        }

        return ls;
    }

    public static LinkedList<Integer> sumLinkedListRepr(LinkedList<Integer> ls1, LinkedList<Integer> ls2) {
        int diff = ls1.size() - ls2.size();
        if (diff > 0) {
            ls2 = addZerosToFront(ls2, diff);
        }
        else if (diff < 0) {
            ls1 = addZerosToFront(ls2, (-1) * diff);
        }

        return sumLinkedListReprHelper(new LinkedList<>(), ls1, ls2, ls1.size() - 1, 0);
    }

    private static LinkedList<Integer> sumLinkedListReprHelper(LinkedList<Integer> add_ls, LinkedList<Integer> ls1, LinkedList<Integer> ls2, int index, int carry) {
        if (index < 0) {
            if (carry > 0) {
                add_ls.addFirst(carry);
            }
            return add_ls;
        }

        int cur_value = ls1.get(index) + ls2.get(index) + carry;
        carry = cur_value / 10;
        add_ls.addFirst(cur_value % 10);

        return sumLinkedListReprHelper(add_ls, ls1, ls2, index - 1, carry);
    }
}
