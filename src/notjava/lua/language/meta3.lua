--
-- Created by IntelliJ IDEA.
-- User: junjun
-- Date: 2020/2/7
-- Time: 11:13 AM
--

print("----元表，元数据----")
mt = {}
print("{} metatable:")
print(getmetatable(mt))

--设置metatable
mt1 = {}
setmetatable(mt, mt1)
print(getmetatable(mt) == mt1)

--[[任何table都可以作为任何值的元表，一组值也可以共享相关元表，一个table可以作为自己的原表
-- lua只能设置table的元表，如果设置其他类型的元表，只能通过c代码。
-- ]]



print("__index")
print("当一个table不存在一个字段时候，table会在去找一个叫__index方法，\r\n如果没有这个元方法，返回nil，如果有返回__index的结果")

Window = {}
Window.prototype = { x = 0, y = 0, width = 100, height = 100 }
Window.mt = {}

function Window.new(o)
    setmetatable(o, Window.mt)
    return o;
end

Window.mt.__index = function(table, key)
    return Window.prototype[key];
end

w1 = Window.new({ x = 10 })
w2 = Window.new({ y = 1 })
w2["width"] = 123
print("w1.x = " .. w1.x .. " w1.y = " .. w1.y)
print("w2.x = " .. w2.x .. " w2.y = " .. w2.y .. " w2.width = " .. w2.width)


print("----有默认值的table----")
function setDefault(t, d)
    local mt_ = { __index = function() return d end }
    setmetatable(t, mt_)
end

tab1 = { x = 10, y = 10 }
print(tab1.x, tab1.y)
setDefault(tab1, 0) -- 原来的默认值为nil
print(tab1.x, tab1.y, tab1.z)

print("----跟踪table访问 代理table----")



at = {}
local _t = at
at = {}
local mt = {
    __index = function(t, k)
        print("*access to  element " .. tostring(k))
        return _t[k]
    end,
    __newindex = function(t, k, v)
        print("*update of element " .. tostring(k) .. " to " .. tostring(v))
        _t[k] = v
    end
}



setmetatable(at,mt)
at[2] = 1
print(at[2])


print("----只读table访问 ，上面 __newindex 抛异常即可----")