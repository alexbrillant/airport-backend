package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.contexts.exceptions.CannotRegisterContractTwiceException;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.exceptions.UnableResolveServiceException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ServiceLocatorTest {
    @Before
    public void setUp() {
        ServiceLocator.reset();
    }

    @Test
    public void givenTestImplementation_whenRegistering_shouldResolveThisImplementation() {
        TestImplementation implementation = new TestImplementation();

        ServiceLocator.registerSingleton(TestContract.class, implementation);

        assertSame(implementation, ServiceLocator.resolve(TestContract.class));
    }

    @Test(expected = UnableResolveServiceException.class)
    public void shouldNotResolveAContractIfNoImplementationIsRegistered() {
        ServiceLocator.resolve(TestContract.class);
    }

    @Test(expected = CannotRegisterContractTwiceException.class)
    public void shouldNotRegisterSameContractTwice() {
        ServiceLocator.registerSingleton(TestContract.class, new TestImplementation());
        ServiceLocator.registerSingleton(TestContract.class, new TestImplementation());
    }

    private interface TestContract {
    }

    private class TestImplementation implements TestContract {
    }
}
