package com.sp.security;


import com.sp.exception.PermissionException;
import com.sp.model.Reseller;
import com.sp.model.UserPrefs;
import com.sun.xml.internal.ws.util.QNameMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class UserDetailsInfo implements UserDetails {

    private int userId;
    private QNameMap authoritiesMap;
    private String restrictedVehicleIdsCommaSeparated;
    private boolean leftPanelDisplayDinAllowedFm2x;
    private boolean leftPanelDisplayDinAllowedFm4x;
    private boolean leftPanelDisplayDinAllowedFm11;
    private boolean leftPanelDisplayDinAllowedT6;
    private boolean userAdmin;
    private String restrictedUnitGroupIdsCommaSeparated;
    private Reseller reseller;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getPassword() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUsername() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEnabled() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setScreenHeight(int screenHeight) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void setScreenWidth(int screenWidth) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public UserPrefs getUserPrefs() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public boolean isReplaceDriverNameWithRegNum() {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    public int getUserId() {
        return userId;
    }

    public Map getAuthoritiesMap() {
        return null;
    }

    public void checkAuthority(String permissionDescr, String permissionAction) throws PermissionException {

    }

    public String getRestrictedVehicleIdsCommaSeparated() {
        return restrictedVehicleIdsCommaSeparated;
    }

    public boolean isLeftPanelDisplayDinAllowedFm2x() {
        return leftPanelDisplayDinAllowedFm2x;
    }

    public boolean isLeftPanelDisplayDinAllowedFm4x() {
        return leftPanelDisplayDinAllowedFm4x;
    }

    public boolean isLeftPanelDisplayDinAllowedFm11() {
        return leftPanelDisplayDinAllowedFm11;
    }

    public boolean isLeftPanelDisplayDinAllowedT6() {
        return leftPanelDisplayDinAllowedT6;
    }

    public boolean isUserAdmin() {
        return userAdmin;
    }

    public String getRestrictedUnitGroupIdsCommaSeparated() {
        return restrictedUnitGroupIdsCommaSeparated;
    }

    public Reseller getReseller() {
        return reseller;
    }
}
