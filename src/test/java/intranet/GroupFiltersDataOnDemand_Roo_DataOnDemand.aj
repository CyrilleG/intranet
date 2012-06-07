// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Filter;
import intranet.FilterDataOnDemand;
import intranet.Group;
import intranet.GroupDataOnDemand;
import intranet.GroupFilters;
import intranet.GroupFiltersDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect GroupFiltersDataOnDemand_Roo_DataOnDemand {
    
    declare @type: GroupFiltersDataOnDemand: @Component;
    
    private Random GroupFiltersDataOnDemand.rnd = new SecureRandom();
    
    private List<GroupFilters> GroupFiltersDataOnDemand.data;
    
    @Autowired
    private FilterDataOnDemand GroupFiltersDataOnDemand.filterDataOnDemand;
    
    @Autowired
    private GroupDataOnDemand GroupFiltersDataOnDemand.groupDataOnDemand;
    
    public GroupFilters GroupFiltersDataOnDemand.getNewTransientGroupFilters(int index) {
        GroupFilters obj = new GroupFilters();
        setIdfilter(obj, index);
        setIdgroup(obj, index);
        return obj;
    }
    
    public void GroupFiltersDataOnDemand.setIdfilter(GroupFilters obj, int index) {
        Filter idfilter = filterDataOnDemand.getRandomFilter();
        obj.setIdfilter(idfilter);
    }
    
    public void GroupFiltersDataOnDemand.setIdgroup(GroupFilters obj, int index) {
        Group idgroup = groupDataOnDemand.getRandomGroup();
        obj.setIdgroup(idgroup);
    }
    
    public GroupFilters GroupFiltersDataOnDemand.getSpecificGroupFilters(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        GroupFilters obj = data.get(index);
        Integer id = obj.getIdgroupFilter();
        return GroupFilters.findGroupFilters(id);
    }
    
    public GroupFilters GroupFiltersDataOnDemand.getRandomGroupFilters() {
        init();
        GroupFilters obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getIdgroupFilter();
        return GroupFilters.findGroupFilters(id);
    }
    
    public boolean GroupFiltersDataOnDemand.modifyGroupFilters(GroupFilters obj) {
        return false;
    }
    
    public void GroupFiltersDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = GroupFilters.findGroupFiltersEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'GroupFilters' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<GroupFilters>();
        for (int i = 0; i < 10; i++) {
            GroupFilters obj = getNewTransientGroupFilters(i);
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