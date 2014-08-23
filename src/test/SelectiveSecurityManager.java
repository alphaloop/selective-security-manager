package test;

import java.security.Permission;

public class SelectiveSecurityManager extends SecurityManager {

	private static final ToggleSecurtiyManagerPermission TOGGLE_PERMISSION = new ToggleSecurtiyManagerPermission();
	
	ThreadLocal<Boolean> enabledFlag = null;
	
	public SelectiveSecurityManager(final boolean enabledByDefault) {
		
		enabledFlag = new ThreadLocal<Boolean>() {

			@Override
			protected Boolean initialValue() {
				return enabledByDefault;
			}

			@Override
			public void set(Boolean value) {
				SecurityManager securityManager = System.getSecurityManager();
				if (securityManager != null) {
					securityManager.checkPermission(TOGGLE_PERMISSION);
				}
				super.set(value);
			}
		};
	}
	
	@Override
	public void checkPermission(Permission permission) {
		if (shouldCheck(permission)) {
			super.checkPermission(permission);
		}
	}

	@Override
	public void checkPermission(Permission permission, Object context) {
		if (shouldCheck(permission)) {
			super.checkPermission(permission, context);
		}
	}
	
	private boolean shouldCheck(Permission permission) {
		return isEnabled() || permission instanceof ToggleSecurtiyManagerPermission;
	}

	public void enable() {
		enabledFlag.set(true);
	}
	
	public void disable() {
		enabledFlag.set(false);
	}
	
	public boolean isEnabled() {
		return enabledFlag.get();
	}

	public static class ToggleSecurtiyManagerPermission extends Permission {

		private static final long serialVersionUID = 4812713037565136922L;
		private static final String NAME = "ToggleSecurityManagerPermission";
		
		public ToggleSecurtiyManagerPermission() {
			super(NAME);
		}
		
		@Override
		public boolean implies(Permission permission) {
			if (permission instanceof ToggleSecurtiyManagerPermission) {
				return true;
			}
			return false;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof ToggleSecurtiyManagerPermission) {
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
			return null;
		}
		
	}
	
}
