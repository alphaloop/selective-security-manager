package test;

import java.security.AccessControlException;

public class Test {

	public static void main(String[] args) throws Exception {
		
		SelectiveSecurityManager securityManager = new SelectiveSecurityManager(false);
		System.setSecurityManager(securityManager);
		
		runTests(securityManager);
		
		securityManager.enable();
		
		runTestsInAnotherThread(securityManager);
		
		securityManager.disable();

		try {
			UntrustedCode.disableSecurityManager();
			throw new RuntimeException("Able to disable security manager from untrusted code.");
		} catch (AccessControlException e) {
		  // Good
		}
		
	}
	
	public static void runTests(SelectiveSecurityManager securityManager) {
		
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
