/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

#import <Foundation/Foundation.h>

#import "TProtocol.h"
#import "TApplicationException.h"
#import "TProtocolException.h"
#import "TProtocolUtil.h"
#import "TProcessor.h"
#import "TObjective-C.h"
#import "TBase.h"
#import "TAsyncTransport.h"
#import "TProtocolFactory.h"
#import "TBaseClient.h"


#import "simple.h"


@implementation simpleConstants
+ (void) initialize {
}
@end

@interface multiply_args : NSObject <TBase, NSCoding> {
  int __n1;
  int __n2;

  BOOL __n1_isset;
  BOOL __n2_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=n1, setter=setN1:) int n1;
@property (nonatomic, getter=n2, setter=setN2:) int n2;
#endif

- (id) init;
- (id) initWithN1: (int) n1 n2: (int) n2;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (int) n1;
- (void) setN1: (int) n1;
#endif
- (BOOL) n1IsSet;

#if !__has_feature(objc_arc)
- (int) n2;
- (void) setN2: (int) n2;
#endif
- (BOOL) n2IsSet;

@end

@implementation multiply_args

- (id) init
{
  self = [super init];
#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
#endif
  return self;
}

- (id) initWithN1: (int) n1 n2: (int) n2
{
  self = [super init];
  __n1 = n1;
  __n1_isset = YES;
  __n2 = n2;
  __n2_isset = YES;
  return self;
}

- (id) initWithCoder: (NSCoder *) decoder
{
  self = [super init];
  if ([decoder containsValueForKey: @"n1"])
  {
    __n1 = [decoder decodeInt32ForKey: @"n1"];
    __n1_isset = YES;
  }
  if ([decoder containsValueForKey: @"n2"])
  {
    __n2 = [decoder decodeInt32ForKey: @"n2"];
    __n2_isset = YES;
  }
  return self;
}

- (void) encodeWithCoder: (NSCoder *) encoder
{
  if (__n1_isset)
  {
    [encoder encodeInt32: __n1 forKey: @"n1"];
  }
  if (__n2_isset)
  {
    [encoder encodeInt32: __n2 forKey: @"n2"];
  }
}

- (NSUInteger) hash
{
  NSUInteger hash = 17;
  hash = (hash * 31) ^ __n1_isset ? 2654435761 : 0;
  if (__n1_isset)
  {
    hash = (hash * 31) ^ [@(__n1) hash];
  }
  hash = (hash * 31) ^ __n2_isset ? 2654435761 : 0;
  if (__n2_isset)
  {
    hash = (hash * 31) ^ [@(__n2) hash];
  }
  return hash;
}

- (BOOL) isEqual: (id) anObject
{
  if (self == anObject) {
    return YES;
  }
  if (![anObject isKindOfClass:[multiply_args class]]) {
    return NO;
  }
  multiply_args *other = (multiply_args *)anObject;
  if ((__n1_isset != other->__n1_isset) ||
      (__n1_isset && (__n1 != other->__n1))) {
    return NO;
  }
  if ((__n2_isset != other->__n2_isset) ||
      (__n2_isset && (__n2 != other->__n2))) {
    return NO;
  }
  return YES;
}

- (void) dealloc
{
  [super dealloc_stub];
}

- (int32_t) n1 {
  return __n1;
}

- (void) setN1: (int32_t) n1 {
  __n1 = n1;
  __n1_isset = YES;
}

- (BOOL) n1IsSet {
  return __n1_isset;
}

- (void) unsetN1 {
  __n1_isset = NO;
}

- (int32_t) n2 {
  return __n2;
}

- (void) setN2: (int32_t) n2 {
  __n2 = n2;
  __n2_isset = YES;
}

- (BOOL) n2IsSet {
  return __n2_isset;
}

- (void) unsetN2 {
  __n2_isset = NO;
}

