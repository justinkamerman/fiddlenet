#!/bin/sh
# 
# $Id: run.sh 17 2010-08-02 20:05:09Z justin.kamerman $ 
# 
# $LastChangedDate: 2010-08-02 17:05:09 -0300 (Mon, 02 Aug 2010) $ 
# 
# $LastChangedBy: justin.kamerman $ 
# 
# weekend.data:    -f data/weekend.data -n weather,parents,money
#
# car.data:        -f data/car.data -n buying,maint,doors,persons,lug_boot,safety
#
# ecoli.data:      -f data/ecoli.data -n mcg,gvh,lip,chg,aac,alm1,alm2
#
# breast-cancer-wisconsin.data: -f data/breast-cancer-wisconsin.data -n Clump_Thickness,Uniformity_of_Cell_Size,Uniformity_of_Cell_Shape,Marginal_Adhesion,Single_Epithelial_Cell_Size,Bare_Nuclei,Bland_Chromatin,Normal_Nucleoli,Mitoses
#
# letter-recognition:   -f data/letter-recognition.data -n x-box,y-box,width,high,onpix,x-bar,y-bar,x2bar,y2bar,xybar,x2ybr,xy2br,x-ege,xegvy,y-ege,yegvx
#
# mushroom.data:        -f data/mushroom.data -n cap-shape,cap-surface,cap-color,bruises?,odor,gill-attachment,gill-spacing,gill-size,gill-color,stalk-shape,stalk-root,stalk-surface-above-ring,stalk-surface-below-ring,stalk-color-above-ring,stalk-color-below-ring,veil-type,veil-color,ring-number,ring-type,spore-print-color,population,habitat
#

java -cp dist/mldm.jar:thirdparty/commons-cli-1.2.jar -Djava.util.logging.config.file=logging.properties NBMain $*