/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pom.common;

import java.io.*;
import java.util.logging.*;

import com.google.gson.*;
import com.google.gson.stream.*;

/**
 *
 * support convert string Json to class
 */
public class JsonUtils
{

	/**
	 * Constructor
	 */
	private JsonUtils()
	{
		throw new IllegalAccessError("JsonUtils class");
	}

	/**
	 * Convert Json to class model
	 *
	 * @param <T>
	 * @param json
	 * @param classModel
	 * @return
	 */
	public static <T> T convertJsonToClass(String json, Class<T> classModel)
	{
		try
		{
			// create new object Gson
			Gson gson = new Gson();
			// read String json
			JsonReader reader = new JsonReader(new StringReader(json));
			// setting Lenient true
			reader.setLenient(true);
			// get String to class
			T dto = gson.fromJson(reader, classModel);
			return dto;
		}
		catch (JsonSyntaxException ex)
		{
			Logger.getLogger(JsonUtils.class.getName()).log(Level.WARNING, null, ex);
			return null;
		}
	}

	/**
	 * Convert java object to json string
	 *
	 * @param objectClass
	 * @return
	 */
	public static String convertClassToJson(Object objectClass)
	{
		try
		{
			// create new object Gson
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			// get String Json
			String jsonInString = gson.toJson(objectClass);
			return jsonInString;
		}
		catch (JsonSyntaxException ex)
		{
			Logger.getLogger(JsonUtils.class.getName()).log(Level.WARNING, null, ex);
			return null;
		}
	}

}
