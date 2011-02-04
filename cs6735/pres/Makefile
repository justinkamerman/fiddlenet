# $Id$ 
# 
# $LastChangedDate$ 
# 
# $LastChangedBy$ 
#
SHELL = /bin/sh
LATEX = /usr/bin/latex
PDFLATEX = /usr/bin/pdflatex
PDFLATEXFLAGS = --interaction nonstopmode
BIBTEX = /usr/bin/bibtex
GNUPLOT = /usr/bin/gnuplot
IMAGES = roc.png

.PHONY: all clean

all: report.pdf

report.pdf: report.tex bibliography.bib $(IMAGES)


clean:
	$(RM) *.log *.blg *.bbl *.aux *.pdf


%.pdf: %.tex
	$(PDFLATEX) $(PDFLATEXFLAGS) $<
	-$(BIBTEX) $(BIBTEXFLAGS) $*
	$(PDFLATEX) $(PDFLATEXFLAGS) $<
	$(PDFLATEX) $(PDFLATEXFLAGS) $<


%.png: %.gnuplot %.data
	$(GNUPLOT) $<



