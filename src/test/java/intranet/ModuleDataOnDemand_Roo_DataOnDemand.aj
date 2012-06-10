// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Module;
import intranet.ModuleDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect ModuleDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ModuleDataOnDemand: @Component;
    
    private Random ModuleDataOnDemand.rnd = new SecureRandom();
    
    private List<Module> ModuleDataOnDemand.data;
    
    public Module ModuleDataOnDemand.getNewTransientModule(int index) {
        Module obj = new Module();
        setClass1(obj, index);
        setDescription(obj, index);
        setName(obj, index);
        return obj;
    }
    
    public void ModuleDataOnDemand.setClass1(Module obj, int index) {
        String class1 = "class1_" + index;
        if (class1.length() > 100) {
            class1 = class1.substring(0, 100);
        }
        obj.setClass1(class1);
    }
    
    public void ModuleDataOnDemand.setDescription(Module obj, int index) {
        String description = "description_" + index;
        if (description.length() > 255) {
            description = description.substring(0, 255);
        }
        obj.setDescription(description);
    }
    
    public void ModuleDataOnDemand.setName(Module obj, int index) {
        String name = "name_" + index;
        if (name.length() > 100) {
            name = name.substring(0, 100);
        }
        obj.setName(name);
    }
    
    public Module ModuleDataOnDemand.getSpecificModule(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Module obj = data.get(index);
        Integer id = obj.getIdmodule();
        return Module.findModule(id);
    }
    
    public Module ModuleDataOnDemand.getRandomModule() {
        init();
        Module obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getIdmodule();
        return Module.findModule(id);
    }
    
    public boolean ModuleDataOnDemand.modifyModule(Module obj) {
        return false;
    }
    
    public void ModuleDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Module.findModuleEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Module' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Module>();
        for (int i = 0; i < 10; i++) {
            Module obj = getNewTransientModule(i);
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
