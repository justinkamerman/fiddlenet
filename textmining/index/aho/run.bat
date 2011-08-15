rem  
rem  $Id$ 
rem  
rem  $LastChangedDate$ 
rem  
rem  $LastChangedBy$ 
rem  

@echo off
java -cp dist\mldm.jar;thirdparty\commons-cli-1.2.jar -Djava.util.logging.config.file=logging.properties Main %*

