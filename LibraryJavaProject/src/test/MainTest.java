package test;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.model.Patron;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// DataStoreFactory dataFactory = new DataStoreFactory();

		// vloz parchanta
		Patron patron = new Patron(10, "Aja", "Nova", "anicka", "husa@email.cz");
		System.out.println(patron);
		patron.add();

		// najdi pomoci id
		PatronDataStore patronDS = DataStoreFactory.getPatronDataStore();
		Patron patron2 = patronDS.findById(10);
		System.out.println(patron2);

	}

}
