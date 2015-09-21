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

import java.util.ArrayList;
import java.util.List;

/**
 *  Author: Evan Anderson
 *  Date: 9/20/2015
 *  Description: Implementation of several sorting algorithms
 */
public class Sorts {
    protected static int[] countingSort(int[] array, int max) {
        int[] sorted = new int[array.length];
        int[] working = new int[max + 1];
        for (int i = 0; i < max; i++) {
            working[i] = 0;
        }
        for (int i = 0; i < array.length; i++) {
            working[array[i]] += 1;
        }
        for (int i = 1; i <= max; i++) {
            working[i] += working[i - 1];
        }
        for (int i = array.length - 1; i >= 0; i--) {
            sorted[working[array[i]] - 1] = array[i];
            working[array[i]]--;
        }
        return sorted;
    }

    protected static int[] radixSort(int[] array, int max) {
        int place = 1;
        while(max / place > 0) {
            int[] sorted = new int[array.length];
            int[] working = new int[10];
            for (int i = 0; i < array.length; i++) {
                working[getDigit(array[i], place)] += 1;
            }
            for (int i = 1; i < working.length; i++) {
                working[i] += working[i - 1];
            }
            for (int i = array.length - 1; i >= 0; i--) {
                int digit = getDigit(array[i], place);
                sorted[working[digit] - 1] = array[i];
                working[digit]--;
            }
            array = sorted;
            place *= 10;
        }
        return array;
    }

    private static int getDigit(int num, int place) {
        return (num / place) % 10;
    }

    protected static int[] bucketSort(int[] array, int max, int min, int bucketSize) {
        int length = array.length;
        List<List<Integer>> buckets = new ArrayList<>();
        int bucketCount = (max - min) / bucketSize + 1;
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int i = 0; i < length; i++) {
            buckets.get((array[i] - min) / bucketSize).add(array[i]);
        }

        int arrayIndex = 0;
        for (int i = 0; i < buckets.size(); i++) {
            Integer[] sorted = buckets.get(i).toArray(new Integer[buckets.get(i).size()]);
            sorted = insertionSort(sorted);
            for (int j = 0; j < sorted.length; j++) {
                array[arrayIndex++] = sorted[j];
            }
        }

        return array;
    }

    //O(n^2) sort

    protected static int[] insertionSort(int[] array) {
        Integer[] newArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        insertionSort(newArray);
        int[] primitiveArray = new int[newArray.length];
        for (int i = 0; i < newArray.length; i++) {
            primitiveArray[i] = newArray[i];
        }
        return primitiveArray;
    }

    protected static Integer[] insertionSort(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
        return array;
    }

    //O(n lg n) sort
    protected static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    protected static void quickSort(int[] array, int p, int r) {
        if (p > r) return;
        int pivot = partition(array, p, r);
        quickSort(array, p, pivot - 1); //sort upper half
        quickSort(array, pivot + 1, r); //sort lower half
    }

    private static int partition(int[] array, int p, int r) {
        int end = array[r];
        int i = p - 1;
        for (int j = p; j < r - 1; j++) {
            if (array[j] <= end) {
                i = i + 1;
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
        int tmp = array[i + 1];
        array[i + 1] = array[r];
        array[r] = tmp;
        return i + 1;
    }

}
