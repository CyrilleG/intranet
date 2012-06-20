package intranet;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_user")
@RooDbManaged(automaticallyDelete = true)
public class AppUser {

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<AppSession> appSessions;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<InfoPrivacities> infoPrivacitieses;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserData> userDatas;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserFilters> userFilterss;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserGroups> userGroupss;

    @OneToMany(mappedBy = "iduser")
    private Set<UserInfo> userInfoes;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserRights> userRightss;

    @Column(name = "login", length = 45, unique = true)
    @NotNull
    private String login;

    @Column(name = "password", length = 100)
    @NotNull
    private String password;

    @Column(name = "enabled")
    @NotNull
    private boolean enabled;

    private int findUserDataByName(String name) {
        UserData[] elems = (UserData[]) userDatas.toArray();
        for (int i = 0; i < elems.length; i++) if (elems[i].getName().compareTo(name) == 0) return i;
        return -1;
    }

    public boolean hasObject(String name) {
        return findUserDataByName(name) != -1;
    }

    public UserData getObject(String name) throws Exception {
        int index = findUserDataByName(name);
        if (index != -1) return (UserData) userDatas.toArray()[index]; else throw new Exception("Object not found for this User");
    }

    public void removeObject(String name) throws Exception {
        int index = findUserDataByName(name);
        if (index != -1) {
            UserData e = (UserData) userDatas.toArray()[index];
            for (DataField f : e.getDataFields()) f.remove();
            e.remove();
        } else throw new Exception("Object not found for this User");
    }

    public void setUserData(UserData o) throws Exception {
        if (!hasObject(o.getName())) userDatas.add(o); else throw new Exception("Object name must be unique for an User");
    }

    public Set<intranet.UserFilters> getUserFilterss() {
        return userFilterss;
    }

    public void setUserFilterss(Set<intranet.UserFilters> userFilterss) {
        this.userFilterss = userFilterss;
    }

    public Set<intranet.UserGroups> getUserGroupss() {
        return userGroupss;
    }

    public void setUserGroupss(Set<intranet.UserGroups> userGroupss) {
        this.userGroupss = userGroupss;
    }

    public Set<intranet.UserInfo> getUserInfoes() {
        return userInfoes;
    }

    public void setUserInfoes(Set<intranet.UserInfo> userInfoes) {
        this.userInfoes = userInfoes;
    }

    public Set<intranet.UserRights> getUserRightss() {
        return userRightss;
    }

    public void setUserRightss(Set<intranet.UserRights> userRightss) {
        this.userRightss = userRightss;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<intranet.AppSession> getAppSessions() {
        return appSessions;
    }

    public void setAppSessions(Set<intranet.AppSession> appSessions) {
        this.appSessions = appSessions;
    }

    public Set<intranet.InfoPrivacities> getInfoPrivacitieses() {
        return infoPrivacitieses;
    }

    public void setInfoPrivacitieses(Set<intranet.InfoPrivacities> infoPrivacitieses) {
        this.infoPrivacitieses = infoPrivacitieses;
    }

    public Set<intranet.UserData> getUserDatas() {
        return userDatas;
    }

    public void setUserDatas(Set<intranet.UserData> userDatas) {
        this.userDatas = userDatas;
    }
}
