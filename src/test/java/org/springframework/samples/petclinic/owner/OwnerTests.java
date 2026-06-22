/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the {@link Owner#getPet} lookup methods.
 */
class OwnerTests {

	private Pet namedPet(String name, Integer id) {
		Pet pet = new Pet();
		pet.setName(name);
		pet.setId(id);
		return pet;
	}

	@Test
	void getPetShouldFindByNameCaseInsensitively() {
		Owner owner = new Owner();
		Pet leo = namedPet("Leo", 1);
		owner.getPets().add(leo);

		assertThat(owner.getPet("leo")).isSameAs(leo);
		assertThat(owner.getPet("LEO")).isSameAs(leo);
	}

	@Test
	void getPetShouldReturnNullWhenNotFound() {
		Owner owner = new Owner();
		owner.getPets().add(namedPet("Leo", 1));

		assertThat(owner.getPet("Max")).isNull();
	}

	@Test
	void getPetShouldIgnoreNewPetsWhenIgnoreNewIsTrue() {
		Owner owner = new Owner();
		Pet newPet = namedPet("Basil", null);
		owner.addPet(newPet);

		assertThat(newPet.isNew()).isTrue();
		assertThat(owner.getPet("Basil", true)).isNull();
		assertThat(owner.getPet("Basil", false)).isSameAs(newPet);
		assertThat(owner.getPet("Basil")).isSameAs(newPet);
	}

	@Test
	void getPetShouldReturnSavedPetWhenIgnoreNewIsTrue() {
		Owner owner = new Owner();
		Pet savedPet = namedPet("Iggy", 7);
		owner.getPets().add(savedPet);

		assertThat(savedPet.isNew()).isFalse();
		assertThat(owner.getPet("Iggy", true)).isSameAs(savedPet);
	}

}
