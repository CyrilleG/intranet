// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Filter;
import intranet.FilterDataOnDemand;
import intranet.User;
import intranet.UserDataOnDemand;
import intranet.UserFilters;
import intranet.UserFiltersDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect UserFiltersDataOnDemand_Roo_DataOnDemand {
    
    declare @type: UserFiltersDataOnDemand: @Component;
    
    private Random UserFiltersDataOnDemand.rnd = new SecureRandom();
    
    private List<UserFilters> UserFiltersDataOnDemand.data;
    
    @Autowired
    private FilterDataOnDemand UserFiltersDataOnDemand.filterDataOnDemand;
    
    @Autowired
    private UserDataOnDemand UserFiltersDataOnDemand.userDataOnDemand;
    
    public UserFilters UserFiltersDataOnDemand.getNewTransientUserFilters(int index) {
        UserFilters obj = new UserFilters();
        setIdfilter(obj, index);
        setIduser(obj, index);
        return obj;
    }
    
    public void UserFiltersDataOnDemand.setIdfilter(UserFilters obj, int index) {
        Filter idfilter = filterDataOnDemand.getRandomFilter();
        obj.setIdfilter(idfilter);
    }
    
    public void UserFiltersDataOnDemand.setIduser(UserFilters obj, int index) {
        User iduser = userDataOnDemand.getRandomUser();
        obj.setIduser(iduser);
    }
    
    public UserFilters UserFiltersDataOnDemand.getSpecificUserFilters(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        UserFilters obj = data.get(index);
        Integer id = obj.getIduserFilters();
        return UserFilters.findUserFilters(id);
    }
    
    public UserFilters UserFiltersDataOnDemand.getRandomUserFilters() {
        init();
        UserFilters obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getIduserFilters();
        return UserFilters.findUserFilters(id);
    }
    
    public boolean UserFiltersDataOnDemand.modifyUserFilters(UserFilters obj) {
        return false;
    }
    
    public void UserFiltersDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = UserFilters.findUserFiltersEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'UserFilters' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<UserFilters>();
        for (int i = 0; i < 10; i++) {
            UserFilters obj = getNewTransientUserFilters(i);
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
