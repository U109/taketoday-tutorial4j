package cn.tuyucheng.taketoday.servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/u_login")
public class UserLoginServlet extends HttpServlet {

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HttpSession session = request.getSession();

      session.setAttribute("userId", request.getParameter("userId"));

      request.setAttribute("id", session.getAttribute("userId"));

      request.getRequestDispatcher("/WEB-INF/jsp/userlogin.jsp").forward(request, response);

   }

}
