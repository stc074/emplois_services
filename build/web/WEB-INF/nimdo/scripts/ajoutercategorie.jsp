<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Empoi services - ASMINISTRATION</title>
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
                <h1>Ajouter une catégorie.</h1>
                <c:if test="${requestScope.categorie!=null}">
                    <c:set var="cat" value="${requestScope.categorie}" scope="page"></c:set>
                    <div id="form">
                        <c:if test="${cat.test==1}">
                            <div class="cadre">
                                <div class="info">Catégorie "<c:out value="${cat.categorie}"></c:out>" enregistrée !</div>
                            </div>
                            <br/>
                        </c:if>
                        <c:if test="${not empty cat.errorMsg}">
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <br/>
                                ${cat.errorMsg}
                            </div>
                        </c:if>
                        <fieldset>
                            <legend>Nouvelle catégorie</legend>
                            <form action="./ajouter-categorie.html#form" method="POST">
                                <label for="categorie">Nouvelle catégorie :</label>
                                <br/>
                                <input type="text" name="categorie" id="categorie" value="" size="40" maxlength="80" />
                                <br/>
                                <input type="submit" value="Valider" name="kermit" />
                                <br/>
                            </form>
                        </fieldset>
                    </div>
                </c:if>
                <c:if test="${requestScope.categories!=null}">
                    <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                    <h2>Catégories enregistrées</h2>
                    <c:choose>
                        <c:when test="${cats.nb==0}">
                            <div class="cadre">
                                <div class="info">Aucune catégorie enregistrée.</div>
                            </div>
                        </c:when>
                        <c:when test="${cats.nb>0}">
                            <div class="cadre">
                                <div class="info"><c:out value="${cats.nb}"></c:out> catégories enregistrées :</div>
                            </div>
                            <br/>
                            <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                <div><c:out value="${cats.categories[i]}"></c:out></div>
                                <br/>
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
