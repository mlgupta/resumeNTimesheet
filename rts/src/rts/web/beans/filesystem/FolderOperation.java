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
 * $Id: FolderOperation.java,v 1.3 2005/09/13 09:48:45 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.filesystem;

public class FolderOperation  {
    public final static byte NO_OPERATION = 1;
    public final static byte MENU_CLICK = 1;
    public final static byte GOTO = 2;
    public final static byte UP_FOLDER = 3;
    public final static byte FOLDER_CLICK = 4;
    public final static byte REFRESH = 5;
    public final static byte BACK = 6;
    public final static byte FORWARD = 7;    
    public final static byte COPY = 8;
    public final static byte MOVE = 9;
    public final static byte CHECKOUT = 10;
    public final static byte CHECKIN = 11;
    public final static byte NAVIGATE = 12;
    public final static byte LINK = 13;
}
