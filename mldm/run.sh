#!/bin/sh
# 
# $Id: run.sh 17 2010-08-02 20:05:09Z justin.kamerman $ 
# 
# $LastChangedDate: 2010-08-02 17:05:09 -0300 (Mon, 02 Aug 2010) $ 
# 
# $LastChangedBy: justin.kamerman $ 
# 

# car.data: -f data/car.data -n buying,maint,doors,persons,lug_boot,safety

java -cp dist/mldm.jar:thirdparty/commons-cli-1.2.jar -Djava.util.logging.config.file=logging.properties Main $*