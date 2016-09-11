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
                <h1>Ajouter une sous-categorie</h1>
                <c:if test="${requestScope.sousCategorie!=null}">
                    <c:set var="sousCat" value="${requestScope.sousCategorie}" scope="page"></c:set>
                    <div id="form">
                        <c:if test="${sousCat.test==1}">
                            <div class="cadre">
                                <div class="info">Sous-catégorie "<c:out value="${sousCat.sousCategorie}"></c:out>" enregistrée !</div>
                            </div>
                            <br/>
                        </c:if>
                        <c:if test="${not empty sousCat.errorMsg}">
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <br/>
                                ${sousCat.errorMsg}
                            </div>
                            <br/>
                        </c:if>
                            <fieldset>
                                <legend>Nouvelle sous-catégorie</legend>
                                <form action="./ajouter-sous-categorie.html#form" method="POST">
                                    <label for="idCategorie">Catégorie :</label>
                                    <select name="idCategorie" id="idCategorie" onchange="javascript:window.location.href='./ajouter-sous-categorie-1-'+this.value+'.html';">
                                        <option value="0"<c:if test="${sousCat.idCategorie==0}"> selected="selected"</c:if>>Choisissez</option>
                                        <c:if test="${requestScope.categories!=null}">
                                            <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                                            <c:if test="${cats.nb>0}">
                                                <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                                    <option value="<c:out value="${cats.ids[i]}"></c:out>"<c:if test="${sousCat.idCategorie==cats.ids[i]}"> selected="selected"</c:if>><c:out value="${cats.categories[i]}"></c:out></option>
                                                </c:forEach>
                                            </c:if>
                                        </c:if>
                                    </select>
                                        <br/>
                                        <c:if test="${sousCat.idCategorie!=0}">
                                            <label for="sousCategorie">Sous catégorie :</label>
                                            <br/>
                                            <input type="text" name="sousCategorie" id="sousCategorie" value="" size="40" maxlength="80" />
                                            <br/>
                                            <input type="submit" value="Valider" name="kermit" />
                                            <br/>
                                        </c:if>
                                </form>
                            </fieldset>
                    </div>
                </c:if>
                <c:if test="${requestScope.sousCategories!=null}">
                    <c:set var="sousCats" value="${requestScope.sousCategories}" scope="page"></c:set>
                    <c:if test="${sousCats.idCategorie!=0}">
                        <h2>Sous-catégories de la catégorie <c:out value="${sousCats.categorie}"></c:out></h2>
                        <c:choose>
                            <c:when test="${sousCats.nb==0}">
                                <div class="cadre">
                                    <div class="info">Aucune sous catégorie enregistrée.</div>
                                </div>
                                <br/>
                            </c:when>
                            <c:when test="${sousCats.nb>0}">
                                <div class="cadre">
                                    <div class="info"><c:out value="${sousCats.nb}"></c:out> sous-catégorie pour la catégorie "<c:out value="${sousCats.categorie}"></c:out>".</div>
                                <c:forEach var="i" begin="0" end="${sousCats.nb-1}" step="1">
                                    <div><c:out value="${sousCats.sousCategories[i]}"></c:out></div>
                                    <br/>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </c:if>
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
