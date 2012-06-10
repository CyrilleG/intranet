// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.User;
import intranet.UserDataOnDemand;
import intranet.UserInfo;
import intranet.UserInfoDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect UserInfoDataOnDemand_Roo_DataOnDemand {
    
    declare @type: UserInfoDataOnDemand: @Component;
    
    private Random UserInfoDataOnDemand.rnd = new SecureRandom();
    
    private List<UserInfo> UserInfoDataOnDemand.data;
    
    @Autowired
    private UserDataOnDemand UserInfoDataOnDemand.userDataOnDemand;
    
    public UserInfo UserInfoDataOnDemand.getNewTransientUserInfo(int index) {
        UserInfo obj = new UserInfo();
        setIduser(obj, index);
        setKey(obj, index);
        setValue(obj, index);
        return obj;
    }
    
    public void UserInfoDataOnDemand.setIduser(UserInfo obj, int index) {
        User iduser = userDataOnDemand.getRandomUser();
        obj.setIduser(iduser);
    }
    
    public void UserInfoDataOnDemand.setKey(UserInfo obj, int index) {
        String key = "key_" + index;
        if (key.length() > 100) {
            key = new Random().nextInt(10) + key.substring(1, 100);
        }
        obj.setKey(key);
    }
    
    public void UserInfoDataOnDemand.setValue(UserInfo obj, int index) {
        String value = "value_" + index;
        if (value.length() > 255) {
            value = value.substring(0, 255);
        }
        obj.setValue(value);
    }
    
    public UserInfo UserInfoDataOnDemand.getSpecificUserInfo(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        UserInfo obj = data.get(index);
        Integer id = obj.getIdinfo();
        return UserInfo.findUserInfo(id);
    }
    
    public UserInfo UserInfoDataOnDemand.getRandomUserInfo() {
        init();
        UserInfo obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getIdinfo();
        return UserInfo.findUserInfo(id);
    }
    
    public boolean UserInfoDataOnDemand.modifyUserInfo(UserInfo obj) {
        return false;
    }
    
    public void UserInfoDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = UserInfo.findUserInfoEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'UserInfo' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<UserInfo>();
        for (int i = 0; i < 10; i++) {
            UserInfo obj = getNewTransientUserInfo(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
