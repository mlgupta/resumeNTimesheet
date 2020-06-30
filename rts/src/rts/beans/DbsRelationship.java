package rts.beans;


import oracle.ifs.beans.Relationship;
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsRelationship  extends DbsSystemObject{
  private Relationship relationship;
  
  public static final String ACTIVE_ATTRIBUTE = Relationship.ACTIVE_ATTRIBUTE;
  public static final String CLASS_NAME = Relationship.CLASS_NAME;
  public static final String LEFTOBJECT_ATTRIBUTE = Relationship.LEFTOBJECT_ATTRIBUTE;
  public static final String NAME_ATTRIBUTE = Relationship.NAME_ATTRIBUTE;
  public static final String POLICYBUNDLE_ATTRIBUTE = Relationship.POLICYBUNDLE_ATTRIBUTE;
  public static final String PROPERTYBUNDLE_ATTRIBUTE = Relationship.PROPERTYBUNDLE_ATTRIBUTE;
  public static final String RIGHTOBJECT_ATTRIBUTE = Relationship.RIGHTOBJECT_ATTRIBUTE;
  public static final String SORTSEQUENCE_ATTRIBUTE = Relationship.SORTSEQUENCE_ATTRIBUTE;
  
  public DbsRelationship( Relationship newRelationship ) {
    super(newRelationship);
    this.relationship = newRelationship;
  }
  
  
  public DbsPublicObject getLeftObject() throws DbsException{
    DbsPublicObject dbsPOToReturn = null;
    try{
      dbsPOToReturn = new DbsPublicObject(this.relationship.getLeftObject());
    }catch (IfsException e) {
      throw new DbsException(e); 
    }
    return dbsPOToReturn;
  }

  
  public DbsPublicObject getRightObject() throws DbsException{
    DbsPublicObject dbsPOToReturn = null;
    try{
      dbsPOToReturn = new DbsPublicObject(this.relationship.getRightObject());
    }catch (IfsException e) {
      throw new DbsException(e); 
    }
    return dbsPOToReturn;
  }

  
  public long getSortSequence() throws DbsException{
    long sortSeqId = 0;
    try {
      sortSeqId = this.relationship.getSortSequence();
    }catch (IfsException e) {
      throw new DbsException(e); 
    }
    return sortSeqId;
  }
    
}