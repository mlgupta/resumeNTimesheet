package rts.beans;
import java.util.Comparator;

/**
 *	Implements a comparator that is used in sorting objects by name.
 */
public class DbsComparator implements Comparator  {
 /**
 * Compares two objects by names.
 * @param  a the first object to be compared.
 * @param  b the second object to be compared.
 * @return a negative integer, zero, or a positive integer as the first argument is less
 * than, equal to, or greater than the second
 */
  public int compare( Object a, Object b ) {
    try{
      DbsPublicObject dbsPO1 = (DbsPublicObject)a;
      DbsPublicObject dbsPO2 = (DbsPublicObject)b;
      
      if( dbsPO1 instanceof DbsFolder && dbsPO2 instanceof DbsFolder ){
        System.out.println("Both instances of DbsFolder");
        return dbsPO1.getName().compareToIgnoreCase(dbsPO2.getName());
      }else if( dbsPO1 instanceof DbsDocument && dbsPO2 instanceof DbsDocument ){
        System.out.println("Both instances of DbsDocument");
        return dbsPO1.getName().compareToIgnoreCase(dbsPO2.getName());
      }else if(dbsPO1 instanceof DbsFolder && dbsPO2 instanceof DbsDocument){
        System.out.println("one is an instance of DbsFolder and other DbsDocument");
        return -1;
      }else{
        System.out.println("None of the criteria match");
        return 1;
      }
    }catch (DbsException dbsEx) {
      dbsEx.printStackTrace();
    }
    System.out.println("Finally nothing has matched");
    return 1;
  }

/**
 * gives String representation of FsObjectComparator object.
 * @return String repersentation of FsObjectComparator object.
 */
  public String toString(){
    return this.toString();
  }
}
