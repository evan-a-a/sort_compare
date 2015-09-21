/*
 * Copyright 2015 Evan Anderson
 *
 * This file is part of sort_compare.
 *
 * sort_compare is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sort_compare is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with sort_compare.  If not, see <http://www.gnu.org/licenses/>.
 */

package us.thelinuxman.sortcompare;

/**
 *  Author: Evan Anderson
 *  Date: 9/20/2015
 *  Description: Main class to analyze sorting algorithm runtime
 */
public class Main {

    private static final int MAX_DATA_VALUE = 100000;
    private static final int DEFAULT_BUCKET_SIZE = 5;

    public static void main(String[] args) {
        if (args.length != 2) { // catch invalid args
            printHelp();
            System.exit(1);
        }

        //parse sort and data type
        String sortType = args[0].toUpperCase().substring(0, 1);
        String dataType = args[0].toUpperCase().substring(1);
        String sortLongName = "unknown";

        int length = Integer.parseInt(args[1]);
        long startTime; //start time in ms
        long runtime = 0; //runtime in ms

        int[] data = generateArray(dataType, length);

        if (data[0] == -1) { //catch invalid array type
            printHelp(); //provide guidance
            System.exit(1);
        }

        System.out.println("Sort start...");

        switch (sortType) {
            case "C": //counting sort
                sortLongName = "Counting sort";
                startTime = System.currentTimeMillis();
                data = Sorts.countingSort(data, MAX_DATA_VALUE);
                runtime = System.currentTimeMillis() - startTime;
                break;
            case "R": //radix sort
                sortLongName = "Radix sort";
                int max = findRange(data)[1];
                startTime = System.currentTimeMillis();
                data = Sorts.radixSort(data, max); //TODO: implement, fix digit values
                runtime = System.currentTimeMillis() - startTime;
                break;
            case "B": //bucket sort
                sortLongName = "Bucket sort";
                int[] range = findRange(data);
                startTime = System.currentTimeMillis();
                data = Sorts.bucketSort(data, range[1], range[0], DEFAULT_BUCKET_SIZE);
                runtime = System.currentTimeMillis() - startTime;
                break;
            case "I":
                sortLongName = "Insertion sort";
                startTime = System.currentTimeMillis();
                Sorts.insertionSort(data);
                runtime = System.currentTimeMillis() - startTime;
                break;
            case "Q":
                sortLongName = "Quick sort";
                startTime = System.currentTimeMillis();
                Sorts.quickSort(data);
                runtime = System.currentTimeMillis() - startTime;
                break;
            default:

        }

        //printSorted(data);
        if (sortLongName.equals("unknown")) {
            System.out.println("Invalid sort choice");
        } else {
            System.out.println(sortLongName + " finished in " + runtime / 1000 + " seconds (" + runtime + " ms).");
        }
    }

    private static int[] generateArray(String type, int length) {
        int[] data = new int[length];
        switch (type) {
            case "R":
                for (int i = 0; i < length; i++) {
                    data[i] = (int) (Math.random() * MAX_DATA_VALUE + 1);
                }
                break;
            case "S":
                for (int i = 0; i < length; i++) {
                    data[i] = i % MAX_DATA_VALUE;
                }
                break;
            case "E":
                for (int i = length - 1; i > 0; i--) {
                    data[i] = i % MAX_DATA_VALUE;
                }
                break;
            default:
                System.out.println("Invalid data array type");
                data[0] = -1;
                return data;
        }
        return data;
    }

    private static int[] findRange(int[] array) {
        int range[] = {array[0], array[0]};
        for (int i = 1; i < array.length; i++) {
            if (array[i] < range[0]) {
                range[0] = array[i];
            } else if (array[i] > range[1]) {
                range[1] = array[i];
            }
        }
        return range;
    }

    private static void printSorted(int[] array) {
        for (int value: array) {
            System.out.print(Integer.toString(value) + " ");
        }
        System.out.println();
    }

    private static void printHelp() {
        System.out.print("Usage information\n\nArguments:\n\tXY #\n\t\tX: valid sort code\n\t\tY: valid data " +
                "code\n\t\t#: array length to generate\n");
        System.out.print("Sorts:\n\t\tI - Insertion sort [O(n)]\n\t\tQ - Quick sort [O(n lg n)]\n\t\t" +
                "C - Counting sort [O(n)]\n\t\tR - Radix sort [O(n)]\n\t\tB - Bucket sort [O(n)]\n");
        System.out.print("Data:\n\t\tR: random data\n\t\tS: sorted data\n\t\tE: reverse sorted data\n");
    }

}
