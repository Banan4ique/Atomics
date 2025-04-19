package ru.netology;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadPalindrome = getPalindromeThread(texts);
        Thread threadOneLetter = getOneLetterThread(texts);
        Thread threadOrder = getOrderedThread(texts);

        threadPalindrome.start();
        threadOneLetter.start();
        threadOrder.start();

        threadOrder.join();
        threadOneLetter.join();
        threadPalindrome.join();

        System.out.println("Красивых слов с длиной 3: " + count3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static Thread getPalindromeThread(String[] texts) {
        return new Thread(() -> {
            for (String text : texts) {
                if (text.length() >= 3 && text.length() <= 5) {
                    StringBuilder sb = new StringBuilder(text);
                    if (text.equals(sb.reverse().toString()) && !text.chars().allMatch(x -> x == text.charAt(0))) {
                        switch (text.length()) {
                            case 3:
                                count3.getAndIncrement();
                                break;
                            case 4:
                                count4.getAndIncrement();
                                break;
                            case 5:
                                count5.getAndIncrement();
                                break;
                        }
                    }
                }
            }
        });
    }

    public static Thread getOneLetterThread(String[] texts) {
        return new Thread(() -> {
            for (String text : texts) {
                if (text.length() >= 3 && text.length() <= 5) {
                    boolean oneLetter = text.chars().allMatch(x -> x == text.charAt(0));
                    if (oneLetter) {
                        switch (text.length()) {
                            case 3:
                                count3.getAndIncrement();
                                break;
                            case 4:
                                count4.getAndIncrement();
                                break;
                            case 5:
                                count5.getAndIncrement();
                                break;
                        }
                    }
                }
            }
        });
    }

    public static Thread getOrderedThread(String[] texts) {
        return new Thread(() -> {
            for (String text : texts) {
                if (text.length() >= 3 && text.length() <= 5) {
                    char[] charArray = text.toCharArray();
                    Arrays.sort(charArray);
                    String sorted = new String(charArray);
                    if (text.equals(sorted)) {
                        switch (text.length()) {
                            case 3:
                                count3.getAndIncrement();
                                break;
                            case 4:
                                count4.getAndIncrement();
                                break;
                            case 5:
                                count5.getAndIncrement();
                                break;
                        }
                    }
                }
            }
        });
    }
}