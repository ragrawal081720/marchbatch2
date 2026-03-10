import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {
    public static void main(String[] args) throws IOException {
        // Create HTTP server on port 8083
        HttpServer server = HttpServer.create(new InetSocketAddress(8083), 0);
        
        // Create context for root path
        server.createContext("/", new RootHandler());
        server.createContext("/hello", new HelloHandler());
        server.createContext("/info", new InfoHandler());
        
        // Set executor (null means default executor)
        server.setExecutor(null);
        
        // Start the server
        server.start();
        System.out.println("Server started on port 8083");
        System.out.println("Visit http://localhost:8083 in your browser");
        System.out.println("Available endpoints:");
        System.out.println("  - http://localhost:8083/");
        System.out.println("  - http://localhost:8083/hello");
        System.out.println("  - http://localhost:8083/info");
    }
    
    // Handler for root path "/"
    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head><title>Java Web Server</title>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; margin: 40px; background: #f0f0f0; }" +
                    "h1 { color: #333; }" +
                    "p { color: #666; }" +
                    "a { color: #0066cc; text-decoration: none; }" +
                    "a:hover { text-decoration: underline; }" +
                    ".container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }" +
                    "</style></head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h1>Welcome to Java Web Server on Port 8083!</h1>" +
                    "<p>Server is running successfully on <strong>localhost:8083</strong></p>" +
                    "<h3>Available Endpoints:</h3>" +
                    "<ul>" +
                    "<li><a href='/'>/ - Home page (current)</a></li>" +
                    "<li><a href='/hello'>/hello - Hello message</a></li>" +
                    "<li><a href='/info'>/info - System information</a></li>" +
                    "</ul>" +
                    "<p><em>Created with Java HttpServer</em></p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
            
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    // Handler for "/hello" path
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head><title>Hello</title>" +
                    "<style>body { font-family: Arial, sans-serif; margin: 40px; background: #f0f0f0; }" +
                    ".container { background: white; padding: 20px; border-radius: 8px; }</style></head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h1>Hello from Java!</h1>" +
                    "<p>This is the /hello endpoint</p>" +
                    "<p><a href='/'>Back to home</a></p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
            
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    // Handler for "/info" path
    static class InfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head><title>System Info</title>" +
                    "<style>body { font-family: Arial, sans-serif; margin: 40px; background: #f0f0f0; }" +
                    ".container { background: white; padding: 20px; border-radius: 8px; }</style></head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h1>System Information</h1>" +
                    "<ul>" +
                    "<li><strong>Java Version:</strong> " + System.getProperty("java.version") + "</li>" +
                    "<li><strong>OS Name:</strong> " + System.getProperty("os.name") + "</li>" +
                    "<li><strong>OS Architecture:</strong> " + System.getProperty("os.arch") + "</li>" +
                    "<li><strong>User Name:</strong> " + System.getProperty("user.name") + "</li>" +
                    "<li><strong>Server Port:</strong> 8083</li>" +
                    "</ul>" +
                    "<p><a href='/'>Back to home</a></p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
            
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
