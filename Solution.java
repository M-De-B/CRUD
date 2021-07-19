package com.javarush.task.task17.task1711;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* 
CRUD 2
*/

public class Solution {
    public static volatile List<Person> allPeople = new ArrayList<Person>();

    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) throws ParseException {
        //start here - начни тут
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        switch (args[0]) {
            case "-c":
                synchronized (allPeople) {
                    int countPeoples = args.length - 1;
                    for (int i = 0; i < countPeoples; i += 3) {
                        if (args[2 + i].equals("м"))
                            allPeople.add(Person.createMale(args[1 + i], format.parse(args[3 + i])));
                        else
                            allPeople.add(Person.createFemale(args[1 + i], format.parse(args[3 + i])));
                        System.out.println(allPeople.size() - 1);
                    }
                }
                break;
            case "-i":
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i++) {
                        Person person = allPeople.get(Integer.parseInt(args[i]));
                        String sex = person.getSex().equals(Sex.MALE) ? "м" : "ж";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        System.out.println(String.format("%s %s %s", person.getName(), sex, simpleDateFormat.format(person.getBirthDate()).toString()));
                    }
                }
                break;

            case "-u":
                synchronized (allPeople) {
                    int countPeoples1 = args.length - 1;
                    for (int i = 0; i < countPeoples1; i += 4) {
                        Person upPerson = allPeople.get(Integer.parseInt(args[1 + i]));
                        upPerson.setName(args[2 + i]);
                        upPerson.setSex(args[3 + i].equals("м") ? Sex.MALE : Sex.FEMALE);
                        upPerson.setBirthDate(format.parse(args[4 + i]));
                    }
                }
                break;

            case "-d":
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i++) {
                        Person delPerson = allPeople.get(Integer.parseInt(args[i]));
                        delPerson.setBirthDate(null);
                        delPerson.setSex(null);
                        delPerson.setName(null);
                        allPeople.remove(args[i]);
                    }
                }
                break;
        }
    }
}

