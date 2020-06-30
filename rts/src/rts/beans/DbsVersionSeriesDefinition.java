package rts.beans;

/* cmsdk API */
import oracle.ifs.adk.filesystem.*;
import oracle.ifs.beans.*;
import oracle.ifs.common.*;


public class DbsVersionSeriesDefinition extends DbsPublicObjectDefinition {
  
  private VersionSeriesDefinition versionSeriesDefinition = null;
  
  public DbsVersionSeriesDefinition( DbsLibrarySession dbsSession ) throws DbsException{
  
    super(dbsSession);
    
    try{
    
      this.versionSeriesDefinition = new VersionSeriesDefinition(dbsSession.getLibrarySession());
      
    }catch( IfsException ifsError ){
    
      throw new DbsException(ifsError);
      
    }
  
  }
  
  
  public void setFamilyDefinition( DbsFamilyDefinition dbsFamDef ) throws DbsException {
 
    try{
  
      this.versionSeriesDefinition.setFamilyDefinition(dbsFamDef.getFamilyDefinition());
    
    }catch( IfsException ifsError ){
      
      throw new DbsException(ifsError);
      
    }
    
  }
  
  
  public VersionSeriesDefinition getVersionSeriesDefinition(){
    
    return this.versionSeriesDefinition;
    
  }
}