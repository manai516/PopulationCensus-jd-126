import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        // Фильтр несовершеннолетних
        long minorsCont = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Несовершеннолетних: " + minorsCont);

        // Фильтр призывной возраст и мужчины а  так же фамилии получение списком
        List<String> conscripts = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .filter(person -> person.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Призывников: " + conscripts.size());

        // Фильтр работоспособных с высшим образованием сортированных по фамилиям
        List<Person> workable = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> (person.getSex() == Sex.WOMAN && person.getAge() <= 60)
                        || (person.getSex() == Sex.MAN && person.getAge() <= 65))
                .collect(Collectors.toList());
        System.out.println("Работоспособных с высшим образованием: " + workable.size());

        workable.stream().limit(5).forEach(System.out::println);
    }
}
