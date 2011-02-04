# $Id: Makefile 53 2010-11-16 19:32:58Z justinkamerman $ 
# 
# $LastChangedDate: 2010-11-16 15:32:58 -0400 (Tue, 16 Nov 2010) $ 
# 
# $LastChangedBy: justinkamerman $ 
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



