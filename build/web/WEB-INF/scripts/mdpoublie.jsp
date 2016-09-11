<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Emploi services - Mot de passe oublié</title>
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
                <h1>Mot de passe oublié</h1>
                <c:if test="${requestScope.membre!=null}">
                    <c:set var="mem" value="${requestScope.membre}" scope="page"></c:set>
                    <h2>Vous avez oublié votre mot de passe ? ...</h2>
                    <p>...ce n'est pas grave nous allons vous en envoyer un autre.</p>
                    <c:choose>
                        <c:when test="${mem.test==0}">
                    <div id="form">
                        <c:if test="${not empty mem.errorMsg}">
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <br/>
                                ${mem.errorMsg}
                            </div>
                        </c:if>
                        <fieldset>
                            <legend>Adresse Email de mon compte :</legend>
                            <form action="<c:url value="./mdp-oublie.html#form"></c:url>" method="POST">
                                <label for="email">Saississez l'Adresse-Email de votre compte :</label>
                                <br/>
                                <input type="text" name="email" id="email" value="<c:out value="${mem.email}"></c:out>" size="30" maxlength="100" />
                                <br/>
                                <br/>
                                <div class="captcha"></div>
                                <div class="captchaDroite">
                                    <label for="captcha">&rarr;COPIEZ LE CODE SVP&rarr;</label>
                                    <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                </div>
                                <br/>
                                <br/>
                                <input type="submit" value="Valider" name="kermit" />
                                <br/>
                            </form>
                        </fieldset>
                    </div>
                        </c:when>
                        <c:when test="${mem.test==1}">
                            <div class="cadre">
                                <div class="info">Un nouveau mot de passe vous a été envoyé dans votre boite mail.</div>
                            </div>
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
        <%@include file="./footer.jsp" %>
    </body>
</html>
