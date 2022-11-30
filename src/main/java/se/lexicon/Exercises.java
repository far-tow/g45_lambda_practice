package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);

        storage.findMany(person -> person.getFirstName().equalsIgnoreCase("Erik"))
                .forEach(System.out::println);
        //System.out.println(storage.findMany(person -> person.getFirstName().equalsIgnoreCase("Erik")));
        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        storage.findMany(person -> person.getGender().equals(Gender.FEMALE))
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);

        storage.findMany(person -> person.getBirthDate().isAfter(LocalDate.parse("2000-01-01")))
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        System.out.println(storage.findOne(person -> person.getId() == 123));
        System.out.println("----------------------");

    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
      /*  System.out.println(message);
        Person exc5 = storage.findOne(person -> person.getId() == 456);
        System.out.println(exc5.getFirstName() + " " + exc5.getLastName() + " born " + exc5.getBirthDate() + " id " + exc5.getId());
        System.out.println("----------------------");
    }*/
        Predicate<Person> findCondition = p -> p.getId() == 456;
        Function<Person, String> mapper = p -> " Name:  " +
                p.getFirstName() + " "
                + p.getLastName()
                + " born "
                + p.getBirthDate();

        String personResult = storage.findOneAndMapToString(findCondition, mapper);

        System.out.println(personResult);
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        Predicate<Person> findCondition = p -> p.getGender() == Gender.MALE && p.getFirstName().toLowerCase().startsWith("e");
        Function<Person, String> mapper = p -> p.toString();
        List<String> personResult = storage.findManyAndMapEachToString(findCondition, mapper);

        personResult.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        Function<Person, String> mapper = p ->
                p.getFirstName() + " " + p.getLastName() + " "
                        + (LocalDate.now().getYear() - p.getBirthDate().getYear()) + " years";
        Predicate<Person> pp = p -> LocalDate.now().getYear() - p.getBirthDate().getYear() < 10;

        List<String> result = storage.findManyAndMapEachToString(pp, mapper);
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        Predicate<Person> findCondition = p -> p.getFirstName().equalsIgnoreCase("ulf");
        Consumer<Person> printPeople = p -> System.out.println(p.toString());

        storage.findAndDo(findCondition, printPeople);

        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        Predicate<Person> findCondition = p -> p.getLastName().toLowerCase().contains(p.getFirstName().toLowerCase());
        Consumer<Person> printPeople = p -> System.out.println(p.toString());

        storage.findAndDo(findCondition, printPeople);
        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        Predicate<Person> namePalindrome = person -> new StringBuilder(person.getFirstName())
                .reverse().toString().equalsIgnoreCase(person.getFirstName());

        Consumer<Person> printer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());

        storage.findAndDo(namePalindrome, printer);
        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        System.out.println(message);
        Predicate<Person> findCondition = p -> p.getFirstName()
                .toLowerCase().startsWith("a");

        Comparator<Person> sortB = (o1, o2) -> o2.getBirthDate()
                .compareTo(o1.getBirthDate());
        List<Person> result = storage.findAndSort(findCondition, sortB);

        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        LocalDate date = LocalDate.parse("1950-01-01");
        Predicate<Person> findCondition = p -> p.getBirthDate().isBefore(date);
        Comparator<Person> sortP = (o1, o2) -> o2.getBirthDate()
                .compareTo(o1.getBirthDate());

        List<Person> result = storage.findAndSort(findCondition, sortP);
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        Comparator<Person> sortP = Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getBirthDate);

        List<Person> result = storage.findAndSort(sortP);
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }
}
