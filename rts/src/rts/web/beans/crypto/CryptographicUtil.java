/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: CryptographicUtil.java,v 1.3 2006/01/06 10:21:25 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.crypto;
/*rts package references */
import rts.beans.DbsDocument;
import rts.beans.DbsException;
/* Java API */
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

import java.util.Date;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.security.Security;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.X509V3CertificateGenerator;
import org.apache.log4j.*;
import org.bouncycastle.jce.provider.*;

/**
 * @author	Mishra Maneesh
 */
public class CryptographicUtil {
    private RSAPublicKey publicKey = null; 
    private RSAPrivateCrtKey privateKey = null;
    private static final String SYMMETRIC_ALGO="Blowfish";
    private static final String ASYMMETRIC_ALGO="RSA";
    private static final String PROVIDER="BC";
    private static final String KEYSTORE_TYPE="BKS";
    private static final int BYTE_LENGTH=128;
    private static final String KEYSTORE_FILE_NAME=".keystore";
    private static final String PASSWORD_FILE_NAME=".password";
    private static final String PATH_SEPERATOR=File.separator;
    private static final String ENCRYPTED_FILE_PREFIX="encrypted_";
    private static final String DECRYPTED_FILE_PREFIX="decrypted_";
    private static final String KEYSTORE_DIR="UserKeystores";
    private static final String SYSTEM_KEYSTORE_DIR="SystemKeystore";
    private static final String KEYSTORE_NOT_PRESENT="KEYSTORE_NOT_PRESENT";
    private static final String PASSWORD_WRONG="PASSWORD_WRONG";
    private static final String SUCCESS="SUCCESS";
    private static final String FAILURE="FAILURE";      
    private static final String KEY_GENERATION_ERROR="KEY_GENERATION_ERROR";
    private static final String URL_FILE="URL_FILE";
    private Logger logger = Logger.getLogger("DbsLogger");

    public CryptographicUtil()
    {
        addProvider();
    }

    private void addProvider(){
        java.security.Provider provider = new BouncyCastleProvider();
        int result = Security.addProvider(provider);
        if(result == -1){
            logger.info(" Provider entry already in file.\n");
         } else{
            logger.info(" Provider added at position: " + result);
         }
   }
    /***/
    private boolean genKeys(){
        FileInputStream fin = null;
        KeyPairGenerator keyGen = null;
        SecureRandom random = null;
        boolean keysGenerated=false;
        try{
            keyGen = KeyPairGenerator.getInstance(ASYMMETRIC_ALGO,PROVIDER);
            random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            RSAKeyGenParameterSpec keySpec = new RSAKeyGenParameterSpec(1024,RSAKeyGenParameterSpec.F0);
            keyGen.initialize(keySpec, random);
            KeyPair pair = keyGen.generateKeyPair();
            publicKey = (RSAPublicKey)pair.getPublic();
            privateKey = (RSAPrivateCrtKey)pair.getPrivate();
            logger.debug("Generate Key process completed");
            keysGenerated=true;
        }catch (Exception e){
            logger.debug("No such Random/KeyPairGenerator algorithm or provider");
            logger.error(e);
        }
        return keysGenerated;
    }    

