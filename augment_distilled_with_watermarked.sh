#!/bin/sh

# use this to convert image tags into relatiive path, alt label, and title if present

while read distilled_line
do
	PATH_EXTRACT=`echo $distilled_line | sed 's/\(@\).*//g' | sed 's/\(Pro\).*//g'`
	RESIDUAL=`echo $distilled_line | sed 's/.*\(@\)//g'`
	# echo $PATH_EXTRACT
	WATER="Water*"
	BIG_IMG="$PATH_EXTRACT$WATER"
        # echo $BIG_IMG	
	BIG_IMG_FILE=$(ls $BIG_IMG)
	if [ -z "$BIG_IMG_FILE" ]
	then
		echo "${SMALL_IMG_FILE}@$RESIDUAL"
	else
		echo "${BIG_IMG_FILE}@$RESIDUAL"
#		echo "${distilled_line}@$SMALL_IMG_FILE"
	fi
done < $1


