package test;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataStoreFactory dataFactory = new DataStoreFactory();
		
		PatronDataStore patronDS = dataFactory.getPatronDataStore();
		patronDS.findById(1);

	}

}
