百词斩学习记录提交有可能失败，当学习记录失败后数据放到一个数据库里面。
下面有一段逻辑就是负责处理这些失败的数据。

```
 @ChooseDatabase
    @Transactional(transactionManager = DataSourceCfg.TM_NAME)
    public void executeFailSubmitRecordTask(Long userId, FailedSubmitRecord task) {
        try {
            SubmitRecord sr = parseRawMessage(task.getContent());
            processSubmitRecord(sr.getUserId(), sr);
            failedSubmitRecordService.updateState(task.getUserId(), task.getId(), Constants.INC_TASK_STATUS_SUCCESS);
        } catch (Exception e) {
            logger.error("executeFailSubmitRecordTask error : id =  " + task.getId(), e);
            failedSubmitRecordService.updateState(task.getUserId(), task.getId(), Constants.INC_TASK_STATUS_ERROR);
        }
    }

```

executeFailSubmitRecordTask这个方法就是从这个库里面拿数据，然后重新处理。


```
SubmitRecord sr = parseRawMessage(task.getContent());
processSubmitRecord(sr.getUserId(), sr);
failedSubmitRecordService.updateState(task.getUserId(), task.getId(), Constants.INC_TASK_STATUS_SUCCESS);
```
这段代码就是重新处理数据，最后把记录状态为INC_TASK_STATUS_SUCCESS.
如果try里面的逻辑失败了，调用failedSubmitRecordService.updateState(task.getUserId(), task.getId(), Constants.INC_TASK_STATUS_ERROR);

但是try里面失败了 failedSubmitRecordService.updateState(task.getUserId(), task.getId(), Constants.INC_TASK_STATUS_ERROR); 也会失败。
按理说会成功的。抛出的异常是: 

```
org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only

	at org.springframework.transaction.support.AbstractPlatformTransactionManager.processRollback(AbstractPlatformTransactionManager.java:873)
	at org.springframework.transaction.support.AbstractPlatformTransactionManager.commit(AbstractPlatformTransactionManager.java:710)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.commitTransactionAfterReturning(TransactionAspectSupport.java:532)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:304)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:98)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)
	at org.springframework.aop
```
看了一下updateState的代码
```$xslt
 @ChooseDatabase
    @Transactional(transactionManager = DataSourceCfg.TM_NAME)
    public int updateState(Long userId, int id, int state)
    {
        return failedSubmitRecordMapper.updateState(id,state);
    }
```
   
@Transaction 默认的事务传播(propagation)是 Propagation.REQUIRED,如果在在执行某个有事务方法时候，如果没有事务，新建一个，如果已经有了加入这个事务
当执行
```
 failedSubmitRecordService.updateState(task.getUserId(), task.getId(), Constants.INC_TASK_STATUS_SUCCESS);
```
失败的时候已经有一个事务。并且出错了，Spring的TransactionManager将事务状态(TransactionStatus)设置为rollBackOnly（setRollbackOnly）
然后进入 catch 块里面执行
```
 failedSubmitRecordService.updateState(task.getUserId(), task.getId(), Constants.INC_TASK_STATUS_ERROR);

```
发现已经有一个transaction存在，加入这个transaction，但是在commit时候发现这个事务的状态(TransactionStatus)已经为 Rollback。
所以抛出Exception 

```
org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
```


修改代码，propagation 修改为 Propagation.REQUIRES_NEW，问题解决。Propagation.REQUIRES_NEW 表示每次都新建一个Transaction
```$xslt
 @ChooseDatabase
    @Transactional(transactionManager = DataSourceCfg.TM_NAME, propagation = Propagation.REQUIRES_NEW)
    public int updateState(Long userId, int id, int state)
    {
        return failedSubmitRecordMapper.updateState(id,state);
    }
```

   