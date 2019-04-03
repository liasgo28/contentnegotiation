package br.com.diego.contentnegotiation.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.diego.contentnegotiation.model.Person;

@Repository
public class PersonRepository {
	
	public final List<Person> getAllPersons() {
		List<Person> persons= new ArrayList<Person>();		
		persons.add(new Person(1L,"Name One", "teste@teste.com.br"));
		persons.add(new Person(2L,"Name Two", "teste2@teste.com.br"));
		
		return persons;
	}
	
}
