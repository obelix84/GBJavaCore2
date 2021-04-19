package ru.gb.java2;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	    doTask1();
        doTask2();
    }

    public static void doTask1() {
        String randomStr = "I tried so hard and got so far " +
                "But in the end it doesn't even matter " +
                "I had to fall to lose it all " +
                "But in the end it doesn't even matter";
        String [] words = randomStr.split("\s");
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (wordCount.containsKey(words[i])) {
                int value = wordCount.get(words[i]) + 1;
                wordCount.put(words[i], value);
            } else {
                wordCount.put(words[i], 1);
            }
        }
        System.out.println(wordCount);
    }

    public static void doTask2() {
        Phonebook pb = new Phonebook();
        pb.add("vasya1", "11");
        pb.add("vasya5", "22");
        pb.add("vasya3", "33");
        pb.add("vasya1", "44");
        pb.add("vasya1", "55");
        pb.add("vasya5", "88");
        pb.add("vasya6", "77");
        //тут можно было как-то покрасивше обработать
        System.out.println(pb.get("vasya1").toString());
        System.out.println(pb.get("vasya5").toString());

    }

}



