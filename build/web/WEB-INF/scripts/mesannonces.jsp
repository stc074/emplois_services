<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Emploi services - Mes annonces</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Emploi services - Toutes les annonces GRATUITES d'emplois services et emplois à domicile."/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="./GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="./CSS/style.css" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
    </head>
    <body>
        <%@include file="./haut.jsp" %>
        <section>
            <p></p>
<div>
<a name="fb_share" type="button_count" share_url="http://www.emploiservices.net">Cliquez pour partager ce site sur Facebook !!!</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div>
            <p></p>
           <g:plusone></g:plusone>
           <br/>
           <p></p>
            <c:catch var="ex">
                <h1>Mes annonces</h1>
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <div class="cadre">
                                <div class="info">Vous devez être connecté pour pouvoir lister vos annonces.</div>
                                <p></p>
                                <div>
                                    <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                                </div>
                            </div>
                            <p></p>
                        </c:when>
                    </c:choose>
                </c:if>
                        <c:if test="${requestScope.annonces!=null}">
                            <c:set var="ans" value="${requestScope.annonces}" scope="page"></c:set>
                            <div class="cadre">
                            <p>Voici la liste de vos annonces, vous pouvez editer vos annonces pour les modidier, les effacer(Définitif) ou les remettre en haut de la liste.</p>
                            </div>
                            <p></p>
                            <c:choose>
                                <c:when test="${ans.nb==0}">
                                    <div class="cadre">
                                        <div class="info">Aucune annonce enregistré pour ce compte.</div>
                                    </div>
                                    <p></p>
                                </c:when>
                                <c:when test="${ans.nb>0}">
                                    <c:forEach var="i" begin="0" end="${ans.nb-1}" step="1">
                                        <div class="cadre">
                                            <div><c:out value="${ans.titres[i]}"></c:out>&nbsp;-&nbsp;<c:out value="${ans.comments[i]}"></c:out><a href="./efface-annonce-<c:out value="${ans.ids[i]}"></c:out>.html" title="ÉFFACER CETTE ANNONCE">ÉFFACER CETTE ANNONCE</a></div>
                                            <div>
                                                <a href="./edit-annonce-<c:out value="${ans.ids[i]}"></c:out>.html" rel="nofollow" title="ÉDITER CETTE ANNONCE">ÉDITER CETTE ANNONCE</a>
                                            </div>
                                            <p></p>
                                            <div>
                                                <a href="./top-<c:out value="${ans.ids[i]}"></c:out>.html" rel="nofollow" title="REMETTRE EN HAUT DE LA LISTE">METTRE EN HAUT DE LA LISTE</a>
                                            </div>
                                            <p></p>
                                        </div>
                                            <p></p>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </c:if>
            </c:catch>
            <c:if test="${not empty ex}">
                <div class="erreur">
                    <div>ERREUR :</div>
                    <p></p>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </c:if>
        </section>
        <%@include file="./footer.jsp" %>
    </body>
</html>
