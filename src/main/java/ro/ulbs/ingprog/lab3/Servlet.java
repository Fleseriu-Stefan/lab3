
package ro.ulbs.ingprog.lab3;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CounterServlet", value = {"/CounterServlet", "/servlet"})
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        updateCounter(request, response, "CounterGetName", "WarningGetName");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        updateCounter(request, response, "CounterPostName", "WarningPostName");
    }

    private void updateCounter(HttpServletRequest request, HttpServletResponse response,
                               String counterName, String warningName) throws IOException {
        HttpSession session = request.getSession();
        Integer counter = (Integer) session.getAttribute(counterName);
        if (counter == null) {
            counter = 0;
        }

        try {
            // 3.5.1 si 3.5.2: adunam inc si scadem dec folosind valorile primite.
            int increment = readValue(request.getParameter("inc"));
            int decrement = readValue(request.getParameter("dec"));
            counter = Math.toIntExact((long) counter + increment - decrement);
        } catch (NumberFormatException | ArithmeticException exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Parametrii inc si dec si rezultatul trebuie sa fie numere intregi de tip int.");
            return;
        }

        session.setAttribute(counterName, counter);

        // 3.5.3: avertizarea exista in sesiune doar cat contorul este zero.
        if (counter == 0) {
            session.setAttribute(warningName, Boolean.TRUE);
        } else {
            session.removeAttribute(warningName);
        }

        status(request, response);
    }

    private int readValue(String value) {
        // Un parametru absent sau un camp gol nu modifica valoarea contorului.
        return value == null || value.trim().isEmpty() ? 0 : Integer.parseInt(value.trim());
    }

    private void status(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        HttpSession session = request.getSession();

        Integer counterGet = (Integer) session.getAttribute("CounterGetName");
        Integer counterPost = (Integer) session.getAttribute("CounterPostName");

        printWriter.println("<!DOCTYPE html>");
        printWriter.println("<html lang=\"ro\"><head><meta charset=\"UTF-8\">");
        printWriter.println("<title>Counter Servlet</title></head><body>");
        printWriter.println("<h2>Welcome to Counter Servlet</h2>");
        printWriter.println("<p>Counter for GET: " + (counterGet == null ? 0 : counterGet) + "</p>");
        printWriter.println("<p>Counter for POST: " + (counterPost == null ? 0 : counterPost) + "</p>");

        if (Boolean.TRUE.equals(session.getAttribute("WarningGetName"))) {
            printWriter.println("<p>WARNING GET</p>");
        }
        if (Boolean.TRUE.equals(session.getAttribute("WarningPostName"))) {
            printWriter.println("<p>WARNING POST</p>");
        }

        printWriter.println("<p><a href=\"index.jsp\">Inapoi la formulare</a></p>");
        printWriter.println("</body></html>");
    }
}
