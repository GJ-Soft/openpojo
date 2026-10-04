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

package com.openpojo.reflection.java.bytecode.asm.method;

import java.util.HashMap;
import java.util.Map;

import com.openpojo.reflection.java.bytecode.asm.method.impl.*;

/**
 * Picks the right {@code MethodHandler} according to the return type of the method to implement.
 *
 * @author oshoukry
 */
public class MethodHandlerFactory {

	private final Map<String, MethodHandler> signatureMethodHandlers = new HashMap<>();
	private final Map<String, MethodHandler> methodNameHandlers = new HashMap<>();
	private final Map<String, MethodHandler> returnTypeMethodHandlers = new HashMap<>();

	private final MethodHandler defaultReturnTypeMethodHandler = new DefaultReturnTypeMethodHandler();
	private static final MethodHandlerFactory INSTANCE = new MethodHandlerFactory();

	private MethodHandlerFactory() {
		signatureMethodHandlers.put("toString()Ljava/lang/String;", new ToStringMethodHandler());

		methodNameHandlers.put("<init>", new InitMethodHandler());

		returnTypeMethodHandlers.put("boolean", new BooleanReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("byte", new ByteReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("char", new CharReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("float", new FloatReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("short", new ShortReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("int", new IntReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("long", new LongReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("double", new DoubleReturnTypeMethodHandler());
		returnTypeMethodHandlers.put("void", new VoidReturnTypeMethodHandler());
	}

	/**
	 * Picks the right body generator for the return type of the method.
	 *
	 * @param methodSignature
	 *     The method signature.
	 * @param methodName
	 *     The method name.
	 * @param returnType
	 *     The descriptor of the return type.
	 * @return the handler able to implement it.
	 */
	public MethodHandler getHandler(String methodSignature, String methodName, String returnType) {
		MethodHandler methodHandler = signatureMethodHandlers.get(methodSignature);

		if (methodHandler == null)
			methodHandler = methodNameHandlers.get(methodName);

		if (methodHandler == null)
			methodHandler = returnTypeMethodHandlers.get(returnType);

		if (methodHandler == null)
			methodHandler = defaultReturnTypeMethodHandler;
		return methodHandler;
	}

	/**
	 * Returns the single instance of this class; it is the one that gets registered and the one reused on every
	 * request.
	 *
	 * @return the shared instance.
	 */
	public static MethodHandlerFactory getInstance() {
		return INSTANCE;
	}
}
