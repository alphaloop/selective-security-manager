package demo;

import java.security.Permission;

public class ToggleSecurityManagerPermission extends Permission {

  private static final long serialVersionUID = 4812713037565136922L;
  private static final String NAME = "ToggleSecurityManagerPermission";

  public ToggleSecurityManagerPermission() {
    super(NAME);
  }

  @Override
  public boolean implies(Permission permission) {
    return this.equals(permission);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ToggleSecurityManagerPermission) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return NAME.hashCode();
  }

  @Override
  public String getActions() {
    return "";
  }

}