    /***/
    private boolean saveStore(String passwd,String absolutePath){       
       boolean keystoreSaved=false;
       try{
            KeyStore store = KeyStore.getInstance(KEYSTORE_TYPE,PROVIDER);
            store.load(null, null);            
            BigInteger      modulus = privateKey.getModulus();
            BigInteger      privateExponent = privateKey.getPrivateExponent();            
            Hashtable                   attrs = new Hashtable();
            attrs.put(X509Principal.C, "IN");
            attrs.put(X509Principal.O, "DBSentry Solutions Pvt Ltd");
            attrs.put(X509Principal.L, "PUNE");
            attrs.put(X509Principal.ST, "MH");
            attrs.put(X509Principal.EmailAddress, "info@dbsentry.com");            
            X509V3CertificateGenerator  certGen = new X509V3CertificateGenerator();
            certGen.setSerialNumber(BigInteger.valueOf(1));
            certGen.setIssuerDN(new X509Principal(attrs));
            certGen.setNotBefore(new Date(System.currentTimeMillis() - 50000));
            certGen.setNotAfter(new Date(System.currentTimeMillis() + 50000));
            certGen.setSubjectDN(new X509Principal(attrs));
            certGen.setPublicKey(publicKey);
            certGen.setSignatureAlgorithm("MD5WithRSAEncryption");            
            java.security.cert.Certificate[]   chain = new java.security.cert.Certificate[1];           
            X509Certificate cert = certGen.generateX509Certificate(privateKey);
            cert.checkValidity(new Date());
            cert.verify(publicKey); 
            ByteArrayInputStream    bIn = new ByteArrayInputStream(cert.getEncoded());
            CertificateFactory      fact = CertificateFactory.getInstance("X.509", "BC");
            cert = (X509Certificate)fact.generateCertificate(bIn);
            chain[0] = cert;           
            store.setKeyEntry("private", privateKey, passwd.toCharArray(), chain);
            store.store(new FileOutputStream(absolutePath,false),passwd.toCharArray());
            keystoreSaved=true;
        }catch (Exception e){
            logger.error(e);
        }
        return keystoreSaved;
    }

