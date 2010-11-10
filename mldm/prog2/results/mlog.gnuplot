set terminal png size 1200,800
set output "mlog.png"
set grid
set xlabel "m"
set ylabel "mean accuracy"
set xrange [0:10000]
set yrange [0:1]
set title "Probability Estimation"
set data style dot
plot "car.mlog" using 1:2 index 0 title "car" smooth bezier, \
     "ecoli.mlog" using 1:2 index 0 title "ecoli" smooth bezier, \
     "breast-cancer-wisconsin.mlog" using 1:2 index 0 title "breast-cancer" smooth bezier, \
     "mushroom.mlog" using 1:2 index 0 title "mushroom" smooth bezier