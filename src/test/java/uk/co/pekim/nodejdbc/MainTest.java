package uk.co.pekim.nodejdbc;

import java.security.Permission;

import org.junit.After;
import org.junit.Test;

/**
 * Unit test for NodeJdbc.
 */
public class MainTest {
    @After
    public void clearSecurityManager() {
        System.setSecurityManager(null);
    }
    
    @Test(expected = ExitException.class)
    public void testMissingJsonArg() {
        System.setSecurityManager(new NoExitSecurityManager());

        Main.main(new String[] {});
    }

    @Test(expected = ExitException.class)
    public void testBadJsonArg() {
        System.setSecurityManager(new NoExitSecurityManager());

        Main.main(new String[] {"bad"});
    }

    @Test
    public void testGoodJsonArg() {
        Main.main(new String[] {"{}"});
    }

    @SuppressWarnings("serial")
    protected static class ExitException extends SecurityException {
        public final int status;

        public ExitException(int status) {
            super("There is no escape!");
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            // allow anything.
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
            // allow anything.
        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }
}