    /***/
    private boolean readKeyStore(String passwd,String absolutePath){
        boolean isKeystorePresent=false;
        try{           
            KeyStore store = KeyStore.getInstance("BKS","BC");            
            store.load(new FileInputStream(absolutePath),passwd.toCharArray());
            privateKey = (RSAPrivateCrtKey)store.getKey("private", passwd.toCharArray());
            java.security.cert.Certificate cert = store.getCertificateChain("private")[0];
            publicKey=(RSAPublicKey)cert.getPublicKey();
            //logger.debug("public Modulus " + publicKey.getModulus());
            //logger.debug("private Modulus " + privateKey.getModulus());

            //logger.debug("public Exponent " + publicKey.getPublicExponent());
            //logger.debug("private Exponent " + privateKey.getPrivateExponent());
            isKeystorePresent=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isKeystorePresent;        
    }   

    /**
     * @param	userName
     * @param	oldPass
     * @param	newPass
     * @param	path
     */
    public String changeKeystorePass(String userName,String oldPass,String newPass,String path){
        try{
            String absolutePath=path+KEYSTORE_DIR+PATH_SEPERATOR+userName+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
            File keystoreFile=new File(absolutePath);
            if(!keystoreFile.exists()){
                return KEYSTORE_NOT_PRESENT;
            }

            if(readKeyStore(oldPass,absolutePath)){
                if(saveStore(newPass,absolutePath)){
                    return SUCCESS;
                }else{
                    return FAILURE;
                }
            }else{
                return PASSWORD_WRONG;
            }
        }catch(Exception exception){
            logger.error(exception);
            return FAILURE;
        }
    }

     /**
      * @param	userName
      * @param	newPass
      * @param	path
      * 	
      */
     public String setKeystorePass(String userName,String newPass,String path){
        try{
            String dirPath=path+KEYSTORE_DIR+PATH_SEPERATOR+userName;
            File dir=new File(dirPath);
            if(!dir.isDirectory()){
                dir.mkdirs();
            }         
            String absolutePath=path+KEYSTORE_DIR+PATH_SEPERATOR+userName+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
            
            if(genKeys()){
                if(saveStore(newPass,absolutePath)){
                    return SUCCESS;
                }else{
                    return FAILURE;
                }
            }else{
                return KEY_GENERATION_ERROR;
            }
        }catch(Exception exception){
            logger.error(exception);
            return FAILURE;
        }
    }




    /**
      * @param	userName
      * @param	newPass
      * @param	path
      * 	
      */
     public String setSystemKeystorePass(String newPass,String path){
        try{
            //this.logger.debug("path: "+path);
            String dirPath=path+SYSTEM_KEYSTORE_DIR;
            File dir=new File(dirPath);
            if(!dir.isDirectory()){
                dir.mkdirs();
            }         
            String absolutePath=path+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
            
            if(genKeys()){
                if(saveStore(newPass,absolutePath)){
                    return SUCCESS;
                }else{
                    return FAILURE;
                }
            }else{
                return KEY_GENERATION_ERROR;
            }
        }catch(Exception exception){
            logger.error(exception);
            return FAILURE;
        }
    }

  /**
   * @param	userName
   * @param	path
   */
  public void deleteKeystore(String userName,String path){
    try{
        String dirPath=path+KEYSTORE_DIR+PATH_SEPERATOR+userName;
        File dir=new File(dirPath);
        if ((dir.exists())){

        // Deleting the files from  Folder
        File[] filesInDir=dir.listFiles();
        for(int index=0;index<filesInDir.length;index++){
          if (!(filesInDir[index].delete())){
            this.logger.error("Unable to Delete "+ filesInDir[index].getName() + " File"); 
          }
        }

        // Deleting the Temp Folder
        if (!(dir.delete())){    
          this.logger.error("Unable to Delete "+ dirPath + " Directory"); 
        }else{
          this.logger.info("Directory "+ dirPath + " Deleted");      
        }  

      }else{
        this.logger.error("Unable to Find "+dirPath+ " Directory"); 
      }
 
    }catch(Exception e){
      this.logger.info("Exception Caught In deleteKeystore() of CryptographicUtil.java");
      this.logger.fatal(e.getMessage());       
    }
    

  }

    private RSAPublicKey getPublicKey() {
        return publicKey;
    }

    private RSAPrivateCrtKey getPrivateKey() {
        return privateKey;
    }

    /*private void testing() {
        logger.debug("public Modulus " + publicKey.getModulus());
        logger.debug("private Modulus " + privateKey.getModulus());

        logger.debug("public Exponent " + publicKey.getPublicExponent());
        logger.debug("private Exponent " + privateKey.getPrivateExponent());
    }*/

    private String encryptDecryptFile(InputStream fis,FileOutputStream fos,Cipher cipher){
         CipherInputStream cis = null;
         try{                      
             cis = new CipherInputStream(fis, cipher);
             byte[] b = new byte[2048];
             int i = cis.read(b);
             while (i != -1) {
                fos.write(b, 0, i);
                i = cis.read(b);
             }
         }catch(FileNotFoundException fe){
             logger.error(fe);
             return "FILENOTFOUND";
         }catch(IOException ie){
            logger.error("IOException: "+"IOException");
             logger.error(ie);
             return "IOEXCEPTION";
         }finally{
           if( cis!= null ){
             try {
               cis.close();
             }catch (Exception e) {
              logger.error(e.toString()); 
             }
             cis = null;
           }
         }
         return SUCCESS;       
    }

    private Key generateSecretKey(){
         Key key = null;
         try{
            SecureRandom rand = new SecureRandom();
            KeyGenerator kGen = KeyGenerator.getInstance(SYMMETRIC_ALGO);
            kGen.init(256, rand);
            key=kGen.generateKey();
            if(key!=null){
              logger.debug("key generated is not null");
            }else
              logger.debug("key generated is null..");
         }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
         }catch(Exception ex ){
            ex.printStackTrace();
        }
         return key;
    }

    /**
     * @param	userName
     * @param	passwd
     * @param	path
     */
    public boolean checkKeystore(String userName,String passwd,String path){
        String absolutePath=path+KEYSTORE_DIR+PATH_SEPERATOR+userName+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
        boolean isKeystorePresent=false;
        isKeystorePresent=readKeyStore(passwd,absolutePath);
        return isKeystorePresent;        
    }

    private boolean generateKeyStore(String userName,String passwd,String absolutePath){
        boolean keystoreGenerated=false;
        boolean keysGenerated=genKeys();
        if(keysGenerated){
            keystoreGenerated= saveStore(passwd,absolutePath);
        }
        return keystoreGenerated;
    }

