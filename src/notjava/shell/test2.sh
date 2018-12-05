#===运算符==== http://www.runoob.com/linux/linux-shell-basic-operators.html
#!/bin/bash

#表达式和运算符之间要有空格，例如 2+2 是不对的，必须写成 2 + 2，这与我们熟悉的大多数编程语言不一样。
v=`expr 2 + 2`
echo "2+2=${v}"


#运算符
a=10
b=20
val=`expr $a + $b`
echo "a+b=${val}"

#注意转义
val=`expr $a \* $b`
echo "a * b : $val"


if [ $a -lt $b ];then
    echo "$a 小于 $b"
fi
