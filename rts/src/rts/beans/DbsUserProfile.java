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
 * $Id: DbsUserProfile.java,v 1.1.1.1 2005/05/26 12:31:59 suved Exp $
 *****************************************************************************
 */
package rts.beans;


/*CMSDK API*/ 
import oracle.ifs.beans.*;
import oracle.ifs.common.*;


public class DbsUserProfile extends DbsPublicObject{
UserProfile userProfile=null;
    public DbsUserProfile(UserProfile userProfile)
    {
      super(userProfile);
      this.userProfile=userProfile;
    }
}
