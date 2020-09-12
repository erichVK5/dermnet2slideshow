#!/bin/sh

# we use this to extract the image tags from the html files listed in $1
# and exclue rubbish content

while read html_line
do
	cat $html_line | grep "img src=\"" | sed -e 's/^[ \t]*//' | sed 's@</div>@@g' | grep -v "quicksilver" | grep -v "sidebar-images" | grep -v "self-examination" | grep -v "logo" | grep -v "amazon-adsystem.com" | grep "__Protect"
done < $1	
