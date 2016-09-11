<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Empoi services - ASMINISTRATION - Liste des abus</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Emploi services - Toutes les annonces GRATUITES d'emplois services et emplois à domicile."/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="../GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="../CSS/style.css" />
<%@include file="./../../scripts/analytics.jsp" %>
    </head>
    <body>
        <%@include file="./haut.jsp" %>
        <section>
            <c:catch var="ex">
                <h1>Liste des abus</h1>
                <c:if test="${requestScope.abus!=null}">
                    <c:set var="ab" value="${requestScope.abus}" scope="page"></c:set>
                    <c:choose>
                        <c:when test="${ab.nb==0}">
                            <div class="cadre">
                                <div class="info">Aucun abus enregistré !</div>
                            </div>
                        </c:when>
                        <c:when test="${ab.nb>0}">
                            <div class="cadre">
                                <div class="info"><c:out value="${ab.nb}"></c:out> abus enregistré(s).</div>
                            </div>
                            <p></p>
                            <c:forEach var="i" begin="0" end="${ab.nb-1}" step="1">
                                <div class="cadre">
                                    <p>
                                        <a href="<c:out value="${ab.urls[i]}"></c:out>" rel="nofollow" title="<c:out value="${ab.titres[i]}"></c:out>"><c:out value="${ab.titres[i]}"></c:out></a>
                                        <span>&nbsp;<c:out value="${ab.comments[i]}"></c:out></span>
                                    </p>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:if>
            </c:catch>
            <c:if test="${not empty ex}">
                <div class="erreur">
                    <div>ERREUR :</div>
                    <br/>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </c:if>
        </section>
    </body>
</html>
