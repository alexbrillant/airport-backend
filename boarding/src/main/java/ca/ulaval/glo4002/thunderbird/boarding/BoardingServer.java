package ca.ulaval.glo4002.thunderbird.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.contexts.Context;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.DemoContext;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext;
import ca.ulaval.glo4002.thunderbird.boarding.rest.filters.EntityManagerContextFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

import static java.util.Optional.ofNullable;

public class BoardingServer implements Runnable {

    private static final String PORT_PROPERTY = "boarding.port";
    public static final int DEFAULT_PORT = 49152;

    private static final String CONTEXT_PROPERTY = "context";
    private static final String DEFAULT_CONTEXT = "demo";
    private Server server;

    public static void main(String[] args) {
        new BoardingServer().run();
    }

    private static Context resolveContext(String contextName) {
        switch (contextName) {
            case "demo":
                return new DemoContext();
            case "dev":
                return new DevContext();
            default:
                throw new RuntimeException("Cannot load context " + contextName);
        }
    }

    @Override
    public void run() {
        int httpPort = ofNullable(System.getProperty(PORT_PROPERTY)).map(Integer::parseInt).orElse(DEFAULT_PORT);
        System.setProperty("boarding.port", Integer.toString(httpPort));
        Context context = resolveContext(ofNullable(System.getProperty(CONTEXT_PROPERTY)).orElse(DEFAULT_CONTEXT));

        start(httpPort, context);
        join();
    }

    public void start(int httpPort, Context context) {
        System.setProperty("boarding.port", Integer.toString(httpPort));
        context.apply();

        server = new Server(httpPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        configurerJersey(servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void join() {
        try {
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tryStopServer();
        }
    }

    private void tryStopServer() {
        try {
            server.destroy();
        } catch (Exception e) {
            return;
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurerJersey(ServletContextHandler servletContextHandler) {
        ResourceConfig resourceConfig = new ResourceConfig().packages("ca.ulaval.glo4002.thunderbird.boarding")
                // Now you can expect validation errors to be sent to the client.
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
                // @ValidateOnExecution annotations on subclasses won't cause errors.
                .property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

        ServletContainer container = new ServletContainer(resourceConfig);
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    }
}
