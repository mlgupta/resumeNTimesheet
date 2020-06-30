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
 * $Id: SampleConnection.java,v 1.1.1.1 2005/05/26 12:32:03 suved Exp $
 *****************************************************************************
 */
package rts.beans; 

public class SampleConnection {

    public static void main(String[] args) {

        try {

            // Connect to the 9iFS server. The arguments (args) are 
            // IfsService, IfsServicePassword, UserName, UserPassword 
            // The default values are ifsDefault, ifssys, system, manager9ifs
            System.out.println("Connecting...");

            args    = new String[6];
            args[0] = "IfsDefault";
            args[1] = "cmsdk";
            args[2] = "SmallServiceConfiguration";
            args[3] = "ifs://linux.dbsentry.com:1521:cmsdk.com:CMSDK";
            args[4] = "system";
            args[5] = "system";

            /*
                  // Use the IfsService and IfsServicePassword to start a LibraryService
                  LibraryService service = LibraryService.startService(args[0], args[1],args[2], args[3]);


                  // Create a CleartextCredential object, which encapsulates the
                  // username and password.

                  CleartextCredential cred = new CleartextCredential(args[4], args[5]);

                  // Start a LibrarySession by passing the credentials to the service

                  LibrarySession ifsSession = service.connect(cred, null);

                  //This is the place where you add code that actually does something.
                  //Just to show that we're connected, we'll query the repository for
                  //the name of the user who has logged in.
                  //

                  DirectoryUser du = ifsSession.getDirectoryUser();
                 System.out.println("Current user: " + du.getName());

                  // Disconnect from the 9iFS LibrarySession.
                  ifsSession.disconnect();

            */
           

             DbsLibraryService service =DbsLibraryService.startService(args[0], args[1], args[2],
                                           args[3]);

            DbsCleartextCredential cred = new DbsCleartextCredential(args[4],
                                              args[5]);

            

            DbsLibrarySession ifsSession = service.connect(cred, null);

            
            DbsDirectoryUser du = ifsSession.getUser();

//            du.setDirectoryUser(ifsSession.getUser());
            /*System.out.println("Current user: "
                               + du.getDistinguishedName());*/
            ifsSession.disconnect();
            //System.out.println("Disconnected.");
        } catch(DbsException dbsEx) {
            //System.out.println(dbsEx.getErrorCode() +"  "+dbsEx.getErrorMessage());
        }
    }
}


