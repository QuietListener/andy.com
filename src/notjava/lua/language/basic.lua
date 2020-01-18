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

print("b=" .. tostring(b))
b = 10
print("b=" .. b)
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
print("aa" .. 1)
print(11 .. 22)

--字符串转换
s1 = "a12"
sn = tonumber(s1)
if (n == nil) then
    print(s1 .. " is not a number")
else
    print(s1 .. " is  a number" .. sn)
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
print("a['x']=" .. a["x"])
print("a[k]=" .. a[k])

k = 20
print("a['x']=" .. a["x"])
print("a[k]=" .. a[k])

ab = a --变量 ab 指向table
a = nil
print("ab['x']=" .. ab["x"])
print("ab[k]=" .. ab[k])

print("ab.x=" .. ab.x) --语法糖 ab["x"] 等价于 ab.x


print("********function********")
--[[
    在lua中函数式"一等公民"，函数可以存储在变量中，可以作为参数传递，函数可以返回函数。
 ]]



print("********表达式********")

--%取模
print("3%2 = " .. 3 % 2)
print("1.2345%1 = " .. tostring(1.2345 % 1)) --取小数部分
print("1.2345%0.01 = " .. tostring(1.2345 % 0.01)) --取小数部分后两位

-- 关系操作符
d1 = 1
print("d1 = " .. d1)
d3 = d1 == 1
if d3 then
    print("d1 == 1")
end

if d1 ~= 2 then
    print("d1 ~= 2")
end

-- nil 只能与自己相等
print(aaaaa == nil)


print("********表达式：逻辑操作符********")
--[[逻辑操作符有 and, or, not。 逻辑操作符将false和nil视作假，其他东西视作真。
    对and来说如果第一个操作为假，就返回第一个操作数.
    对or来说，如果第一个为操作数真，就返回第一个，否则返回第二个。
--]]

print(4 and 5) -- 5
print(nil and 12) --nil
print(false and 13) --false
print(4 or 5) --4
print(not nil)
print(not false)
print(not 0)
print(not not nil)

-- 等价于 if not x then x = 1 end;
x = x or 1


print("********表达式：table constructor********")
-- table初始化

days = { "Monday", "Tuesday", "Wednesday", "Thursday" }
print(days[1]) -- Monday 注意下表从1开始
print(#days) -- 返回长度


t1 = { x = 10, y = 20 }
print("t1['x'] = " .. t1["x"]) -- 10
print("t1.x =" .. t1.x) --10
t1.y = 30
print("t1['y'] = " .. t1["y"]);
print("#t1 = " .. #t1)


-- lua不能使用

t3 = {
    color = "red",
    point = 10; --可以用分号来分隔

    { x = 3, y = 0 },
    { x = -10, y = 2 },
    info = { name = "junjun", age = 10 }
}

print("t3[1].x = " .. t3[1].x) -- 3 注意下标从1开始
print("t3.info.name = " .. t3.info.name)

-- table允许提供通用表达式来作为索引
ttmp1 = 10
t4 = { ["-"] = 1, [ttmp1 + 2] = 10, [2 + 3] = 11 }
print(t4["-"])
print(t4[12])
print(t4[5])




print("********语句********")

print("********赋值********")
ss1 = 1
ss2 = 2

ss1, ss2 = ss2, ss1
print("ss1 = " .. ss1 .. "  ss2= " .. ss2)

ss3, ss4, ss5 = "a", 1

print("ss3, ss4, ss5:" .. ss3 .. ", " .. ss4 .. ", " .. tostring(ss5))