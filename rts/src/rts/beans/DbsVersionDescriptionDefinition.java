package rts.beans;


/*CMSDK API*/ 
import oracle.ifs.adk.filesystem.*;
import oracle.ifs.beans.*;
import oracle.ifs.common.*;


public class DbsVersionDescriptionDefinition extends DbsPublicObjectDefinition  {
  
  private VersionDescriptionDefinition vdd = null;
  
  public DbsVersionDescriptionDefinition(DbsLibrarySession dbsSession) throws DbsException {
  
        super(dbsSession);
        
        try{
        
          this.vdd = new VersionDescriptionDefinition(dbsSession.getLibrarySession());
        
        }catch(IfsException ifsError){
          
          throw new DbsException(ifsError);
          
        }
  }
  public void setPublicObject(DbsPublicObject dbsPublicObject) throws DbsException{

      try{
  
        this.vdd.setPublicObject(dbsPublicObject.getPublicObject());  
    
      }catch( IfsException ifsError ){
        
        throw new DbsException(ifsError);
        
      }
  
  }
  
  public void setOwnerBasedOnPublicObjectOption(boolean boolOption) throws DbsException{
    
    try{
    
      this.vdd.setOwnerBasedOnPublicObjectOption(boolOption);
  
  
    }catch( IfsException ifsError ){
      
      throw new DbsException(ifsError);
      
    }
    
  }
  public VersionDescriptionDefinition getVersionDescriptionDefinition(){
    
    return this.vdd;
    
  }
  
  public void setVersionSeriesDefinition(DbsVersionSeriesDefinition dbsVsd ) throws DbsException{
    
    try{
    
      this.vdd.setVersionSeriesDefinition(dbsVsd.getVersionSeriesDefinition());
  
  
    }catch( IfsException ifsError ){
      
      throw new DbsException(ifsError);
      
    }
    
    
  }
}