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
    count = 0
    for k in pairs(tt) do --pairs 返回table原生迭代器
        if type(tt[k]) == "table" then
            printTable(tt[k], "sub" .. count + 1)
        else
            print(k .. "->" .. tostring(tt[k]))
        end
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
    print(string.format("count = %d, arg2 = %s", count, argi))

    for i, v in ipairs(v) do
        s = s + v;
    end

    return s;
end

r = add(1, 2, 3)
print("r = " .. r)



print("---深入函数---")
--[[
--1.函数像变量一样，是"一等公明",
-- a.函数可以存到变量，table中，
-- b.也可以作为参数传给其他函数，
-- c.还可以作为其他函数返回值
--2.函数有特定的词法域:一个函数可以嵌套在另一个函数中，内部函数可以访问外部函数的变量。支持函数式编程(Lua包含Lambda Caculus)
 ]]


a = { p = print }
a.p("hello")

print = math.sin
a.p(print(1))
print = a.p --还是还原下面好使用




print("----函数----")
--[[函数式"值"，函数就是由一些表达式创建的"值"
function foo(x) return 2 * x end
上面这个定义其实只是语法糖真正的书写形式是：
foo = function(x) return 2 * x end

一个函数定义其实就是一条赋值语句，这条语句创建了一个类型为"函数"的值，并将其复制给一个变量(foo)
function(x) <body> end 和 {} 一样，前者是函数的构造式，后者是table的构造式。

]]





print("--函数:匿名函数 高阶函数--")

network = {
    { name = "a", ip = "1.2.3.4" },
    { name = "c", ip = "1.2.3.5" },
    { name = "b", ip = "1.2.3.6" }
}

table.sort(network, function(a, b) return a.name > b.name end)
printTable(network)

--[[
-- sort 可以接受一个函数 function(a, b) return a.name > b.name end 作为参数，这个函数式匿名函数
-- sort这样可以接受一个函数作文实参的函数叫"高阶函数"
]]


-- 使用高阶函数求函数 f(x)导数
function derivate(f, delta)
    delta = delta or 1e-4
    return function(x)
        return (f(x + delta) - f(x)) / delta;
    end
end

--sina'10 = cos10
derivate_sin = derivate(math.sin)
print("math.cos = " .. math.cos(10) .. " sin'(10) = " .. derivate_sin(10))








print("--函数:闭合函数 closure--")
--[[ 内部函数可以返回外部函数的局部变量，这个特性叫"词法域"
-- ]]

function newCounter()
    local i = 0;
    return function()
        i = i + 1;
        return i;
    end
end

counter1 = newCounter()
print("counter1 = " .. counter1())
print("counter1 = " .. counter1())
counter2 = newCounter();
print("counter2 = " .. counter2())
print("counter2 = " .. counter2())
print("counter1 = " .. counter1())


--[[
function newCounter()
    local i = 0;
    return function()
        i = i + 1;
        return i;
    end
end
这段代码中匿名函数访问了一个"非局部变量"i,i保持了一个计数器。当newCounter返回后，每次调用
匿名函数时候，好像i都超出了作用范围，但是其实不然。Lua的closure会处理这种情况。
closure = 函数+该函数所需访问的所有"非局部变量"。如果再调用newCounter会创建一个新的局部变量i，
也会得到一个新的closure。
-- ]]


print("--- -函数：函数 vs closure---")
--[[函数只是一种特殊的closure, lua中其实只有closure没有函数这个概念]]


print("--- -函数：非全局函数---")
print("--- -函数：非全局函数 存table中---")
Lib = {}
Lib.foo = function(x, y) return x + y end
Lib.goo = function(x, y) return x - y end

print(Lib.foo(1, 2));

--等价于--
Lib = {
    foo1 = function(x, y) return x + y end,
    goo1 = function(x, y) return x - y end
}
print(Lib.foo1(1, 2));
--等价于 语法糖--
function Lib.foo2(x, y) return x + y end

function Lib.goo2(x, y) return x - y end

print(Lib.foo1(1, 2));


print("--- -函数：非全局函数 局部函数---")

local foo3 = function(x, y) return x + y end --语法糖local function foo3(x, y) return x + y end
local foo4 = function(x, y)
    local r = x + y
    return r + foo3(x, y) -- foo3 可见
end

print(foo4(1, 2))

print("--- -函数：非全局函数 局部函数---")

--[[
local fact1 = function (n)
    if n == 0 then
        return 1;
    else
        return n * fact1(n - 1)
    end
end

print("fact1(6):" .. fact1(3))
这段代码有问题，因为在递归调用fact1的时候fact1还没有初始完。
可以改成 下面的。
]]

