package controllers.employees;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesFollowServlet
 */
@WebServlet("/employees/followrelease")
public class EmployeesFollowreleaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesFollowreleaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String _token = (String)request.getParameter("_token");
        //トークンが空以外かつトークンの文字列が一致している時
        if(_token != null && _token.equals(request.getSession().getId())) {
        */
        //現行フォロー機能になっている
        EntityManager em = DBUtil.createEntityManager();


        Employee follow =(Employee) request.getSession().getAttribute("login_employee");//ログインID
        Employee followee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));//フォローされるID

        request.setAttribute("employee", followee);

        if(followee.getFollow_flag()==0) {
            followee.setFollow_flag(1);
            }
        else {followee.setFollow_flag(0);
        }

        Follow f = new Follow();
        f.setFollower(follow);
        f.setFollowee(followee);
        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "フォローしました。");

        response.sendRedirect(request.getContextPath() + "/employees/index");
    }
}
//}
