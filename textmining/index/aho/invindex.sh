#!/bin/sh
# 
# $Id: ahoindex.sh 106 2011-03-10 23:58:34Z justinkamerman $ 
# 
# $LastChangedDate: 2011-03-10 18:58:34 -0500 (Thu, 10 Mar 2011) $ 
# 
# $LastChangedBy: justinkamerman $ 
# 

java -Xmx1024m -cp dist/cs6999.jar:thirdparty/commons-cli-1.2.jar -Djava.util.logging.config.file=logging.properties InvIndexMain $*