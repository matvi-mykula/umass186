package log;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

//SELF ASSESSMENT 4 

public class assessment4 implements Comparator<Integer> {

    // Write a class DescendingIntegerComparator that implements
    // Comparator<Integer>. If it were used as the argument to List.sort(),
    // it would sort the list in descending order. Note that this question is
    // not asking you to sort a list – just to write the comparator!

    // For example, if the unsorted list were [2, 1, 4, 3], then the list would be
    // [4, 3, 2, 1] after sorting using this comparator.
    @Override
    public int compare(Integer num1, Integer num2) {
        // Compare the integers in reverse order to achieve descending order sorting
        return num2.compareTo(num1);
    }

    public static void main(String[] args) {
        // Test case 1: Sorting a list in descending order
        List<Integer> numbers = new ArrayList<>(Arrays.asList(2, 1, 4, 3));
        Comparator<Integer> comparator = new assessment4();
        numbers.sort(comparator);
        System.out.println(numbers); // Expected output: [4, 3, 2, 1]

        // Test case 2: Sorting an already sorted list in descending order
        List<Integer> sortedNumbers = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
        sortedNumbers.sort(comparator);
        System.out.println(sortedNumbers); // Expected output: [5, 4, 3, 2, 1]

        // Test case 3: Sorting a list with duplicate values in descending order
        List<Integer> duplicates = new ArrayList<>(Arrays.asList(3, 1, 4, 2, 4, 1, 3));
        duplicates.sort(comparator);
        System.out.println(duplicates); // Expected output: [4, 4, 3, 3, 2, 1, 1]
    }

}
