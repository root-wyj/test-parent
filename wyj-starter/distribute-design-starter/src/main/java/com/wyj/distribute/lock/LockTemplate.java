package com.wyj.distribute.lock;


import com.wyj.apps.common.core.apiresult.CommonResultEnum;
import com.wyj.apps.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class LockTemplate<R> {


    private DistributeLock distributeLock;

    public LockTemplate (DistributeLock distributeLock){
        this.distributeLock = distributeLock;
    }

    public DistributeLock getDistributeLock() {
        return distributeLock;
    }

    public <R> R doBiz(LockedCallback<R> lockedCallback, String scene, String uniqueId, String key, Long expireSecond){
        if(StringUtils.isBlank(uniqueId)){
            uniqueId = System.currentTimeMillis()+"";
        }
        boolean acquiredSuccess = false;
        key = "lock-"+key;
        try{
            acquiredSuccess = distributeLock.tryLock(key, uniqueId, expireSecond);
            if(!acquiredSuccess){
                log.info("|doBiz thread={} acquire lock fail scene={}, key={},uniqueId={},expireSecond={}",
                        Thread.currentThread().getName(),scene,key,uniqueId,expireSecond);
                throw new BusinessException(CommonResultEnum.DISTRIBUTE_LOCK_FAILED);
            }
            return (R)lockedCallback.callback();

        }catch (Exception e){
            if(! (e instanceof BusinessException)){
                log.info("|doBiz|lockedCallback not BusinessException, scene={},key={},uniqueId={},expireSecond={} exception e:",scene,key,uniqueId,expireSecond,e);
            }
            throw e;
        }finally {
            if(acquiredSuccess){
                distributeLock.unlock(key,uniqueId);
                log.info("release lock key={},uniqueId={} success",key,uniqueId);
            }
        }
    }
    
    
    public <R> R doBiz(LockedCallback<R> lockedCallback, String scene, String key, Long expireSecond){

            return doBiz(lockedCallback,scene,"",key,expireSecond);
    }

    public <R> R doBiz(LockedCallback<R> lockedCallback, String key, Long expireSecond){

        return doBiz(lockedCallback,"","",key,expireSecond);
    }

    public <R> R doBiz(LockedCallback<R> lockedCallback, String key, String uniqueId){

        return doBiz(lockedCallback,"",uniqueId,key,2L);
    }
    
    public <R> R doBiz(LockedCallback<R> lockedCallback, String key){

        return doBiz(lockedCallback,"","",key,2L);
    }
}