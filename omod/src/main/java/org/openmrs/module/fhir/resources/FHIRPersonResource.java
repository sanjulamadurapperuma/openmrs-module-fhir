/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.fhir.resources;

import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.fhir.api.PersonService;

import java.util.List;

public class FHIRPersonResource extends Resource {

	public Person getByUniqueId(IdType id) {
		PersonService personService = Context.getService(PersonService.class);
		Person fhirPerson = personService.getPerson(id.getIdPart());
		if (fhirPerson == null) {
			throw new ResourceNotFoundException("Person is not found for the given Id " + id.getIdPart());
		}
		return fhirPerson;
	}

	public List<Person> searchByUniqueId(TokenParam id) {
		PersonService personService = Context.getService(PersonService.class);
		return personService.searchPersonByUuid(id.getValue());
	}

	public List<Person> searchPersons(String name, Integer birthYear, StringParam gender) {
		PersonService personService = Context.getService(PersonService.class);
		return personService.searchPersons(name, birthYear, gender.getValue());
	}

	public List<Person> searchByName(StringParam name) {
		PersonService personService = Context.getService(PersonService.class);
		return personService.searchPersonsByName(name.getValue());
	}

	public Person createFHIRPerson(Person person) {
		PersonService personService = Context.getService(PersonService.class);
		return personService.createFHIRPerson(person);
	}

	public Person updateFHIRPerson(Person person, String theId) {
		PersonService personService = Context.getService(PersonService.class);
		return personService.updateFHIRPerson(person, theId);
	}

	/**
	 * Retire person by unique id
	 *
	 * @param theId object containing the id
	 * @throws ResourceNotFoundException when no person with given id is found
	 */
	public void deletePerson(IdType theId) throws ResourceNotFoundException {
		PersonService personService = Context.getService(PersonService.class);
		personService.retirePerson(theId.getIdPart());
	}
}
