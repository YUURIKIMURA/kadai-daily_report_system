<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員　一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                    <th>フォロー機能</th>
                </tr>
                <%--レコードの繰り返し--%>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <%--色--%>
                    <tr class="row${status.count % 2}">
                    <%--社員番号--%>
                        <td><c:out value="${employee.code}" /></td>
                    <%--氏名--%>
                        <td><c:out value="${employee.name}" /></td>
                    <%--操作--%>
                        <td>
                            <c:choose>
                                <c:when test="${employee.delete_flag == 1}">
                                    <a href="<c:url value='/employees/show?id=${employee.id}' />">削除済み</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    <%--フォロー機能--%>
                        <td>
                        <c:choose>

                                <c:when test="${employee.follow_flag == 1}">
                                <a href="<c:url value='/employees/followrelease?id=${employee.id}' />">フォロー済み</a>
                                </c:when>
                                <c:otherwise>
                                <a href="<c:url value='/employees/follow?id=${employee.id}' />">フォローする</a>
                                </c:otherwise>
                        </c:choose>

                        </td>

                </c:forEach>
            </tbody>
        </table>
        <%--全件操作--%>
        <div id="pagination">
            （全 ${employees_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/employees/new' />">新規従業員の登録</a></p>

    </c:param>
</c:import>