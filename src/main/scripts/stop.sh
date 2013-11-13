#!/bin/sh

sh_path=`dirname $(pwd)/$0`
cd $sh_path

files=`find ${dir} -maxdepth 1 -name "*.jar" -or -name "*.sh" -not -name "stop.sh" -not -name "startup.sh"`

echo "Try to kill process as below:"
for file in ${files}
do
  
echo $file
ps gaux | grep `basename $file` | grep -v grep | awk '{print "kill -9 " $2}' | sh
done