    /**
     * @param	toBeEncrypted
     * @param	createKeystore
     * @param	userName
     * @param	passwd
     * @param	path
     */
    public InputStream encryptDoc(DbsDocument toBeEncrypted,String userName,String passwd ,String path ){
        ByteArrayInputStream encryptedObject=null;
        String absolutePath=path+KEYSTORE_DIR+PATH_SEPERATOR+userName+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
        try {

            if(readKeyStore(passwd,absolutePath)==false){
                return encryptedObject;
            }

            InputStream fis=null;
            FileOutputStream fos=null;
            Cipher c = Cipher.getInstance(ASYMMETRIC_ALGO,PROVIDER); 
            Key secretKey=generateSecretKey();
            c.init(Cipher.WRAP_MODE,getPublicKey());
        
            fis=toBeEncrypted.getContentStream();
            String filePathWithName=path+KEYSTORE_DIR+PATH_SEPERATOR+userName+PATH_SEPERATOR+ENCRYPTED_FILE_PREFIX+toBeEncrypted.getName();
            fos=new FileOutputStream(filePathWithName);
            fos.write(c.wrap(secretKey));
            Cipher encryptCipher = Cipher.getInstance(SYMMETRIC_ALGO,PROVIDER);                                 
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String result=encryptDecryptFile(fis,fos,encryptCipher);
            fis.close();
            fos.close(); 
            if(result.equals(SUCCESS)){
               File file = new File(filePathWithName);
               FileInputStream encryptedStream=new FileInputStream(file);
               int availableBytes=encryptedStream.available();
               byte fileBytes[]=new byte[availableBytes];
               encryptedStream.read(fileBytes,0,availableBytes);
               encryptedObject=new ByteArrayInputStream(fileBytes);
               encryptedStream.close();
               encryptedStream = null;
               if(file.exists()){
                    file.delete();
               }
            }
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }catch(DbsException dbsExcep)
        {
         logger.error(dbsExcep.getErrorMessage());   
        }catch (Exception e) {
            e.printStackTrace();
        }
     
        return encryptedObject;
        
    }

    //----------------Added by Suved--------------//

