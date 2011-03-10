#!/bin/sh
# 
# $Id: ahoindex.sh -1   $ 
# 
# $LastChangedDate: $ 
# 
# $LastChangedBy: $ 
# 

java -cp dist/cs6999.jar:thirdparty/commons-cli-1.2.jar -Djava.util.logging.config.file=logging.properties AhoSearchMain $*