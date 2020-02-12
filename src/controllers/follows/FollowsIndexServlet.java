package controllers.follows;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsIndexServlet
 */
@WebServlet("/follows/index")
public class FollowsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

      //ログインIDをセッションから特定
        Employee follow =(Employee) request.getSession().getAttribute("login_employee");
        System.out.println(follow);
        List<Report> followreports = em.createNamedQuery("getAllFollowedReports", Report.class)
                                         .setParameter("follower", follow)
                                         .setFirstResult(15 * (page - 1))
                                         .setMaxResults(15)
                                         .getResultList();

        long followreports_count = (long)em.createNamedQuery("getAllFollowedReportsCount", Long.class)
                                           .getSingleResult();


        em.close();

        request.setAttribute("followreports", followreports);
        request.setAttribute("followreports_count", followreports_count);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);

    }



}