- (void) read: (id <TProtocol>) inProtocol
{
  NSString * fieldName;
  int fieldType;
  int fieldID;

  [inProtocol readStructBeginReturningName: NULL];
  while (true)
  {
    [inProtocol readFieldBeginReturningName: &fieldName type: &fieldType fieldID: &fieldID];
    if (fieldType == TType_STOP) { 
      break;
    }
    switch (fieldID)
    {
      case 1:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setN1: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      case 2:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setN2: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      default:
        [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        break;
    }
    [inProtocol readFieldEnd];
  }
  [inProtocol readStructEnd];
}

- (void) write: (id <TProtocol>) outProtocol {
  [outProtocol writeStructBeginWithName: @"multiply_args"];
  if (__n1_isset) {
    [outProtocol writeFieldBeginWithName: @"n1" type: TType_I32 fieldID: 1];
    [outProtocol writeI32: __n1];
    [outProtocol writeFieldEnd];
  }
  if (__n2_isset) {
    [outProtocol writeFieldBeginWithName: @"n2" type: TType_I32 fieldID: 2];
    [outProtocol writeI32: __n2];
    [outProtocol writeFieldEnd];
  }
  [outProtocol writeFieldStop];
  [outProtocol writeStructEnd];
}

- (void) validate {
  // check for required fields
}

- (NSString *) description {
  NSMutableString * ms = [NSMutableString stringWithString: @"multiply_args("];
  [ms appendString: @"n1:"];
  [ms appendFormat: @"%i", __n1];
  [ms appendString: @",n2:"];
  [ms appendFormat: @"%i", __n2];
  [ms appendString: @")"];
  return [NSString stringWithString: ms];
}

@end

@interface Multiply_result : NSObject <TBase, NSCoding> {
  int __success;

  BOOL __success_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, getter=success, setter=setSuccess:) int success;
#endif

- (id) init;
- (id) initWithSuccess: (int) success;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (int) success;
- (void) setSuccess: (int) success;
#endif
- (BOOL) successIsSet;

@end

@implementation Multiply_result

- (id) init
{
  self = [super init];
#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
#endif
  return self;
}

- (id) initWithSuccess: (int) success
{
  self = [super init];
  __success = success;
  __success_isset = YES;
  return self;
}

- (id) initWithCoder: (NSCoder *) decoder
{
  self = [super init];
  if ([decoder containsValueForKey: @"success"])
  {
    __success = [decoder decodeInt32ForKey: @"success"];
    __success_isset = YES;
  }
  return self;
}

- (void) encodeWithCoder: (NSCoder *) encoder
{
  if (__success_isset)
  {
    [encoder encodeInt32: __success forKey: @"success"];
  }
}

- (NSUInteger) hash
{
  NSUInteger hash = 17;
  hash = (hash * 31) ^ __success_isset ? 2654435761 : 0;
  if (__success_isset)
  {
    hash = (hash * 31) ^ [@(__success) hash];
  }
  return hash;
}

- (BOOL) isEqual: (id) anObject
{
  if (self == anObject) {
    return YES;
  }
  if (![anObject isKindOfClass:[Multiply_result class]]) {
    return NO;
  }
  Multiply_result *other = (Multiply_result *)anObject;
  if ((__success_isset != other->__success_isset) ||
      (__success_isset && (__success != other->__success))) {
    return NO;
  }
  return YES;
}

- (void) dealloc
{
  [super dealloc_stub];
}

- (int32_t) success {
  return __success;
}

- (void) setSuccess: (int32_t) success {
  __success = success;
  __success_isset = YES;
}

- (BOOL) successIsSet {
  return __success_isset;
}

- (void) unsetSuccess {
  __success_isset = NO;
}

- (void) read: (id <TProtocol>) inProtocol
{
  NSString * fieldName;
  int fieldType;
  int fieldID;

  [inProtocol readStructBeginReturningName: NULL];
  while (true)
  {
    [inProtocol readFieldBeginReturningName: &fieldName type: &fieldType fieldID: &fieldID];
    if (fieldType == TType_STOP) { 
      break;
    }
    switch (fieldID)
    {
      case 0:
        if (fieldType == TType_I32) {
          int32_t fieldValue = [inProtocol readI32];
          [self setSuccess: fieldValue];
        } else { 
          [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        }
        break;
      default:
        [TProtocolUtil skipType: fieldType onProtocol: inProtocol];
        break;
    }
    [inProtocol readFieldEnd];
  }
  [inProtocol readStructEnd];
}

- (void) write: (id <TProtocol>) outProtocol {
  [outProtocol writeStructBeginWithName: @"Multiply_result"];

  if (__success_isset) {
    [outProtocol writeFieldBeginWithName: @"success" type: TType_I32 fieldID: 0];
    [outProtocol writeI32: __success];
    [outProtocol writeFieldEnd];
  }
  [outProtocol writeFieldStop];
  [outProtocol writeStructEnd];
}

- (void) validate {
  // check for required fields
}

- (NSString *) description {
  NSMutableString * ms = [NSMutableString stringWithString: @"Multiply_result("];
  [ms appendString: @"success:"];
  [ms appendFormat: @"%i", __success];
  [ms appendString: @")"];
  return [NSString stringWithString: ms];
}

@end

@implementation MultiplicationServiceClient
- (id) initWithProtocol: (id <TProtocol>) protocol
{
  return [self initWithInProtocol: protocol outProtocol: protocol];
}

- (id) initWithInProtocol: (id <TProtocol>) anInProtocol outProtocol: (id <TProtocol>) anOutProtocol
{
  self = [super init];
  inProtocol = [anInProtocol retain_stub];
  outProtocol = [anOutProtocol retain_stub];
  return self;
}

- (void) send_multiply: (int) n1 n2: (int) n2
{
  [outProtocol writeMessageBeginWithName: @"multiply" type: TMessageType_CALL sequenceID: 0];
  [outProtocol writeStructBeginWithName: @"multiply_args"];
  [outProtocol writeFieldBeginWithName: @"n1" type: TType_I32 fieldID: 1];
  [outProtocol writeI32: n1];
  [outProtocol writeFieldEnd];
  [outProtocol writeFieldBeginWithName: @"n2" type: TType_I32 fieldID: 2];
  [outProtocol writeI32: n2];
  [outProtocol writeFieldEnd];
  [outProtocol writeFieldStop];
  [outProtocol writeStructEnd];
  [outProtocol writeMessageEnd];
}

- (int) recv_multiply
{
  TApplicationException * x = [self checkIncomingMessageException];
  if (x != nil)  {
    @throw x;
  }
  Multiply_result * result = [[[Multiply_result alloc] init] autorelease_stub];
  [result read: inProtocol];
  [inProtocol readMessageEnd];
  if ([result successIsSet]) {
    return [result success];
  }
  @throw [TApplicationException exceptionWithType: TApplicationException_MISSING_RESULT
                                           reason: @"multiply failed: unknown result"];
}

- (int) multiply: (int) n1 n2: (int) n2
{
  [self send_multiply : n1 n2: n2];
  [[outProtocol transport] flush];
  return [self recv_multiply];
}

@end

@implementation MultiplicationServiceProcessor

- (id) initWithMultiplicationService: (id <MultiplicationService>) service
{
self = [super init];
if (!self) {
  return nil;
}
mService = [service retain_stub];
mMethodMap = [[NSMutableDictionary dictionary] retain_stub];
{
  SEL s = @selector(process_multiply_withSequenceID:inProtocol:outProtocol:);
  NSMethodSignature * sig = [self methodSignatureForSelector: s];
  NSInvocation * invocation = [NSInvocation invocationWithMethodSignature: sig];
  [invocation setSelector: s];
  [invocation retainArguments];
  [mMethodMap setValue: invocation forKey: @"multiply"];
}
return self;
}

- (id<MultiplicationService>) service
{
  return [[mService retain_stub] autorelease_stub];
}

- (BOOL) processOnInputProtocol: (id <TProtocol>) inProtocol
                 outputProtocol: (id <TProtocol>) outProtocol
{
  NSString * messageName;
  int messageType;
  int seqID;
  [inProtocol readMessageBeginReturningName: &messageName
                                       type: &messageType
                                 sequenceID: &seqID];
  NSInvocation * invocation = [mMethodMap valueForKey: messageName];
  if (invocation == nil) {
    [TProtocolUtil skipType: TType_STRUCT onProtocol: inProtocol];
    [inProtocol readMessageEnd];
    TApplicationException * x = [TApplicationException exceptionWithType: TApplicationException_UNKNOWN_METHOD reason: [NSString stringWithFormat: @"Invalid method name: '%@'", messageName]];
    [outProtocol writeMessageBeginWithName: messageName
                                      type: TMessageType_EXCEPTION
                                sequenceID: seqID];
    [x write: outProtocol];
    [outProtocol writeMessageEnd];
    [[outProtocol transport] flush];
    return YES;
  }
  // NSInvocation does not conform to NSCopying protocol
  NSInvocation * i = [NSInvocation invocationWithMethodSignature: [invocation methodSignature]];
  [i setSelector: [invocation selector]];
  [i setArgument: &seqID atIndex: 2];
  [i setArgument: &inProtocol atIndex: 3];
  [i setArgument: &outProtocol atIndex: 4];
  [i setTarget: self];
  [i invoke];
  return YES;
}

- (void) process_multiply_withSequenceID: (int32_t) seqID inProtocol: (id<TProtocol>) inProtocol outProtocol: (id<TProtocol>) outProtocol
{
multiply_args * args = [[multiply_args alloc] init];
[args read: inProtocol];
[inProtocol readMessageEnd];
Multiply_result * result = [[Multiply_result alloc] init];
[result setSuccess: [mService multiply: [args n1] n2: [args n2]]];
[outProtocol writeMessageBeginWithName: @"multiply"
                                  type: TMessageType_REPLY
                            sequenceID: seqID];
[result write: outProtocol];
[outProtocol writeMessageEnd];
[[outProtocol transport] flush];
[result release_stub];
[args release_stub];
}

- (void) dealloc
{
[mService release_stub];
[mMethodMap release_stub];
[super dealloc_stub];
}

@end

