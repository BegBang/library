package test;

import java.util.ArrayList;
import java.util.Collection;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronExists;
import com.ibm.ils.library.model.exceptions.PatronNotFound;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// DataStoreFactory dataFactory = new DataStoreFactory();

	  /*Patron patronX = new Patron();
		// vloz parchanta
		Patron patron = new Patron(11, "Aja", "Nova", "anicka", "aja");
		System.out.println("Patron: " + patron);*/
		/*try {
      patron.add();
    }
    catch (PatronExists e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    catch (SystemUnavailableException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    catch (OperationFailed e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }*/

		// najdi pomoci id
		/*PatronDataStore patronDS = DataStoreFactory.getPatronDataStore();
		Patron patron2 = new Patron();
		try {
			patron2 = patronDS.findById(6);
			System.out.println("Patron2: " +patron2);
		} catch (SystemUnavailableException e) {
			// TODO Automaticky generovan� blok catch
			e.printStackTrace();
		} catch (PatronNotFound e) {
			// TODO Automaticky generovan� blok catch
			e.printStackTrace();
		}
    catch (OperationFailed e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }*/
		
		//remove
		/*Patron patron3 = new Patron();
		patron3.setId(20);
		try {
		  
		  patronX.remove(patron3);
    }
    catch (PatronNotFound e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (SystemUnavailableException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }*/
		
		//update
		/*try {
      patronX.update(patron);
    }
    catch (SystemUnavailableException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (PatronNotFound e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (PatronExists e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }*/

	  //find copies for patron id
	 /* Copy copyX = new Copy();
	  Collection<Copy> copies = new ArrayList<Copy>();
	  try {
	    copies = copyX.findCopiesForPatronId(2);
    }
    catch (SystemUnavailableException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
	  System.out.println("Kopie pro patrona:");
	  for (Copy copy : copies) {
      System.out.println(copy.toString());
    }*/
	  
	  
	  Copy copyX = new Copy();
    Collection<LoanedCopy> copies = new ArrayList<LoanedCopy>();
    try {
      copies = copyX.findLoanedCopiesForPatronId(2);
    }
    catch (SystemUnavailableException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Vypujcene Kopie pro patrona:");
    for (LoanedCopy copy : copies) {
      System.out.println(copy.toString());
    }
	  
	}

}
