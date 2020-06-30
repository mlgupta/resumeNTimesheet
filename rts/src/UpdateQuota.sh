#!/bin/sh


################################################################################################################################
########################################CMSDK Specific Information#################################################################
################################################################################################################################

#CMSDK Service Name
export SERVICE_NAME="IfsServiceDefault"

#CMSDK Domain
export DOMAIN="ifs://user1.dbsentry.com:1521:cmsdk:CMSDK"

#CMSDK Service Configuration to use
export SERVICE_CONFIG="SmallServiceConfiguration"

#CMSDK schema password
export IFS_SCHEMA_PASSWORD="cmsdk" 

#User Id for System Admin user in CMSDK
export SYS_AD_USER="system"

#Password for System Admin user in CMSDK
export SYS_AD_PASSWORD="system"


################################################################################################################################
########################################Company Specific Information################################################################
################################################################################################################################

#Company Name
export COMPANY_NAME="tradebench"

#Company Admin User Name
export COMPANY_ADMIN_NAME="myadmin"

#Company Admin Password
export COMPANY_ADMIN_PASSWORD="aaaaa"

#Quota to be Updated
#export QUOTA="100"
export QUOTA="120"

################################################################################################################################
########################################CLASSPATH Information####################################################################
################################################################################################################################

#Oracle Home
export ORACLE_HOME="/opt/ias/10g"

# RTS deploy Location
export RTS_DEPLOY_LOCATION=$ORACLE_HOME/j2ee/rts/applications/rts/rts/

export CLASSPATH=:.:$RTS_DEPLOY_LOCATION/WEB-INF/classes:$RTS_DEPLOY_LOCATION/WEB-INF/lib/cmsdk.jar:$ORACLE_HOME/jdbc/lib/classes12.jar:$RTS_DEPLOY_LOCATION/WEB-INF/lib/log4j-1.2.8.jar

java UpdateQuota   $SERVICE_NAME $SERVICE_CONFIG $IFS_SCHEMA_PASSWORD $DOMAIN $SYS_AD_USER $SYS_AD_PASSWORD $QUOTA $COMPANY_ADMIN_NAME $COMPANY_ADMIN_PASSWORD

