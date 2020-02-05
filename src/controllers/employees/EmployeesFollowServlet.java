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
@WebServlet("/employees/follow")
public class EmployeesFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesFollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        //トークンが空以外かつトークンの文字列が一致している時
        if(_token != null && _token.equals(request.getSession().getId())) {
            // EntityManagerのオブジェクトを生成
            EntityManager em = DBUtil.createEntityManager();
            // セッションスコープから従業員情報のIDを数値として取得して
            // 該当のIDの従業員情報1件のみをデータベースから取得
            //Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
            //request.setAttribute("follow_id", e);
            //System.out.println("コメント"+em.find(Employee.class, Integer.parseInt(request.getParameter("id"))));
            //追加
            Employee follow =(Employee) request.getSession().getAttribute("login_employee");
            Employee followee = em.find(Employee.class, (Integer)(request.getSession().getAttribute("follow_id")));

            //フォローしてる際にサーブレット起動でフォロー解除
            /*
            if(e.getFollow_flag()==0) {
                e.setFollow_flag(1);
                }
            else {e.setFollow_flag(0);
            }

            e.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            */
            // データベースを更新
            Follow f = new Follow();
            f.setFollower(follow);
            f.setFollowee(followee);
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();
            /*
            if(e.getFollow_flag()==0) {
            request.getSession().setAttribute("flush", "フォロー解除しました。");
            }
            else {request.getSession().setAttribute("flush", "フォローしました。");
            }
            */

            request.getSession().setAttribute("flush", "値を格納しました");
            //リダイレクトでindexへ遷移
            response.sendRedirect(request.getContextPath() + "/employees/index");
        }
    }

}
