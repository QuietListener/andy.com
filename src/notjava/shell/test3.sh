#===传参数===

#!/bin/bash

#传递参数 bash test2.sh 1 2 3
echo "Shell 传递参数实例！";
echo "执行的文件名：$0";
echo "参数个数为: $#"

#$* 与 $@ 区别：
#相同点：都是引用所有参数。
#不同点：只有在双引号中体现出来。假设在脚本运行时写了三个参数 1、2、3，，则 " * " 等价于 "1 2 3"（传递了一个参数），而 "@" 等价于 "1" "2" "3"（传递了三个参数）。
echo "所有参数作为一个字符串显示: $*"
echo "所有参数 $@ "

echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";

`afasf -lahs  .`
echo "最后命令退出状态 $?" #0表示没有错误，其他任何值表明有错误。