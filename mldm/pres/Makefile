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

.PHONY: all clean

all: report.pdf

report.pdf: report.tex bibliography.bib

clean:
	$(RM) *.log *.blg *.bbl *.aux *.pdf

%.dvi: %.tex
	$(LATEX) $(LATEXFLAGS) $<
	-$(BIBTEX) $(BIBTEXFLAGS) $*
	$(LATEX) $(LATEXFLAGS) $<
	$(LATEX) $(LATEXFLAGS) $<

%.pdf: %.tex
	$(PDFLATEX) $(PDFLATEXFLAGS) $<
	-$(BIBTEX) $(BIBTEXFLAGS) $*
	$(PDFLATEX) $(PDFLATEXFLAGS) $<
	$(PDFLATEX) $(PDFLATEXFLAGS) $<


%.ps: %.dvi
	$(DVIPS) $(DVIPSFLAGS) $@ $<



