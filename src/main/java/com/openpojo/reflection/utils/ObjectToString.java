/*
 * Copyright (c) 2010-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.reflection.utils;

import java.util.Arrays;

/**
 * Builds the text representation of an object from its fields, handling arrays separately since they have no useful
 * {@code toString()}.
 *
 * @author oshoukry
 */
public class ObjectToString {

	/**
	 * Renders an object as text, unfolding arrays element by element.
	 *
	 * @param o
	 *     The object to render.
	 * @return its text representation.
	 */
	public static String toString(Object o) {
		return getHandler(o).toString(o);
	}

	private static ObjectToStringHandler getHandler(Object o) {
		if (o == null)
			return value -> null;

		if (!o.getClass().isArray())
			return Object::toString;

		// Array handling
		// Since Java has no way of auto-boxing an array of primitives, each must be examined independently.
		Class<?> componentType = o.getClass().getComponentType();

		if (componentType == byte.class)
			return value -> Arrays.toString(byte[].class.cast(value));

		if (componentType == char.class)
			return value -> Arrays.toString(char[].class.cast(value));

		if (componentType == short.class)
			return value -> Arrays.toString(short[].class.cast(value));

		if (componentType == int.class)
			return value -> Arrays.toString(int[].class.cast(value));

		if (componentType == long.class)
			return value -> Arrays.toString(long[].class.cast(value));

		if (componentType == float.class)
			return value -> Arrays.toString(float[].class.cast(value));

		if (componentType == double.class)
			return value -> Arrays.toString(double[].class.cast(value));

		if (componentType == boolean.class)
			return value -> Arrays.toString(boolean[].class.cast(value));

		return value -> Arrays.deepToString((Object[]) value);
	}

	@FunctionalInterface
	interface ObjectToStringHandler {
		String toString(Object o);
	}

	private ObjectToString() {
		throw new UnsupportedOperationException(ObjectToString.class.getName() + " should not be constructed!");
	}
}
