#!/bin/bash

#https://awk.readthedocs.io/en/latest/chapter-one.html
#数据emp.data 的文件，其中包含员工的姓名、薪资（美元/小时）以及小时数，一个员工一行数据

#1.打印出工作时间大于0的员工名字和工资
mode=`cat system.conf | awk -F = '{print$2}'`
echo $mode
