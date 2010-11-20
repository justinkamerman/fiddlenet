set terminal png size 800, 800
set terminal png enhanced 
set output "roc.png"
set size .75,1
set size ratio 1
set xtics .1
set ytics .1
set grid
set xrange [0:1.025]
set yrange [0:1.025]
set key right bottom
set ylabel "True Positive Rate"
set xlabel "False Positive Rate" 
set label "a" at 0.08,0.75
random(x)=x
iso1(x)=1.51*x+0.48
plot random(x) title "TP = FP", \
     iso1(x) notitle linetype 4, \
     "roc.data" using 1:2 index 3 notitle with points 5, \
     "roc.data" using 1:2 index 0 title "A" smooth bezier, \
     "roc.data" using 1:2 index 1 title "B" smooth bezier, \
     "roc.data" using 1:2 index 2 title "C" smooth bezier
     