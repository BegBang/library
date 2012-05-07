package com.ibm.ils.library.datastore.util;

import java.util.Random;

public class LibraryIdGenerator {
	private static Random r = new Random();
	
	public static int generateId() {
		return r.nextInt(Integer.MAX_VALUE - 1) + 1;
	}

}
