#!/usr/bin/perl -w


$SQLFILE = "blogpost.sql";
open(SQLFILE) or die ("Could not open sql file.");
my $linecount = 0;

while (<SQLFILE>) 
{
    # do line-by-line processing.
    if (/^ \(([0-9]+),'(.*?)','/)
    {
        $linecount++;
        my $id = $1;
        my $content = $2;

        open (POST, ">>../documents/real/$id.txt");
        print  (POST "$content");
        close (POST); 

        printf ("Wrote post $id\n");
    }
}

print ("Read $linecount lines\n");
