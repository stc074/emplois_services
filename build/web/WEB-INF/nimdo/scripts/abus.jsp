<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Empoi services - ASMINISTRATION - Abus</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
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
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <div class="cadre">
                                <div class="info">Abus non spécifié.</div>
                            </div>
                        </c:when>
                        <c:when test="${requestSope.info==2}">
                            <div class="cadre">
                                <div class="info">Impossible de lire l'abus.</div>
                            </div>
                        </c:when>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.abus!=null}">
                    <c:set var="ab" value="${requestScope.abus}" scope="page"></c:set>
                    <h1>Abus</h1>
                    <div class="cadre">
                        <p></p>
                        <div>
                            <a href="./ignore-abus-<c:out value="${ab.id}"></c:out>.html" title="IGNORER CET ABUS" rel="nofollow">IGNORER CET ABUS</a>
                        </div>
                        <p></p>
                        <div>
                            <a href="./efface-abus-<c:out value="${ab.id}"></c:out>.html" rel="nofollow" title="ÉFFACER CETTE ANNONCE">ÉFFACER CETTE ANNONCE</a>
                        </div>
                        <p></p>
                    </div>
                        <p></p>
                        <div class="cadre">
                            <h2><c:out value="${ab.titreAnnonce}"></c:out></h2>
                            ${ab.descriptionAnnonce}
                        </div>
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
