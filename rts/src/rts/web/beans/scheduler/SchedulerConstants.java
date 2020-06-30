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
 * $Id: SchedulerConstants.java,v 1.1.1.1 2005/05/26 12:32:48 suved Exp $
 *****************************************************************************
 */
package rts.web.beans.scheduler;
/**
 *	Purpose: To provide a single source of declaration for all the constants 
 *           being used by scheduler classes.
 *                 
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SchedulerConstants 
{
    public static final String JOB_TYPE = "job_type";
    public static final String JOB_CREATOR = "job_creator";
    public static final String EXECUTION_TIME = "execution_time";
    public static final String TIMEZONE = "timezone";
    public static final String CREATE_TIME = "create_time";
    public static final String COMPANY_NAME = "company_name";
    public static final String PROP_SMTP_HOST = "smtp_host"; 
    public static final String PROP_FAX_NUMBER = "fax_number";
    public static final String PROP_FAX_TO = "fax_to";
    public static final String PROP_FAX_COMMENTS = "fax_comments";
    public static final String PROP_RECIPIENT = "recipient";    
    public static final String PROP_CC_RECIPIENT = "cc_recipient";
    public static final String PROP_BCC_RECIPIENT = "bcc_recipient";
    public static final String PROP_SENDER = "sender";  
    public static final String PROP_REPLY_TO = "reply_to";    
    public static final String PROP_SUBJECT = "subject";
    public static final String PROP_MESSAGE = "message";
    public static final String PROP_ATTACHMENTS = "attachments";
    public static final String MAIL_JOB = "MAIL" ;
    public static final String PRINT_JOB = "PRINT" ;
    public static final String FAX_JOB = "FAX" ;
    public static final String ALL_JOBS = "ALL" ;
    public static final String JOB_NAME_SUFFIX = "job_";
    public static final String TRIGGER_NAME_SUFFIX = "trigger_";    
    public static final String FAX_TEMP_DIR="fax_temp_dir";
    public static final String FAX_CMD="sendfax ";
    public static final String RETRIAL_COUNT="retrial_count";
    public static final String RETRIAL_INTERVAL="retrial_interval";
    public static final String MAX_COUNT="max_count";
    public static final String ERROR_MESG="error_mesg";
    
}
