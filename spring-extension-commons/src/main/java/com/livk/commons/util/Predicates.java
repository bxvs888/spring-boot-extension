/*
 * Copyright 2021 spring-boot-extension the original author or authors.
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
 *
 */

package com.livk.commons.util;

import org.springframework.util.Assert;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The type Predicates.
 *
 * @param <T> the type parameter
 * @author livk
 */
public class Predicates<T> {

	private final T[] dataArray;

	private Predicates(T[] dataArray) {
		Assert.notEmpty(dataArray, "dataArray must not be null or empty");
		this.dataArray = dataArray;
	}

	/**
	 * Create predicates.
	 *
	 * @param <T>       the type parameter
	 * @param dataArray the data array
	 * @return the predicates
	 */
	@SafeVarargs
	public static <T> Predicates<T> create(T... dataArray) {
		return new Predicates<>(dataArray);
	}

	/**
	 * All checked boolean.
	 *
	 * @param predicate the predicate
	 * @return the boolean
	 */
	public boolean allChecked(Predicate<T> predicate) {
		return Stream.of(dataArray).allMatch(predicate);
	}

	/**
	 * Any checked boolean.
	 *
	 * @param predicate the predicate
	 * @return the boolean
	 */
	public boolean anyChecked(Predicate<T> predicate) {
		return Stream.of(dataArray).anyMatch(predicate);
	}

	/**
	 * None match boolean.
	 *
	 * @param predicate the predicate
	 * @return the boolean
	 */
	public boolean noneMatch(Predicate<T> predicate) {
		return Stream.of(dataArray).noneMatch(predicate);
	}
}
