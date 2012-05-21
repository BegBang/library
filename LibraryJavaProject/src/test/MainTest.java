package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.CopyExists;
import com.ibm.ils.library.model.exceptions.CopyNotFound;
import com.ibm.ils.library.model.exceptions.InvalidPassword;
import com.ibm.ils.library.model.exceptions.ItemExists;
import com.ibm.ils.library.model.exceptions.ItemNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronExists;
import com.ibm.ils.library.model.exceptions.PatronNotFound;
import com.ibm.websphere.naming.WsnInitialContextFactory;

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
      e1.printStackTrace();
    }
    catch (SystemUnavailableException e1) {
      e1.printStackTrace();
    }
    catch (OperationFailed e1) {
      e1.printStackTrace();
    }*/

		// najdi pomoci id		
		/*Patron patron2 = null;
		try {
			patron2 = Patron.findById(200);
			System.out.println("Patron2: " +patron2);
		} catch (SystemUnavailableException e) {
			e.printStackTrace();
		} catch (PatronNotFound e) {
			e.printStackTrace();
		}
    catch (OperationFailed e) {
      e.printStackTrace();
    }*/
		
		//remove
		/*Patron patron3 = new Patron();
		patron3.setId(20);
		try {
		  
		  patronX.remove(patron3);
    }
    catch (PatronNotFound e) {
      e.printStackTrace();
    }
    catch (SystemUnavailableException e) {
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      e.printStackTrace();
    }*/
		
		//update
		/*try {
      patronX.update(patron);
    }
    catch (SystemUnavailableException e) {
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      e.printStackTrace();
    }
    catch (PatronNotFound e) {
      e.printStackTrace();
    }
    catch (PatronExists e) {
      e.printStackTrace();
    }*/

	  //find copies for patron id
	 /* Copy copyX = new Copy();
	  Collection<Copy> copies = new ArrayList<Copy>();
	  try {
	    copies = copyX.findCopiesForPatronId(2);
    }
    catch (SystemUnavailableException e) {
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      e.printStackTrace();
    }
	  System.out.println("Kopie pro patrona:");
	  for (Copy copy : copies) {
      System.out.println(copy.toString());
    }*/
	  
	  //list items
	  /*Copy copyX = new Copy();
    Collection<LoanedCopy> copies = new ArrayList<LoanedCopy>();
    try {
      copies = copyX.findLoanedCopiesForPatronId(2);
    }
    catch (SystemUnavailableException e) {
      e.printStackTrace();
    }
    catch (OperationFailed e) {
      e.printStackTrace();
    }
    System.out.println("Vypujcene Kopie pro patrona:");
    for (LoanedCopy copy : copies) {
      System.out.println(copy.toString());
    }*/
    
    //pridani item
		//Item it = new Item();
		/*Item it = new Item('C', "isbn1", "title", "author", true, 1, new Date(1));
		try {
			it.add();
		} catch (SystemUnavailableException e) {
			e.printStackTrace();
		} catch (OperationFailed e) {
			e.printStackTrace();
		} catch (ItemExists e) {
			e.printStackTrace();
		}*/
    
    //renew
		
    
    
    //login
		/*try {
			Patron.verifyLogon("husa@email.c", "anickaa");
		} catch (SystemUnavailableException e) {
			e.printStackTrace();
		} catch (OperationFailed e) {
			e.printStackTrace();
		} catch (PatronNotFound e) {
			e.printStackTrace();
		} catch (InvalidPassword e) {
			e.printStackTrace();
		}*/
    
		//test renew
		/*Patron patronX = new Patron();
		patronX.setId(2);
		try {
			Collection<LoanedCopy> forRenew = patronX.retriveLoanedCopies(patronX);
			System.out.println("Before renew");
			for (LoanedCopy loanedCopy : forRenew) {
				loanedCopy.setRenewRequest(true);
				System.out.println(loanedCopy);
			}
			patronX.renew(forRenew);
			System.out.println("After renew");
			for (LoanedCopy loanedCopy : forRenew) {
				System.out.println(loanedCopy);
			}
		} catch (SystemUnavailableException e) {
			e.printStackTrace();
		} catch (OperationFailed e) {
			e.printStackTrace();
		}*/
		
		/*javax.sql.DataSource ds = null;
		javax.naming.InitialContext ctx;
		
		try {
			ctx = new javax.naming.InitialContext();
			ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/libraryDS");
			System.out.println(ds);
		} catch (NamingException e) {
			e.printStackTrace();
		}*/
		
		/*Copy copy = new Copy(true, null, 10, 0, 0, 11);
		System.out.println(copy);
		try {
			copy.add();
		} catch (SystemUnavailableException e) {
			e.printStackTrace();
		} catch (OperationFailed e) {
			e.printStackTrace();
		} catch (CopyExists e) {
			e.printStackTrace();
		}*/
		
		//TODO opravit
		/*Copy copy = new Copy();
		try {
			Collection<Copy> findCopiesForItemId = copy.findCopiesForItemId(4);
			for (Copy copy2 : findCopiesForItemId) {
				System.out.println(copy2);
			}
		} catch (OperationFailed e) {
			e.printStackTrace();
		} catch (SystemUnavailableException e) {
			e.printStackTrace();
		}*/
		
		/*Copy copy = new Copy();
		try {
			Collection<LoanedCopy> findCopiesForItemId = copy.findLoanedCopiesForPatronId(2);
			for (LoanedCopy copy2 : findCopiesForItemId) {
				System.out.println(copy2);
			}
		} catch (OperationFailed e) {
			e.printStackTrace();
		} catch (SystemUnavailableException e) {
			e.printStackTrace();
		}*/
		/*Copy copy = new Copy();
		copy.setItemId(1);
		try {
			Item item = copy.getItem(copy);
			System.out.println(item);
		} catch (SystemUnavailableException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (OperationFailed e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (ItemNotFound e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}*/
		
		/*Copy copy = new Copy();
		copy.setPatronId(2);
		try {
			Patron patron = copy.getPatron(copy);
			System.out.println(patron);
		} catch (PatronNotFound e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (SystemUnavailableException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (OperationFailed e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}*/
		
		/*Copy copy = new Copy();
		copy.setCopyNumber(3);
		copy.setItemId(1);
		copy.setPatronId(2);
		try {
			copy.update(copy);
		} catch (OperationFailed e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (SystemUnavailableException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (CopyNotFound e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}*/
		/*try {
			Patron patron = Patron.findById(2);
			System.out.println(patron);
			Collection<LoanedCopy> findCopiesForItemId = patron.retriveLoanedCopies();
			for (LoanedCopy copy2 : findCopiesForItemId) {
				System.out.println(copy2);
			}
			
		} catch (PatronNotFound e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (SystemUnavailableException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (OperationFailed e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}*/
		
		Item item = new Item('F', "dfas", "dfas", "dfas", true, 1, null);
		item.setId(1935661715);
		try {
			item.remove();
		} catch (SystemUnavailableException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (ItemNotFound e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (OperationFailed e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}
		
		
		
	}
}
