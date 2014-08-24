package demo;

import java.security.AccessControlException;

public class Test {

  public static void main(String[] args) throws Exception {

    SelectiveSecurityManager securityManager = new SelectiveSecurityManager(false);
    System.setSecurityManager(securityManager);

    assert (System.getSecurityManager() == securityManager);

    // Check that the securityManager can be enabled/disabled in this thread.
    runTests(securityManager);

    // Check that the security manager can be enabled/disabled independently in
    // another thread.
    securityManager.enable();
    runTestsInAnotherThread(securityManager);

    // Check that unauthorised code can't disable the security manager,
    // even if it's already disabled.
    securityManager.disable();
    try {
      UntrustedCode.disableSecurityManager();
      throw new RuntimeException("Able to disable security manager from untrusted code.");
    } catch (AccessControlException e) {
      // Good
    }
    
    System.out.println("All tests passed.");
  }

  public static void runTests(SelectiveSecurityManager securityManager) {

    assert (securityManager.isEnabled() == false);

    UntrustedCode.doStuff();

    securityManager.enable();

    try {
      UntrustedCode.doStuff();
      throw new RuntimeException("Able to run restricted code with security manager enabled.");
    } catch (AccessControlException e) {
      // Good
    }

    securityManager.disable();

    UntrustedCode.doStuff();

  }

  public static void runTestsInAnotherThread(final SelectiveSecurityManager securityManager) throws Exception {

    Thread thread = new Thread(new Runnable() {

      @Override
      public void run() {
        runTests(securityManager);
      }

    });

    thread.start();
    thread.join();

  }

}
