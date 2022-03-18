package com.alain.singleton;

import com.alain.dto.Intern;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;

@Component
public final class CacheSingleton {
    private static CacheSingleton INSTANCE;
    private HashMap<BigInteger, Intern> cache = new HashMap<BigInteger, Intern>();

    private CacheSingleton() {}

    public synchronized static CacheSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CacheSingleton();
        }
        return INSTANCE;
    }

    public static CacheSingleton getINSTANCE() {
        return INSTANCE;
    }

    public synchronized static void setINSTANCE(CacheSingleton INSTANCE) {
        CacheSingleton.INSTANCE = INSTANCE;
    }

    public HashMap<BigInteger, Intern> getCache() {return cache;}

    public synchronized void setCache(HashMap<BigInteger, Intern> cache) {
        this.cache = cache;
    }
}
