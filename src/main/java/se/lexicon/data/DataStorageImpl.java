package se.lexicon.data;


import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Create implementations for all methods. I have already provided an implementation for the first method *
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private final List<Person> personList;

    private DataStorageImpl(){
        personList = PersonGenerator.getInstance().generate(1000);
    }

    static DataStorage getInstance(){
        return INSTANCE;
    }


    @Override
    public List<Person> findMany(Predicate<Person> filter) {
        List<Person> result = new ArrayList<>();
        for(Person person : personList){
            if(filter.test(person)){
                result.add(person);
            }
        }
        return result;
    }

    @Override
    public Person findOne(Predicate<Person> filter) {
        //List<Person> result = new ArrayList<>();
        for(Person person : personList){
            if(filter.test(person)){
                return person;
            }
        }
        return null;
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString){
        for(Person person : personList){
            if(filter.test(person)){
                return person.toString();
            }
        }
        return null;
    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString){
        List<String> toStr = new ArrayList<>();
        List<Person> pers = findMany(filter);

        for (Person person : pers) {
            if(filter.test(person)) {
                toStr.add(personToString.apply(person));
            }
        }

        return toStr;
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer){
        List<Person> find = findMany(filter);
        for (Person person : find) {
            consumer.accept(person);
        }

    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator){
        List<Person> fAndS = new ArrayList<>(personList);
        fAndS.sort(comparator);

        return fAndS;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator){
        List<Person> personList = findMany(filter);
        personList.sort(comparator);

        return personList;
    }
}
