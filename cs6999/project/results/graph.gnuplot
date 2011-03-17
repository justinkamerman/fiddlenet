# ahostatemachine
set terminal png size 1200,800
set output "ahostatemachine.png"
set grid
set xlabel "number of keywords"
set ylabel "time (miliseconds)"
set xrange [0:500]
set yrange [0:25]
set style data points
set pointsize 2
plot "graph.data" using 1:2 index 0 title "", \
    "graph.data" using 1:2 index 0 title "" smooth bezier

# ahoscan
set terminal png size 1200,800
set output "ahoscan.png"
set grid
set xlabel "number of documents"
set ylabel "time (miliseconds)"
set xrange [0:50000]
set yrange [0:150000]
set style data points
set pointsize 2
plot "graph.data" using 1:2 index 1 title "100 keywords", \
    "graph.data" using 1:2 index 1 title "" smooth bezier, \
    "graph.data" using 1:3 index 1 title "200 keywords", \
    "graph.data" using 1:3 index 1 title "" smooth bezier, \
    "graph.data" using 1:4 index 1 title "400 keywords", \
    "graph.data" using 1:4 index 1 title "" smooth bezier

# ahosearch
set terminal png size 1200,800
set output "ahosearch.png"
set grid
set xlabel "number of search terms"
set ylabel "time (miliseconds)"
set xrange [0:15]
set yrange [0:150000]
set style data points
set pointsize 2
plot "graph.data" using 1:2 index 2 title "1000 documents", \
    "graph.data" using 1:2 index 2 title "" smooth bezier, \
    "graph.data" using 1:3 index 2 title "2000 documents", \
    "graph.data" using 1:3 index 2 title "" smooth bezier, \
    "graph.data" using 1:4 index 2 title "4000 documents", \
    "graph.data" using 1:4 index 2 title "" smooth bezier, \
    "graph.data" using 1:5 index 2 title "8000 documents", \
    "graph.data" using 1:5 index 2 title "" smooth bezier, \
    "graph.data" using 1:6 index 2 title "16000 documents", \
    "graph.data" using 1:6 index 2 title "" smooth bezier, \
    "graph.data" using 1:7 index 2 title "32000 documents", \
    "graph.data" using 1:7 index 2 title "" smooth bezier


# invscan
set terminal png size 1200,800
set output "invscan.png"
set grid
set xlabel "number of documents"
set ylabel "time (miliseconds)"
set xrange [0:40000]
set yrange [0:30000]
set style data points
set pointsize 2
plot "graph.data" using 1:2 index 3 title "", \
    "graph.data" using 1:2 index 3 title "" smooth bezier

# invsearch
set terminal png size 1200,800
set output "invsearch.png"
set grid
set xlabel "number of search terms"
set ylabel "time (miliseconds)"
set xrange [0:15]
set yrange [0:2.5]
set style data points
set pointsize 2
plot "graph.data" using 1:2 index 4 title "1000 documents", \
    "graph.data" using 1:2 index 4 title "" smooth bezier, \
    "graph.data" using 1:3 index 4 title "2000 documents", \
    "graph.data" using 1:3 index 4 title "" smooth bezier, \
    "graph.data" using 1:4 index 4 title "4000 documents", \
    "graph.data" using 1:4 index 4 title "" smooth bezier, \
    "graph.data" using 1:5 index 4 title "8000 documents", \
    "graph.data" using 1:5 index 4 title "" smooth bezier, \
    "graph.data" using 1:6 index 4 title "16000 documents", \
    "graph.data" using 1:6 index 4 title "" smooth bezier, \
    "graph.data" using 1:7 index 4 title "32000 documents", \
    "graph.data" using 1:7 index 4 title "" smooth bezier

