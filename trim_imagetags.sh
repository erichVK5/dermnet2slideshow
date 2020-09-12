#!/bin/sh

# use this to convert image tags into relatiive path, alt label, and title if present

cat $1 | sed 's@<img src="/@@g' | sed 's/" alt=/@/g' | sed 's@/>@@g' | sed 's/>//g' | sed 's/ title=/@/g'
