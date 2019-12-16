package com.Lirs.Spring.util;

import java.util.UUID;

public class UUIDUitl {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
