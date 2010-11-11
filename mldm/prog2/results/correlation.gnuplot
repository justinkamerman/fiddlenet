set terminal png size 1200,800
set output "correlation.png"
set grid
set xlabel "total correlation C"
set ylabel "mean accuracy"
set yrange [0:1]
set xrange [0:30]
set data style point
set pointsize 2.5
plot "correlation.data" using 1:2 index 0 title "car", \
     "correlation.data" using 1:2 index 1 title "ecoli", \
     "correlation.data" using 1:2 index 2 title "breast-cancer", \
     "correlation.data" using 1:2 index 3 title "mushroom"