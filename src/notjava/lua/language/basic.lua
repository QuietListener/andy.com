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

print("********语句：赋值********")
ss1 = 1
ss2 = 2

ss1, ss2 = ss2, ss1
print("ss1 = " .. ss1 .. "  ss2= " .. ss2)

ss3, ss4, ss5 = "a", 1

print("ss3, ss4, ss5:" .. ss3 .. ", " .. ss4 .. ", " .. tostring(ss5))


print("********语句：局部变量与块(block)********")


bx = 3 --全局变量
local bli = 1 --局部变量

while bli <= bx do
    local bx = bli * 2
    print(bx) -- 2,4,6
    bli = bli + 1
end

print("bx = " .. bx) -- 3 全局变量不变
print("bli = " .. bli) -- 4 局部变量


--控制局部变量的作用域可以将其放在 do-end之中

x = 1
do
    local x = x --全局的x赋值给局部的x
    local a = 2

    x = x + 1 + a;
    print(x) --2
end

print(x) --1
print(a) --nil



print("********语句：控制结构********")
-- if, while,repeat,for end;  repeat以until结尾。
a = 2
if a == 1 then
    print("1")
elseif a == 2 then
    print("2")
else
    print("other")
end

print("----")
b = 3
local i = 1;
while i < b do
    print(i)
    i = i + 1
end

print("----")
b = 3
local j = 1;
repeat
    print(j)
    j = j + 1
until j >= b

print("----")
-- 2是步长 可以不提供
for ii = 1, 5, 2 do
    print(ii)
end
print(ii) -- nil ii是局部变量

print("----")
for i = 1, -1, -1 do
    print(i)
end



print("---泛型for---")
a = { 1, 2, "a", 4, 5 }
for i, k in ipairs(a) do --ipairs返回数组迭代器
    print(i .. "->" .. k)
end

print("---")
a = { 1, 2, x = "a", y = 3 }
for k in pairs(a) do --pairs 返回table原生迭代器
    print(k .. "->" .. a[k])
end

print("---beak return--")

for i = 1, 3 do
    print("i = " .. i)
    for j = 1, 3 do
        if i >= 2 and j == 1 then
            break;
        end
        print("   j = " .. j)
    end
end



print("---return--")
function foo()
    return
    print("aaa")
end

foo();

print("********函数********")
--[[ Lua中函数式一等公民
      函数式一种对语句和表达式进行抽象的主要机制。
      1.函数既可以完成某些特定的任务，这种情况函数被视为一条语句；
      2. 函数也可以制作一些计算并返回结果，这将其视为一句表达式。
-- ]]


--函数调用，参数必须有一对款括号中，如果没有参数也需要有圆括号。如果只有一个参数，如果这个参数是一个"字面字符串"或table 那就可以不用括号

a = "dddd"
--print a 报错
print "dddd"
print [[dafdasfd]]

function f(v)
    print(v)
end

f { x = 1, y = "a" } --f({x=1,y="a" })

--函数:形参实参 更多重复制相似，实参多于形参 舍弃多余实参，实参不住，就将多余行参初始化为nil
function ff(a, b) return a or b end

ff(3) -- 实参a = 3 ,b =nil
ff(3, 4) -- 实参a = 3 ,b =4
ff(3, 4, 5) -- 实参a = 3 ,b =4, （5被丢掉）

--函数:多重返回
-- maximum返回数组最大元素与其index
function maximum(a)
    local mi = 1;
    local tmp = a[1]
    for i, v in ipairs(a) do
        if v > tmp then
            mi = i;
            tmp = v;
        end
    end

    return tmp, mi
end

a = { 1, 2, 3, 4, 3, 2 }

v, index, other = maximum(a)
print("item=" .. v .. " index = " .. index .. " other = " .. tostring(other))

--函数:返回的坑
function foo0() end

function foo1() return "a" end

function foo2() return "b", "c" end

--function foo2_() return ("a","b") end

--如果一个函数调用不是一系列表达式的最后一个原生，那么只产生一个值
x, y = foo2(), 2
print("x=" .. x .. "  y=" .. y) --x=b,y=2  y不是c
x, y, z = 2, foo2()
print("x=" .. x .. "  y=" .. y .. " z=" .. z) --x=2  y=b z=c

--函数调用也一样
print(foo2(), 2) --b 2
print(2, foo2()) --2 b c
print(foo2() .. "x") --当foo2出现再表达式中，Lua将其返回值的量调整为1

--table也一样
t0 = { foo0() }
t1 = { foo1() }
t2 = { foo2() }
t3 = { foo2(), 4 }
t4 = { 4, foo2(), 5 }
function printTable(tt, tip)
    print(tip)
    for k in pairs(tt) do --pairs 返回table原生迭代器
        print(k .. "->" .. tt[k])
    end
end

printTable(t0, "t0:")
printTable(t1, "t1:")
printTable(t2, "t2:")
printTable(t3, "t3:")
printTable(t4, "t4:")

--圆括号迫使返回第一个值
a = (foo2())
print(a) --b


--unpack 接受一个数组，并从1开始返回所有元素；这很重要，可以实现泛型调用，可以使用任何实参来调用任何函数。
--可以给函数f的传入任意参数unpack(a): f(table.unpack(a))
print("--unpack---")
arr1 = { 1, 2, 3, 4, 3 }
print(table.unpack(arr1))
a, b = table.unpack(arr1)
print(a, b)





--变长参数
print("---变长参数---")

-- 三个点可以看做是一个有多重返回的函数
function add(...)
    print("... = ", ...)
    local s = 0;
    local v = { ... };
    printTable(v)

    local count = select("#", ...) -- select("#",可以返回变长参数个数)
    local argi = select(2, ...) --返回第2个参数
    print(string.format("count = %d, arg2 = %s",count,argi))

    for i, v in ipairs(v) do
        s = s + v;
    end

    return s;
end

r = add(1, 2, 3)
print("r = " .. r)

