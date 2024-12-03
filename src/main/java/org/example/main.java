package org.example;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Введите количество элементов для добавления (целое число):");
        int numElements = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numElements; i++) {
            System.out.println("Введите ключ:");
            int key = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            int value = random.nextInt(100); // Generate random values
            map.put(key, value);
            System.out.println("Добавлен элемент: (" + key + ", " + value + ")");
            System.out.println("Текущий размер HashMap: " + map.size());
        }

        while (true) {
            System.out.println("\nВведите ключ для поиска (или 'q' для выхода):");
            String input = scanner.nextLine();
            if (input.equals("q")) break;
            try {
                int keyToFind = Integer.parseInt(input);
                Integer value = map.get(keyToFind);
                if (value != null) {
                    System.out.println("Значение для ключа '" + keyToFind + "': " + value);
                } else {
                    System.out.println("Ключ '" + keyToFind + "' не найден.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод.");
            }
        }

        System.out.println("\nВведите ключ для удаления:");
        int keyToRemove = Integer.parseInt(scanner.nextLine());
        Integer removedValue = map.remove(keyToRemove);
        if (removedValue != null) {
            System.out.println("Удалено значение: '" + keyToRemove + "': " + removedValue);
        } else {
            System.out.println("Ключ '" + keyToRemove + "' не найден.");
        }

        System.out.println("Текущий размер HashMap: " + map.size());
        System.out.println("Все элементы HashMap:");
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            System.out.println("Key: "+ entry.getKey() + ", Value: " + entry.getValue());
        }
        scanner.close();
    }
}
