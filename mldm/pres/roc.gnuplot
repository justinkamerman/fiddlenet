set terminal png size 800, 800
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
f(x)=x
plot f(x), "roc.data" using 1:2 index 0 title "HelloWorld" with lines
