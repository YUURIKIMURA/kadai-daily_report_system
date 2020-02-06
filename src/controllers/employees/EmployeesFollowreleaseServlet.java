package controllers.employees;

import java.io.IOException;
import java.util.List;

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

        EntityManager em = DBUtil.createEntityManager();

        //ログインIDをセッションから特定
        Employee follow =(Employee) request.getSession().getAttribute("login_employee");
        //フォローされるIDをJSPのURLから引数として特定
        Employee followee = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        //フォローテーブル内のログインID（followee_idカラム）が
        //今ログインしているID（セットパラメータ）と一致しているカラムの表示
        List<Follow> follow_id = em.createNamedQuery("getMyFollow_id",Follow.class)
                                   .setParameter("followee_id", follow)//ログインID
                                   .setParameter("follower_id", followee)//フォローしているユーザID
                                   .getResultList();

        //Follow follow = follow_id.get(インデックス値);


        //request.setAttribute("follow_id", follow_id);//フォローID情報

        //Follow follow = em.find(Follow.class, Integer.parseInt(request.getParameter("follower_id")));
        //Follow delete = Follow.class, Integer.parseInt(request.getParameter("id")));
        for(int i = 0; i < follow_id.size(); i++) {
            System.out.println("コメント"+follow_id.get(i));
          }


        if(followee.getFollow_flag()==0) {
            followee.setFollow_flag(1);
            }
        else {followee.setFollow_flag(0);
        }

        //DB登録準備
        Follow f = new Follow();
        f.setFollower(follow);
        f.setFollowee(followee);
        em.getTransaction().begin();
        //em.remove(follow_id.get());//削除
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "フォロー解除しました。");

        response.sendRedirect(request.getContextPath() + "/employees/index");
    }
}

