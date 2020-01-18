-- 注释hello world
print("hello world")

--[[
阶乘函数
]]
function fact(n)
    if n == 0 then
        return 1;
    else
        return n * fact(n - 1)
    end
end

--[[可以加";"也可以不加]]
--[[
print("enter a number:")
a = io.read("*number") --读一个数字
print("result:");
print(fact(a))
]]



-- 全局变量
--[[
全局变量不需要声明，只需要将一个全局变量就创建了
如果要删除某个全局变量的话只需要赋值为nil
]]

print("b=".. tostring(b))
b = 10
print("b="..b)
b = nil


--[[ 8种类型
    1. nil  空
    2. boolean 布尔
    3. number 数字
    4. string 字符串
    5. userdata 自定义类型
    6 function 函数
    7. thread 线程
    8. table 表
   使用 type返回类型的名称
]]

print("********类型********")
print(c)
print(type(c)) --nil 未初始化

c = 10
print(c .. type(c)) --number

c = "change to string"
print(c .. type(c)) -- string

c = print
print("function" .. type(c)) -- function


print("********boolean********")
bb = true
bd = nil
bc = 0
be = ""

print(bb == true)

if bc then -- 0被认为是true
    print("bc is true")
else
    print("bc is false")
end

if be then -- 空字符串被认为是true
    print("be is true")
else
    print("be is false")
end

print("********number********")
--[[
number表示实数，Lua没有整数类型。
]]

print(type(4))
print(0.4)
print(4.5e-3)
print(0.1e12)




print("********string********")
--[[
 lua的字符串是不可变的。使用双引号或者单引号
]]

--字符串连接
print("aa"..1)
print(11 .. 22)

--字符串转换
s1 = "a12"
sn = tonumber(s1)
if(n == nil) then
    print(s1.." is not a number")
else
    print(s1.." is  a number"..sn)
end

print(tostring("10") == "10")

-- 字符串长度
s2 = "hello"
print("s2.length = " .. #s2)
s3 = "你好hey"
print("s3.length = " .. #s3)

print("********table********")
a = {}
k = "x"
a[k] = 10
a[20] = "great"
print("a['x']="..a["x"])
print("a[k]="..a[k])

k = 20
print("a['x']="..a["x"])
print("a[k]="..a[k])

ab = a   --变量 ab 指向table
a = nil
print("ab['x']="..ab["x"])
print("ab[k]="..ab[k])

print("ab.x="..ab.x) --语法糖 ab["x"] 等价于 ab.x

