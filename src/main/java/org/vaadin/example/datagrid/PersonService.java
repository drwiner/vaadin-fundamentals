package org.vaadin.example.datagrid;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class PersonService {

	private static String[] firstName = { "James", "John", "Robert", "Michael", "William", "David", "Richard",
			"Charles", "Joseph", "Christopher", "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer",
			"Maria", "Susan", "Margaret", "Dorothy", "Lisa" };

	private static String[] lastName = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
			"Moore", "Taylor", "Andreson", "Thomas", "Jackson", "White" };

	private static List<Person> persons;

	public Stream<Person> getPersons(int offset, int limit, AgeGroup filter) {
		ensureTestData();


		final Stream<Person> filtered = persons.stream().filter(p -> filter(p, filter)).skip(offset).limit(limit);

		return filtered;
	}

//	public Stream<Person> getPersons(int offset, int limit, AgeGroup filter, PersonDataProvider.SortingInfo sortingInfo) {
//		ensureTestData();
//
//
//		final Stream<Person> filtered = persons.stream().filter(p -> filter(p, filter)).skip(offset).limit(limit).sorted(sortingInfo);
//
//		return filtered;
//	}

	public Stream<Person> getPersons(AgeGroup filter){
		ensureTestData();
		return persons.stream().filter(p -> filter(p, filter));
	}

	public int totalSize(){
		ensureTestData();
		return persons.size();
	}

	public Collection<Person> findUsers(int start, int end){
		return persons.subList(start, end);
	}

	private void ensureTestData() {
		if (persons == null) {

			final Random r = new Random();

			persons = new ArrayList<Person>();
			for (int i = 0; i < 10000; i++) {
				final Person person = new Person();
				person.setName(firstName[r.nextInt(firstName.length)] + " " + lastName[r.nextInt(lastName.length)]);
				person.setAge(r.nextInt(50) + 18);
				person.setEmail(person.getName().replaceAll(" ", ".") + "@example.com");

				LocalDate date = LocalDate.now().minusYears(person.getAge());
				date = date.withMonth(r.nextInt(12) + 1);
				date = date.withDayOfMonth(r.nextInt(28) + 1);
				person.setBirthday(date);

				persons.add(person);
			}
		}
	}

	private boolean filter(Person p, AgeGroup filter) {
		if (filter == null) {
			return true;
		}

		final int age = p.getAge();
		return filter.getMinAge() <= age && filter.getMaxAge() >= age;
	}

	public int countPersons(int offset, int limit, AgeGroup filter) {
		ensureTestData();

		final long count = persons.stream().filter(p -> filter(p, filter)).skip(offset).limit(limit).count();

		return (int) count;
	}

	public List<Person> getAllPersons() {
		if (persons == null) {

			final Random r = new Random();

			persons = new ArrayList<Person>();
			for (int i = 0; i < 100; i++) {
				final Person person = new Person();
				person.setName(firstName[r.nextInt(firstName.length)] + " " + lastName[r.nextInt(lastName.length)]);
				person.setAge(r.nextInt(50) + 18);
				person.setEmail(person.getName().replaceAll(" ", ".") + "@example.com");

				LocalDate date = LocalDate.now().minusYears(person.getAge());
				date = date.withMonth(r.nextInt(12) + 1);
				date = date.withDayOfMonth(r.nextInt(28) + 1);
				person.setBirthday(date);

				persons.add(person);
			}
		}
		return persons;
	}

	public Stream<Person> getPersons(int offset, int limit, AgeGroup filter, List<PersonSort> sortOrders) {
		ensureTestData();
		if (sortOrders.isEmpty()){
			return getPersons(offset, limit, filter);
		}

		Comparator<Person> comparator = (o1,o2) -> 0;
		for (PersonSort psort: sortOrders){
			switch (psort.getPropertyName()) {
				case PersonSort.AGE_KEY:
					comparator = comparator.thenComparing(Person::getAge);
					break;
				// add more cases for each possible column
			}
			if (! psort.isDescending()) comparator = comparator.reversed();
		}

		List<Person> sortedList = new LinkedList<>(persons);
		sortedList.sort(comparator);

		final Stream<Person> filtered = sortedList.stream().filter(p -> filter(p, filter)).skip(offset).limit(limit);

		return filtered;
	}
}
