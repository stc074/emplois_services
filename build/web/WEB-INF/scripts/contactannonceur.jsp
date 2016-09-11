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
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="./GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="./CSS/style.css" />
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
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
           <p></p>
            <c:catch var="ex">
                <h1>Contacter un annonceur</h1>
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <div class="cadre">
                                <p></p>
                                <div class="info">Vous devez être connecté pour pouvoir contacter un annonceur.</div>
                                <p></p>
                                <div>
                                    <a href="./inscription.html" rel="nofollow" title="S'INSCRITE">S'INSCRIRE</a>
                                </div>
                                <p></p>
                            </div>
                        </c:when>
                        <c:when test="${requestScope.info==2}">
                            <div class="cadre">
                                <div class="info">Annonce inconnue !</div>
                            </div>
                        </c:when>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.message!=null}">
                    <c:set var="msg" value="${requestScope.message}" scope="page"></c:set>
                    <c:choose>
                        <c:when test="${msg.test==0}">
                    <div class="cadre">
                        <p>Annonce "<c:out value="${msg.titreAnnonce}"></c:out>" - Annonceur : <c:out value="${msg.membreDestinataire.pseudo}"></c:out>.</p>
                    </div>
                    <p></p>
                    <div class="cadre">
                        <div class="info">Pour contacter <c:out value="${msg.membreDestinataire.pseudo}"></c:out>, utilisez le formulaire ci-dessous :</div>
                    </div>
                    <p></p>
                    <div id="form">
                        <c:if test="${not empty msg.errorMsg}">
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <p></p>
                                ${msg.errorMsg}
                            </div>
                            <p></p>
                        </c:if>
                            <form action="./contact-annonceur.html#form" method="POST">
                                <fieldset>
                                    <legend>Contacter <c:out value="${msg.membreDestinataire.pseudo}"></c:out></legend>
                                    <input type="hidden" name="idAnnonce" value="<c:out value="${msg.idAnnonce}"></c:out>" />
                                    <p>
                                    <label for="titreMsg">Objet de votre message :</label>
                                    <br/>
                                    <input type="text" name="titreMsg" id="titreMsg" value="<c:out value="${msg.titreMsg}"></c:out>" size="30" maxlength="80" />
                                    <br/>
                                    <label for="contenuMsg">Contenu de votre message :</label>
                                    <br/>
                                    <textarea name="contenuMsg" id="contenuMsg" rows="4" cols="20">${msg.contenuMsg}</textarea>
                                    </p>
                                    <div class="captcha"></div>
                                    <div class="captchaDroite">
                                        <label for="captcha">&rarr;COPIEZ LE CODE SVP&rarr;</label>
                                        <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                    </div>
                                    <p>
                                        <br/>
                                        <input type="submit" value="Valider" name="kermit" />
                                        <br/>
                                    </p>
                                </fieldset>
                            </form>
<script type="text/javascript">
	CKEDITOR.replace( 'contenuMsg' );
</script>
                    </div>
                        </c:when>
                    <c:when test="${msg.test==1}">
                        <div class="cadre">
                            <div class="info">Message envoyé !</div>
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