local fact1
fact1 = function(n)
    if n == 0 then
        return 1;
    else
        return n * fact1(n - 1)
    end
end

print("fact1(6):" .. fact1(3))

--下面是语法糖 lua会将其变为上面的形式
local function fact2(n)
    if n == 0 then
        return 1;
    else
        return n * fact2(n - 1)
    end
end

print("fact2(6):" .. fact1(3))



print("---函数：尾调用，尾递归，尾调用优化--")
--[[
尾调用 类似于 goto语句，当一个函数调用是另一个函数的最后一个动作时候，该调用才叫"尾调用"
例如
function f(x)
    return g(x+1);
end

尾调用优化，
函数调用是一个"栈模型"，一般情况下，函数f在调用函数g的时候， 会保留f的堆栈，g有可能会调用其他函数
所以每调用一次都会有一个堆栈出现，形成一个"调用栈"，有的语言对调用栈的深度是有规定的，例如java。

但是如果是尾调用的情况下：
在调用 return g(x)的时候，已经不需要f(x)的栈了，就可以删除f(x)的堆栈，直接调用g(x+1)。这就是尾调用优化。
尾递归也是一样的道理。 ES6，C++也支持这种优化了。


return g(x)+1
return x or g(x)
return (g(x))都不是尾调用。
]]




print("---迭代器与泛型for---")
--[[迭代器就是可以访问一个集合所有元素的机制，lua中将迭代器表示为函数~，每调用一次返回集合中"下一个"元素]]

print("---迭代器与泛型for：closure---")
function values(t)
    local i = 0;
    return function() i = i + 1; return t[i] end
end

--泛型for，在内部保存迭代器函数，在函数返回nil时候结束
t = { 10, 20, 30 }
for element in values(t) do
    print(element)
end



print("---迭代器与泛型for：泛型语义---")
--[[
 for <var-list> in <exp-list> do
    <body>
 end
 泛型for保存了3个值，一个是迭代器函数，一个是恒定状态，一个是控制变量。
 for做的过程如下:
 1.对in后面的表达式进行求值。
 2.这些表达式求值，返回3个值给for保存:一个是迭代器函数，一个是恒定状态，一个是控制变量的初始值。
]]

--当使用较为简单的迭代器的时候,返回一个迭代器函数即可，恒定状态和控制变量都为nil ，如下values1所示。
function values1(t)
    local i = 0;
    return function() i = i + 1; return t[i], i end, nil, nil
end


t = { 10, 20, 30 }
for element, idx in values1(t) do
    print(string.format("count = %d, arg2 = %s", idx, element))
end


print("----------")


do
    local _f, _s, _var = values1(t)
    while true do
        local _var1, _var2 = _f(_s, _var)
        _var = _var1
        if _var == nil then break; end;
        print(string.format("count = %d, arg2 = %s", _var2, _var1))
    end
end


print("---迭代器与泛型for：无状态迭代器---")
--[[
上面的 迭代工厂函数
function values1(t)
    local i = 0;
    return function() i = i + 1; return t[i], i end,nil,nil
end
会有一个状态变量 例如i
像 ipairs 这种没有一个状态变量。
-- ]]

local function iter(a, i)
    i = i + 1;
    local v = a[i]
    if v then
        return i, v, i + 1 --可以返回任意多个
    end;
end

function myIpairs(a)
    return iter, a, 0 --a 为恒定状态，0为控制变量初始值
end

t = { "a", "b", "c" }
for idx, element, idx1 in myIpairs(t) do
    print(string.format("idx = %s, element = %s , idx1 = %s", idx, element, idx1))
end


print("--pairs一-")
--next(table,key) 返回table key对应的"下一个"key和value，当key为nil时候返回第一个，当最后一个的时候返回nil
t2 = { a = "aa", bb = "bb", cc = "cc" }
kk, vv = next(t2, nil)
print(string.format("kk=%s ,vv=%s", kk, vv))
kk, vv = next(t2, kk)
print(string.format("kk=%s ,vv=%s", kk, vv))
kk, vv = next(t2, kk)
print(string.format("kk=%s ,vv=%s", kk, vv))
kk, vv = next(t2, kk)
print(string.format("kk=%s ,vv=%s", kk, vv))

print("---- ----")
function myPairs(a)
    return next, a, nil
end

for kk, vv in myPairs(t) do
    print(string.format("kk=%s ,vv=%s", kk, vv))
end