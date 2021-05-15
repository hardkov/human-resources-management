package com.agh.hr.persistence.service;
import com.agh.hr.persistence.model.Permission;
import com.agh.hr.persistence.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Auth {

    public static List<Long> getReadIds(User userAuth) {
        List<Long> allowedIds=new ArrayList<>();
        allowedIds.add(userAuth.getId());
        Permission permission=userAuth.getPermissions();
        if(permission!=null) {
            List<Long> readList = permission.getRead();
            if (readList != null)
                allowedIds.addAll(readList);
        }
        return allowedIds;
    }

    public static List<Long> getWriteIds(User userAuth) {
        Permission permission=userAuth.getPermissions();
        if(permission!=null) {
            List<Long> allowedIds = permission.getWrite();
            if (allowedIds != null)
                return allowedIds;
        }
        return Collections.emptyList();
    }

    public static boolean getAdd(User userAuth) {
        Permission permission=userAuth.getPermissions();
        if(permission!=null)
            return(permission.getAdd());
        else
            return false;
    }

    public static User getCurrentUser(){
        return (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
    public static boolean arePermissionsOwnedBy(Permission subset,User user){
        Permission superset=user.getPermissions();
        List<Long> read=superset.getRead();
        List<Long> write=superset.getRead();
        for(Long p:subset.getRead())
            if(!read.contains(p))
                return false;
        for(Long p:subset.getWrite())
            if(!write.contains(p))
                return false;
        return superset.getAdd() || !subset.getAdd();
    }
}
