local ratelimit_info = redis.pcall('HMGET',KEYS[1],'last_time','current_token')
local last_time = ratelimit_info[1]
local current_token = tonumber(ratelimit_info[2])
local max_token = tonumber(ARGV[1])
local token_rate = tonumber(ARGV[2])

local nowInfo = redis.call('TIME')
local current_time = nowInfo[1]

if current_token == nil then
  current_token = max_token
  last_time = current_time
else
  local past_time = current_time-last_time
  local reverse_token = math.floor(past_time/reverse_time)
  current_token = current_token+reverse_token
  last_time = reverse_time*reverse_token+last_time
  if current_token>max_token then
    current_token = max_token
  end
end
local result = 0
if(current_token>0) then
  result = 1
  current_token = current_token-1
end
redis.call('HMSET',KEYS[1],'last_time',last_time,'current_token',current_token)
redis.call('pexpire',KEYS[1],math.ceil(reverse_time*(max_token-current_token)+(current_time-last_time)))
return result