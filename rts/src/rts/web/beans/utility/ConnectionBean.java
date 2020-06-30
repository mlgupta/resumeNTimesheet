package rts.web.beans.utility;

/* rts package references */
import rts.web.beans.utility.ParseXMLTagUtil;
/* Java API */ 
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
/* Oracle JDBC API */
import oracle.jdbc.pool.OracleDataSource;
/* Logger API */
import org.apache.log4j.*;

public class ConnectionBean
{
  
  public Connection connection; // Database Connection Object
  public Statement  statement;
  private String relPath;        /* relative path for "GeneralActionParam.xml" */
  ParseXMLTagUtil parseUtil =null;
  Logger logger = null;
  
  public ConnectionBean(String relPath){
       //dbConnection();
       logger = Logger.getLogger("DbsLogger");
       this.relPath = relPath;
       //logger.debug("relPath: "+relPath);
       //System.out.println("relPath: "+relPath);
       parseUtil = new ParseXMLTagUtil(relPath);
       
  }
  
  public Connection getConnection() {
  
    try {
      
      OracleDataSource ods = new OracleDataSource();
 
      // Sets the driver type
      ods.setDriverType(parseUtil.getValue("DriverType","Configuration"));
 
      // Sets the database server name
      ods.setServerName(parseUtil.getValue("ServerName","Configuration"));
 
      // Sets the database name
      ods.setDatabaseName(parseUtil.getValue("DatabaseName","Configuration"));
 
      // Sets the port number
      ods.setPortNumber((new Integer(parseUtil.getValue("PortNumber","Configuration"))).intValue());
 
      // Sets the user name
      ods.setUser(parseUtil.getValue("dbsysaduser","Configuration"));
 
      // Sets the password
      ods.setPassword(parseUtil.getValue("dbsysadpwd","Configuration"));
      connection=ods.getConnection();
      
 
    } catch(SQLException sqlEx) { // Trap SQL errors
        connection=null;
        logger.error("SQLException: "+sqlEx.getMessage());
        sqlEx.printStackTrace();
    } catch(Exception ex){        /* catch other exceptions */
        connection =null;
        logger.error("Exception: "+ex.getMessage());
        ex.printStackTrace();
    }
    
    return connection;
  }
  
 public Statement getStatement(){

  Connection conn = getConnection();
  Statement stmt = null;
        
  if (conn!=null) {
    //Statement stmt = null;

    try {
          //Statement object for executing queries
          stmt = conn.createStatement();      
          
      } catch(SQLException sqlEx) { // Trap SQL errors
        logger.error("SQLException: "+sqlEx.getMessage());
        sqlEx.printStackTrace();
      } catch(Exception ex) {        /* catch other exceptions */
        logger.error("Exception: "+ex.getMessage());
        ex.printStackTrace();
      }
      
    }    /* end if*/
    connection=conn;
    statement=stmt;
    return statement;
    
  }
  
  public void closeStatement(){
    if (statement!=null) {
    //Statement stmt = null;
    logger.debug("Statement not null");
    try {
          //Statement object for executing queries
               statement.close();
               logger.debug("Statement closed");    
      } catch(SQLException sqlEx) { // Trap SQL errors
        logger.error("SQLException: "+sqlEx.getMessage());
        sqlEx.printStackTrace();
      } catch(Exception ex) {        /* catch other exceptions */        
        logger.error("Exception: "+ex.getMessage());
        ex.printStackTrace();
      }
      
    }    /* end if*/
  }
  
   public void closeConnection(){
    if (connection!=null) {
    //Statement stmt = null;
    logger.debug("connection not null");
    try {
          //Statement object for executing queries
               connection.close();
               logger.debug("connection closed");
      } catch(SQLException sqlEx) { // Trap SQL errors
        logger.error("SQLException: "+sqlEx.getMessage());
        sqlEx.printStackTrace();
      } catch(Exception ex) {        /* catch other exceptions */
        logger.error("Exception: "+ex.getMessage());
        ex.printStackTrace();
      }
      
    }    /* end if*/
  }
  /*public static void main(String args[]){
    ConnectionBean cb = new ConnectionBean("/u01/app/oracle/product/10g/j2ee/samhita1/applications/samhita1/samhita1/WEB-INF/params_xmls/GeneralActionParam.xml");
    Connection con = cb.getConnection();
    Statement stmt = cb.getStatement();
    try{ResultSet rst = stmt.executeQuery("select * from tab");
      while(rst.next()){
        System.out.println(rst.getString(1));
      }
    
    }catch(Exception e){
    
    e.printStackTrace();
  }
 
  }*/
}