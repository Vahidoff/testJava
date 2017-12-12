package ru.job4j.testservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        try {
            req.getRequestDispatcher("/WEB-INF/views/test01.jsp").forward(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
