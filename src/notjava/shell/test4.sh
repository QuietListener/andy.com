#====变量====

#!/bin/bash

watch_log="text.txt"
last_line=""
previous_line=""

i=0
#最多30s
while [ $i -le 30 ]
do
    last_line=`tail -n 1 $watch_log`
    previous_line=`tail -n 1 _previous.txt`

    if [ $last_line == $previous_line ];then
        echo "${i} eq"
        break
    else
        echo "${i} no eq"
    fi

    sleep 1
    let i++
    `echo $last_line > _previous.txt`
done


echo "sleep 2"
sleep 2
echo "awake "