    /**Purpose: added to account for encrypting userpassword in URL
     * @param : password as ByteArrayInputStream
     * @param : userName
     * @param : cryptoPassword
     * @param : contextPath
     **/
     public InputStream encryptURL(InputStream iStream,String userName,String passwd ,String path ){
        ByteArrayInputStream encryptedObject=null;
        String absolutePath=path+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
        try {

            if(readKeyStore(passwd,absolutePath)==false){
                return encryptedObject;
            }

            InputStream fis=null;
            FileOutputStream fos=null;
            Cipher c = Cipher.getInstance(ASYMMETRIC_ALGO,PROVIDER); 
            Key secretKey=generateSecretKey();
            c.init(Cipher.WRAP_MODE,getPublicKey());
        
            fis=iStream;
            logger.debug("fis :"+fis.toString());
            String filePathWithName=path+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+ENCRYPTED_FILE_PREFIX+URL_FILE;
            fos=new FileOutputStream(filePathWithName);
            logger.debug("First   "+secretKey.getEncoded());
            fos.write(c.wrap(secretKey));
            Cipher encryptCipher = Cipher.getInstance(SYMMETRIC_ALGO,PROVIDER);                                 
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String result=encryptDecryptFile(fis,fos,encryptCipher);
            fis.close();
            fos.close(); 
            if(result.equals(SUCCESS)){
               File file = new File(filePathWithName);
               FileInputStream encryptedStream=new FileInputStream(file);
               int availableBytes=encryptedStream.available();
               byte fileBytes[]=new byte[availableBytes];
               encryptedStream.read(fileBytes,0,availableBytes);
               encryptedObject=new ByteArrayInputStream(fileBytes);
               encryptedStream.close();
               encryptedStream = null;
               if(file.exists()){
                    file.delete();
               }
            }
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
     
        return encryptedObject;
        
    }


    /**Purpose: added to account for decrypting userpassword in URL
     * @param : encryptedpassword as ByteArrayInputStream     
     * @param : cryptoPassword
     * @param : contextPath
     **/

     public InputStream decryptURL(InputStream iStream,String passwd ,String path ){
        String absolutePath=path+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
        ByteArrayInputStream decryptedObject=null;
        try {
            if(readKeyStore(passwd,absolutePath)==false){
                return decryptedObject;
            }
            InputStream fis=null;
            FileOutputStream fos=null;
            Cipher c = Cipher.getInstance(ASYMMETRIC_ALGO,PROVIDER); 
            Key secretKey=generateSecretKey();
            c.init(Cipher.UNWRAP_MODE,getPrivateKey());        
            fis=iStream;
            int available=fis.available();
            logger.debug("fis.available in DecryptURL(): "+available);
            //System.out.println("fis.available in DecryptURL(): "+available);
            String filePathWithName=path+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+DECRYPTED_FILE_PREFIX+URL_FILE;
            fos=new FileOutputStream(filePathWithName);
            byte[] wrappedKey=new byte[BYTE_LENGTH];
            fis.read(wrappedKey,0,wrappedKey.length);
            Key unWrappedKey=c.unwrap(wrappedKey,SYMMETRIC_ALGO,Cipher.SECRET_KEY); 
            logger.debug("Two   "+unWrappedKey.getEncoded());
            //System.out.println("Two  "+unWrappedKey.getEncoded());
            
            Cipher decryptCipher = Cipher.getInstance(SYMMETRIC_ALGO,PROVIDER);                                 
            decryptCipher.init(Cipher.DECRYPT_MODE, unWrappedKey);
            String result=encryptDecryptFile(fis,fos,decryptCipher);
            //System.out.println("result: "+result);
            fis.close();
            fos.close(); 
            if(result.equals(SUCCESS)){
               File file = new File(filePathWithName);
               FileInputStream decryptedStream=new FileInputStream(file);
               int availableBytes=decryptedStream.available();
               byte fileBytes[]=new byte[availableBytes];
               decryptedStream.read(fileBytes,0,availableBytes);
               decryptedObject=new ByteArrayInputStream(fileBytes); 
               decryptedStream.close();
               decryptedStream = null;
               if(file.exists()){
                   file.delete();
               }
            }  
            
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }     
        if(decryptedObject!=null){
          logger.debug("decryptedObject: "+"is not null");
          //System.out.println("decryptedObject: "+"is not null");
        }else
          logger.debug("decryptedObject: "+"is null");
          //System.out.println("decryptedObject: "+"is null");
        return decryptedObject;
        
    }
    //----------------Added by Suved-----------------//

      /**
       * @param	toBeDecrypted
       * @param	userName
       * @param	passwd
       * @param	path
       */
      public InputStream decryptDoc(DbsDocument toBeDecrypted,String userName,String passwd ,String path ){
        String absolutePath=path+KEYSTORE_DIR+PATH_SEPERATOR+userName+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
        ByteArrayInputStream decryptedObject=null;
        try {
            if(readKeyStore(passwd,absolutePath)==false){
                return decryptedObject;
            }
            InputStream fis=null;
            FileOutputStream fos=null;
            Cipher c = Cipher.getInstance(ASYMMETRIC_ALGO,PROVIDER); 
            Key secretKey=generateSecretKey();
            c.init(Cipher.UNWRAP_MODE,getPrivateKey());        
            fis=toBeDecrypted.getContentStream();
            String filePathWithName=path+KEYSTORE_DIR+PATH_SEPERATOR+userName+PATH_SEPERATOR+DECRYPTED_FILE_PREFIX+toBeDecrypted.getName();
            fos=new FileOutputStream(filePathWithName);
            byte[] wrappedKey=new byte[BYTE_LENGTH];
            fis.read(wrappedKey,0,wrappedKey.length);
            Key unWrappedKey=c.unwrap(wrappedKey,SYMMETRIC_ALGO,Cipher.SECRET_KEY);            
            Cipher decryptCipher = Cipher.getInstance(SYMMETRIC_ALGO,PROVIDER);                                 
            decryptCipher.init(Cipher.DECRYPT_MODE, unWrappedKey);
            String result=encryptDecryptFile(fis,fos,decryptCipher);
            fis.close();
            fos.close(); 
            if(result.equals(SUCCESS)){
               File file = new File(filePathWithName);
               FileInputStream decryptedStream=new FileInputStream(file);
               int availableBytes=decryptedStream.available();
               byte fileBytes[]=new byte[availableBytes];
               decryptedStream.read(fileBytes,0,availableBytes);
               decryptedObject=new ByteArrayInputStream(fileBytes); 
               decryptedStream.close();
               decryptedStream = null;
               if(file.exists()){
                   file.delete();
               }
            }  
            
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }catch(DbsException dbsExcep)
        {
         logger.error(dbsExcep.getErrorMessage());   
        }catch (Exception e) {
            e.printStackTrace();
        }     
        return decryptedObject;
        
    }

    /**Purpose: added to obtain passwordfile path 
     * @param : contextPath
     * */
    public String getPwdFilePath(String contextPath){
      String pwdFilePath=new String();
      pwdFilePath=contextPath+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+PASSWORD_FILE_NAME;
      return pwdFilePath;
    }

    /**Purpose: added to check the existence of .keyStore as well as .password in SystemKeystore 
     * @param : contextPath 
     * */
    public boolean getSystemKeyStoreFile(String contextPath){         
      boolean keyStoreExists=false;

      try{
        String filePath=contextPath+SYSTEM_KEYSTORE_DIR;
        File keyStoreDir=new File(filePath);

        if(keyStoreDir.isDirectory()){
            
        }else if(keyStoreDir.isFile()){
          keyStoreDir.delete();
          keyStoreDir.mkdirs();
        }else{
          keyStoreDir.mkdirs();
        }
          
        filePath=contextPath+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
        String pwdFilePath=new String();
        pwdFilePath=contextPath+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+PASSWORD_FILE_NAME;
        File keyStore=new File(filePath);
        File pwdFile=new File(pwdFilePath);

        if(keyStore.exists() && pwdFile.exists() ){
          keyStoreExists=true;
        }         

        }catch(Exception exception){
          logger.error(exception);            
        }
        logger.debug("keyStoreExists: "+keyStoreExists);
        return keyStoreExists;
    }

     /**Purpose: added to obtain .keyStore path in SystemKeystore 
      * @param : contextPath
      * */
    public String getSystemKeyStorePath(String contextPath){
      String path=new String("");

      try{
        path=contextPath+SYSTEM_KEYSTORE_DIR+PATH_SEPERATOR+KEYSTORE_FILE_NAME;
      }catch(Exception e){
        logger.error("error message: "+e.getMessage());
        e.printStackTrace();
      }
      return path;
    }

    /**Purpose: added to obtain  cryptoPassword for URL Encrytion/Decryption
     * @param : passwordFilePath
     **/
    public String getCryptoPassword(String pwdFilePath){
      String cryptoPassword=new String();
      try{
        FileReader fr=new FileReader(pwdFilePath);
        BufferedReader br=new BufferedReader(fr);
        StringBuffer sb=new StringBuffer();
        while((cryptoPassword=br.readLine())!=null){
          //logger.debug(cryptoPassword);
          sb.append(cryptoPassword);
        }
        //logger.debug("sb: "+sb.toString());
        cryptoPassword=sb.toString();
        //logger.debug("cryptoPassword: "+cryptoPassword);
        fr.close();
        if( br != null ){
          br.close();
        }
      }catch(Exception ex){        
        logger.error(ex.getMessage());
        ex.printStackTrace();        
      }      
      logger.info("Exiting getCryptoPassword() now...");
      return cryptoPassword;
    }


}
