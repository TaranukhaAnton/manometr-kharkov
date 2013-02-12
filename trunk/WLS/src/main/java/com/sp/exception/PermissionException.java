package com.sp.exception;

public class PermissionException extends ServiceException {
    public PermissionException(String permissionDescr, String permissionAction) {
        super("You do not have a permission to do action '" + permissionAction + "' on object '" + permissionDescr + "'. Please ask administrator to grant the permission");
    }

    public PermissionException(String s) {
        super(s);
    }
}
