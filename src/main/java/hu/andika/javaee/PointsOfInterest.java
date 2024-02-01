package hu.andika.javaee;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import hu.andika.javaee.utils.DatabaseInit;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class PointsOfInterest extends HttpServlet {
    private String message;
    private final Connection connection;

    public PointsOfInterest() throws SQLException {
        DatabaseInit databaseInit = new DatabaseInit();
        this.connection = databaseInit.getConnection();
        System.out.println(getConnection().getCatalog());
    }

    public Connection getConnection() {
        return connection;
    }

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}