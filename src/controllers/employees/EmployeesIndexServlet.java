package controllers.employees;

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
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // EntityManagerのオブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();
        // 該当のIDの従業員情報1件のみをデータベースから取得
        //Employee f = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        // 開くページ数を取得（デフォルトは1ページ目）

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));

        } catch(NumberFormatException e) { }

        // 最大件数と開始位置を指定して従業員情報を取得(15件でページ替え)
        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();


        // 全件数を取得
        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                                       .getSingleResult();


        //ログインIDをセッションから特定
        Employee follow =(Employee) request.getSession().getAttribute("login_employee");
        //ログインIDのフォローしているID情報をDBからリストへ格納
        List<Follow> followlist_id = em.createNamedQuery("getFollowlist_id",Follow.class)
                                       .setParameter("follower_id", follow)//ログインID
                                       .getResultList();
        //確認用
        for(int i = 0; i < followlist_id.size(); i++) {
            System.out.println("コメント"+followlist_id.get(i));
          }


        em.close();
        //DBにある項目をビューに送る
        request.setAttribute("employees", employees);//従業員情報
        request.setAttribute("employees_count", employees_count);// 全件数
        request.setAttribute("page", page);// ページ数

        //フォロー機能用
        // 従業員情報とセッションIDをリクエストスコープに登録
        //request.setAttribute("employee", f);
        request.setAttribute("_token", request.getSession().getId());
        // 従業員IDをセッションスコープに登録
        request.setAttribute("followlist_id", followlist_id);


        //従業員テーブルのフォローフラグを遷移事に呼び出す
        //全従業員ループ（フォローフラグの初期化）
        //フォロー従業員ループ
        //
        // Follow followss = follow.getFollower();

        for(Employee employee : employees) {
            employee.setFollow_flag(0);
            for(Follow follows : followlist_id) {
                if(employee.getId() == follows.getFollowee().getId()) {
                    employee.setFollow_flag(1);//ログインIDのフラグを立てている
                    System.out.println("ログインID従業員"+employee.getId());
                    System.out.println("ログインID"+follows.getFollower().getId());
                    System.out.println("フォローID"+follows.getFollowee().getId());
                }
            }
       }


        //javascript用
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}