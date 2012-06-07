// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Right;
import intranet.RightDataOnDemand;
import intranet.User;
import intranet.UserDataOnDemand;
import intranet.UserRights;
import intranet.UserRightsDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect UserRightsDataOnDemand_Roo_DataOnDemand {
    
    declare @type: UserRightsDataOnDemand: @Component;
    
    private Random UserRightsDataOnDemand.rnd = new SecureRandom();
    
    private List<UserRights> UserRightsDataOnDemand.data;
    
    @Autowired
    private RightDataOnDemand UserRightsDataOnDemand.rightDataOnDemand;
    
    @Autowired
    private UserDataOnDemand UserRightsDataOnDemand.userDataOnDemand;
    
    public UserRights UserRightsDataOnDemand.getNewTransientUserRights(int index) {
        UserRights obj = new UserRights();
        setIdright(obj, index);
        setIduser(obj, index);
        return obj;
    }
    
    public void UserRightsDataOnDemand.setIdright(UserRights obj, int index) {
        Right idright = rightDataOnDemand.getRandomRight();
        obj.setIdright(idright);
    }
    
    public void UserRightsDataOnDemand.setIduser(UserRights obj, int index) {
        User iduser = userDataOnDemand.getRandomUser();
        obj.setIduser(iduser);
    }
    
    public UserRights UserRightsDataOnDemand.getSpecificUserRights(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        UserRights obj = data.get(index);
        Integer id = obj.getIduserRights();
        return UserRights.findUserRights(id);
    }
    
    public UserRights UserRightsDataOnDemand.getRandomUserRights() {
        init();
        UserRights obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getIduserRights();
        return UserRights.findUserRights(id);
    }
    
    public boolean UserRightsDataOnDemand.modifyUserRights(UserRights obj) {
        return false;
    }
    
    public void UserRightsDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = UserRights.findUserRightsEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'UserRights' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<UserRights>();
        for (int i = 0; i < 10; i++) {
            UserRights obj = getNewTransientUserRights(i);
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
