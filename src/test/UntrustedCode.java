package test;

public class UntrustedCode {

  public static void doStuff() {
    System.getProperty("user.dir");
  }

  public static void disableSecurityManager() {
    SelectiveSecurityManager securityManager = (SelectiveSecurityManager) System.getSecurityManager();
    securityManager.disable();
  }

}
