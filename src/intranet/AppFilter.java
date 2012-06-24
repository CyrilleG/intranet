package intranet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import intranet.AppFilter;
import intranet.GroupFilters;
import intranet.UserFilters;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_filter")
@RooDbManaged(automaticallyDelete = true)
public class AppFilter {
	
    @OneToMany(mappedBy = "idfilter", cascade = CascadeType.ALL)
    private Set<GroupFilters> groupFilterss;
    
    @OneToMany(mappedBy = "idfilter", cascade = CascadeType.ALL)
    private Set<UserFilters> userFilterss;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "class", length = 75)
    private String class1;
    
    public void addFilterToGroup()
    {
    	//TODO rights
    }
    
    public void removeFilterFromGroup()
    {
    	//TODO rights
    }
    public void addFilterToUser()
    {
    	//TODO rights
    }
    
    public void removeFilterFromUser()
    {
    	//TODO rights
    }
    
    public boolean hasUser()
    {
    	return userFilterss.size() > 0;
    }
    public int countUser()
    {
    	return userFilterss.size();
    }
    
    public boolean hasGroup()
    {
    	return groupFilterss.size() > 0;
    }
    public int countGroup()
    {
    	return groupFilterss.size();
    }
    
    public String getName() {
        return name;//TODO rights
    }
    
    public void setName(String name) {
        this.name = name;//TODO rights
    }
    
    public String getDescription() {
        return description;//TODO rights
    }
    
    public void setDescription(String description) {
        this.description = description;//TODO rights
    }
    
    public String getFilterClass() {
        return class1;//TODO rights
    }
    
    public void setFilterClass(String type_name) {
        this.class1 = type_name;//TODO rights
    }
    
    public void appliPreFilter()
    {
    	
    }
    public void appliPostFilter()
    {
    	
    }
    
    public boolean isUse()
    {
    	return userFilterss.size() > 0;
    }

}
