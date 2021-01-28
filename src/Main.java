import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {

    List<Integer> myList = Arrays.asList(-20,-1,0,1,2,3,4,5,20,30,40,45,50,60,100,1101,111203);
    List<String> firstNames = Arrays.asList("Seppo", "Matti", "Teppo", "Kalevi", "Olavi");
    List<String> lastNames  = Arrays.asList("Virtanen", "Järvinen", "Lampinen", "Jokela");

    List<Person> persons = new ArrayList<>();
    IntStream.range(40, 80).forEach((num) -> {
        Person p = new Person("X", "X", 1, num);
        persons.add(p);
      }
    );

    if(
    DoubleStream.iterate( Math.random(), (el) -> el*1.15 )
        .limit(10)
        .sorted()
        .mapToObj(String::valueOf)
        .peek(System.out::println)
        .allMatch( (doubleStr) -> doubleStr.contains("9") )
    ) {
      System.out.println("Whee!");
    }

    Set<Person> adults = persons.stream().filter((p) -> p.age > 18)
        .map((p) -> {
          p.fname = firstNames.get( (int) (Math.random()*10 % firstNames.size()) );
          p.lname = lastNames.get( (int) (Math.random()*10 % lastNames.size()) );
          return p;
        })
        .peek((p) -> {
          System.out.println(p.fname + " " + p.lname + " is older than 18! age: " + p.age);
        })
        .collect(Collectors.toSet());

    // Generate some kids for the adults
    List<Person> kids = adults.stream()
        .flatMap( (person) -> {
            return Stream.of(
                new Person("Children of " + person.fname, "", 0, (person.age % 4) + 6),
                new Person("Children of " + person.fname, "", 0, (person.age % 3) + 10 )
            );
        }).collect(Collectors.toList());

        kids.stream().forEach(System.out::println);


    final int SCHOOL_AGE = 7;
    boolean allSchoolAged = kids.stream().allMatch((k) -> k.age >= SCHOOL_AGE);
    System.out.println(  "Are they all aged >= " + SCHOOL_AGE + ": " + allSchoolAged );

    double avgAge = kids.stream().collect(Collectors.averagingDouble( (k) -> k.age )); // avg by age
    System.out.println( "Kids avg. age is: " + avgAge );





    List<Double> result = myList.stream()
        .filter( n -> n > myList.get(5))
        .map(n -> Math.pow(n, 2) )
        .collect(Collectors.toList());

    System.out.println("#1 " + result.toString());
    System.out.println("#2 " + adults.toString());


  }


  public static class Person {
    String fname;
    String lname;
    int sex; // 0 unknown 1 male 2 female
    int age;
    public Person(String fname, String lname, int sex, int age) { this.fname = fname; this.lname = lname; this.sex = sex; this.age = age; }

    @Override
    public String toString() {
      return String.format("(Person %s %s - age: %d)", fname, lname, age);
    }
  }

}
