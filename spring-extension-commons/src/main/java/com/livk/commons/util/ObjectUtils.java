/*
 * Copyright 2021 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.livk.commons.util;

import lombok.experimental.UtilityClass;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * <p>
 * StringUtils
 * </p>
 *
 * @author livk
 */
@UtilityClass
public class ObjectUtils extends org.springframework.util.ObjectUtils {

	/**
	 * All checked boolean.
	 *
	 * @param <T>       the type parameter
	 * @param predicate the predicate
	 * @param ts        the ts
	 * @return the boolean
	 * @see Predicates
	 */
	@Deprecated(since = "1.1.4", forRemoval = true)
	@SafeVarargs
	public <T> boolean allChecked(Predicate<T> predicate, T... ts) {
		return !ObjectUtils.isEmpty(ts) && Stream.of(ts).allMatch(predicate);
	}

	/**
	 * Any checked boolean.
	 *
	 * @param <T>       the type parameter
	 * @param predicate the predicate
	 * @param ts        the ts
	 * @return the boolean
	 * @see Predicates
	 */
	@Deprecated(since = "1.1.4", forRemoval = true)
	@SafeVarargs
	public <T> boolean anyChecked(Predicate<T> predicate, T... ts) {
		return !ObjectUtils.isEmpty(ts) && Stream.of(ts).anyMatch(predicate);
	}

}
