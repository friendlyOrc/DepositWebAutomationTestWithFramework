/*
 * Copyright Orchestra Networks 2000-2010. All rights reserved.
 */
package com.pom.common;

/**
 */
public class StringUtils
{
	public static boolean isEmpty(String input)
	{
		if (input == null || input.length() == 0)
		{
			return true;
		}
		return false;
	}
}
