package com.ibm.ils.library.model;

import java.io.Serializable;
import java.util.Date;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.ItemDataStore;

public class Item implements Serializable {
  private static final long serialVersionUID = 1829130523855209126L;

  /**
   * Medium values (UNKNOWN = '?', BOOK = 'B', CD = 'C', AUDIO = 'A', TAPE =
   * 'T', DVD = 'D').
   */
  private final static String MEDIUMS = "BCATD";
  private final static char UNKNOWN = '?'; //TODO asi nepovolena hodnota
  /*
   * @SuppressWarnings("serial") static final Map<String , String> MEDIUMS = new
   * HashMap<String , String>() {{ put("Up", "Down"); put("Charm", "Strange");
   * put("Top", "Bottom"); }};
   * 
   * public final static char UNKWON = '?'; public final static char BOOK = 'B';
   * public final static char CD = 'C'; public final static char AUDIO = 'A';
   * public final static char VTAPE = 'T'; public final static char DVD = 'D';
   */

  private int id;

  private char medium;

  private String isbnEquivalent;

  private String title;

  private String author;

  private boolean oversize;

  private int volumes;

  private Date published;

  private static ItemDataStore dataStore;

  static {
    dataStore = DataStoreFactory.getItemDataStore();
  }

  public Item() {
    this(UNKNOWN, "", "", "", false, 1, null);
  }

  public Item(char medium, String isbnEquivalent, String title, String author,
      boolean oversize, int volumes, Date published) {
    this(0, medium, isbnEquivalent, title, author, oversize, volumes, published);
  }

  public Item(int id, char medium, String isbnEquivalent, String title,
      String author, boolean oversize, int volumes, Date published) {
    super();
    this.id = id;
    this.medium = medium;
    this.isbnEquivalent = isbnEquivalent;
    this.title = title;
    this.author = author;
    this.oversize = oversize;
    this.volumes = volumes;
    this.published = published;
  }
  
  //TODO volani metod datastore

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public char getMedium() {
    return medium;
  }

  public void setMedium(char medium) {
    //TODO neresit tady, ale pri validaci
    String value = String.valueOf(medium).toUpperCase();
    int index = MEDIUMS.indexOf(value);
    if (index < 0) {
      medium = '?';
    }
    this.medium = medium;
  }

  public String getIsbnEquivalent() {
    return isbnEquivalent;
  }

  public void setIsbnEquivalent(String isbnEquivalent) {
    this.isbnEquivalent = isbnEquivalent;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isOversize() {
    return oversize;
  }

  public void setOversize(boolean oversize) {
    this.oversize = oversize;
  }

  public int getVolume() {
    return volumes;
  }

  public void setVolume(int volume) {
    this.volumes = volume;
  }

  public Date getPublished() {
    return published;
  }

  public void setPublished(Date published) {
    this.published = published;
  }
  
  

}
