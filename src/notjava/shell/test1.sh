#====变量====

#!/bin/bash
echo "Hello World !"

#定义变量 变量名和等号之间不能有空格
your_name="runoob.com"

#使用一个定义过的变量，只要在变量名前面加美元符号即可，如：
echo ${your_name}
echo $your_name
echo "hello ${your_name}"


#for 循环
for file in $(ls .); do
    echo "file: ${file}"
done

for file in `ls .`; do
    echo "file: ${file}"
done

#只读变量
myUrl="baidu.com"
readonly myUrl
myUrl="google.com"

#删除变量 不能删除只读变量
url="ditu.baidu.com"
unset url
echo $url


#获取字符串长度
echo "\"${myUrl}\".length = ${#myUrl}"

#子字符串
string="runoob is a great site"
echo ${string:1:4} # 输出 unoo


#数组
array_name=(value0 value1 value2 value3)
echo ${array_name[0]} ${array_name[3]}
array_name[5]="value5"
echo ${array_name[5]}
#获取数组所有元素
echo ${array_name[@]}
echo "数组长度 ${#array_name[@]}"
