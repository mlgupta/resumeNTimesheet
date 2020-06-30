package rts.beans;

/* cmsdk API */
import oracle.ifs.adk.filesystem.*;
import oracle.ifs.beans.*;
import oracle.ifs.common.*;


public class DbsFamilyDefinition  extends DbsPublicObjectDefinition {


  private FamilyDefinition familyDefinition = null;
  
  public DbsFamilyDefinition(DbsLibrarySession dbsSession) throws DbsException{
    
    super(dbsSession);
    
    try{
    
      this.familyDefinition = new FamilyDefinition(dbsSession.getLibrarySession());
      
    }catch( IfsException ifsError ){
    
      throw new DbsException(ifsError);
      
    }
    
  }

  public FamilyDefinition getFamilyDefinition() {
    
      return this.familyDefinition;
      
  }

}