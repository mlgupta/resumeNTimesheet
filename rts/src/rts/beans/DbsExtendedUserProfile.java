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
 * $Id: DbsExtendedUserProfile.java,v 1.1.1.1 2005/05/26 12:31:44 suved Exp $
 *****************************************************************************
 */
package rts.beans;

/**
 *	Purpose: The encapsulate the functionality of ExtendedUserProfile class provided by CMSDK API.
 * 
 *  @author              Mishra Maneesh
 *  @version             1.0
 * 	Date of creation:   12-02-2004
 * 	Last Modfied by :     
 *	Last Modfied Date:    
 */

/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;

public class DbsExtendedUserProfile extends DbsPublicObject{

    private UserProfile extendedUserProfile=null; // to accept object of type UserProfile
    /** This class name for this class.*/
    public static final String CLASS_NAME=ExtendedUserProfile.CLASS_NAME; 

    /**
     * Purpose   : To create DbsExtendedUserProfile using ExtendedUserProfile class
     * @throws   : DbsException - if operation fails.
     */
    public DbsExtendedUserProfile(ExtendedUserProfile extendedUserProfile) {
        super(extendedUserProfile);
        this.extendedUserProfile=extendedUserProfile;
    }
}
