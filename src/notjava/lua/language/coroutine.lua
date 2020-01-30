print("--- 协程 ---")
--[[
   lua将协程函数放在一个名为coroutine的table中
   使用coroutine.create 创建协程，只有一个参数:一个函数
]]

co1 = coroutine.create(function() print("hi ") end)
print(co1) --是一个thread

--[[协程有四种状态:supended(挂起), running(运行)，dead(死亡)，normal(正常)]]
print(coroutine.status(co1)) -- 初始状态是supendeded
--使用coroutine.resume(co1) 启动或者再次启动一个协程
coroutine.resume(co1)
print(coroutine.status(co1)) -- dead状态
status, msg = coroutine.resume(co1)
print("" .. tostring(status) .. ":" .. msg) --dead状态的协程不能再重启。 false:cannot resume dead coroutine

print("--- 协程：yield ---")
--[[
    yield会将一个正在运行的协程挂起，之后可以在恢复运行
    coroutine.resume在保护模式中运行，如果出现错误不打印信息，将执行权返回给resume
]]

co2 = coroutine.create(function()
    for i = 1, 3 do
        print("co2", i);
        coroutine.yield();
    end
end)

print(coroutine.status(co2)) -- 初始状态是supendeded
coroutine.resume(co2) -- co2     1
print(coroutine.status(co2)) -- 初始状态是supendeded
coroutine.resume(co2) --co2     2
coroutine.resume(co2) --co2     3
coroutine.resume(co2) --false 不打印


print("--- 协程：yield normal状态,协程的参数 ---")
--[[如果一个协程A唤醒另一个协程B，协程A处于一种既不是running也不是suspend的状态:特殊状态(normal)]]

co3 = coroutine.create(function(a, b)
    print("co3", a, b) -- co3     1       2
    coroutine.yield(a + 1, b + 1); --yield的返回值作为resume的返回值
end)

print(coroutine.resume(co3, 1, 2)) -- true    2       3


co4 = coroutine.create(function(a, b)
    print("co3", a, b) -- co3     1       2
    coroutine.yield(a + 1, b + 1); --yield的返回值作为resume的返回值
end)

print(coroutine.resume(co3, 1, 2)) -- true    2       3

print("--- 协程：对称协程和非对称协程 ---")
--[[
lua 提供两个命令来实现协程: resume和yield是半对称协程
像 golang只提供一个命令 go来实现协程，叫做对称协程。
其实就像自动档位和手动挡一样，lua这种半对称协程，将协程的调度交给了程序员控制。而go这种完全交给语言(调度器)来控制。

半对称的可以连续调用yield和resume就可以实现对称协程了。
]]



local producer1 = coroutine.create(function()
    while true do
        local x = io.read();
        print("producer1", x);
        send(x);
    end
end)

local consumer1 = coroutine.create(function()
    while true do
        local x = receive()
        print("consumer1", x);
    end
end)

function receive()
    local status, data = coroutine.resume(producer1);
    return data
end

function send(x)
    coroutine.yield(x);
end

--coroutine.resume(producer1)
--assert(coroutine.resume(consumer1))


print("------filter--------")

function producer2()
    return coroutine.create(function()
        while true do
            local x = io.read();
            print("producer2", x);
            send1(x);
        end
    end);
end

function consumer2(prod)
    while true do
        local x = receive1(prod)
        print("consumer2", x);
    end
end

function filter(prod)
    return coroutine.create(function()
        while true do
            local x = receive1(prod)
            send1("filter:" .. x);
        end
    end);
end

function receive1(prod)
    local status, data = coroutine.resume(prod);
    return data
end

function send1(x)
    coroutine.yield(x);
end

consumer2(filter(producer2()))