package demo;

/**
 * The build script will package this class in a separate jar for testing
 * purposes. No permissions should be granted to this class in the security
 * policy.
 */
public class UntrustedCode {

  public static void doStuff() {
    System.getProperty("user.dir");
  }

  public static void disableSecurityManager() {
    SelectiveSecurityManager securityManager = (SelectiveSecurityManager) System.getSecurityManager();
    securityManager.disable();
  }

}
