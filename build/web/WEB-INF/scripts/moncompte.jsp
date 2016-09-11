<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Emploi services - Annonces d'emplois - Emploi à domicile</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Emploi services - Toutes les annonces GRATUITES d'emplois services et emplois à domicile."/>
<link rel="icon" type="image/png" href="./GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="./CSS/style.css" />
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
    </head>
    <body>
        <%@include file="./haut.jsp" %>
        <section>
            <p>
                           <br/>
<div>
<a name="fb_share" type="button_count" share_url="http://www.emploiservices.net">Cliquez pour partager ce site sur Facebook !!!</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div>
           <br/>
           <g:plusone></g:plusone>
           <br/>
            </p>
            <c:catch var="ex">
                <h1>EMPLOI SERVICES - MON COMPTE</h1>
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <div class="cadre">
                                <br/>
                                <div class="info">Vous devez être connecté pour pouvoir accéder à votre compte</div>
                                <br/>
                                <div>
                                    <a href="./inscription.html" rel="nofollow" title="INSCRIPTION">S'INSCRIRE</a>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.membre!=null}">
                    <div class="cadre">
                        <div>
                            <a href="./infos-personnelles.html" rel="nofollow" title="INFOS PERSONNELLES">INFOS PERSONNELLES</a>
                        </div>
                    </div>
                    <br/>
                    <div class="cadre">
                        <div>
                            <a href="./mes-annonces.html" rel="nofollow" title="MES ANNONCES">MES ANNONCES</a>
                        </div>
                    </div>
                    <br/>
                    <div class="cadre">
                        <div>
                            <a href="./messagerie.html" rel="nofollow" title="MA MESSAGERIE">MA MESSAGERIE</a>
                        </div>
                    </div>
                    <br/>
                    <div class="cadre">
                        <div>
                            <a href="./supprimer-compte.html" rel="nofollow" title="SUPPRIMER DÉFINITIVEMENT MON COMPTE">SUPPRIMER MON COMPTE</a>
                        </div>
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
        <%@include file="./footer.jsp" %>
    </body>
</html>
