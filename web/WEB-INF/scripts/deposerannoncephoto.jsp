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
<script src="./js-global/FancyZoom.js" type="text/javascript"></script>
<script src="./js-global/FancyZoomHTML.js" type="text/javascript"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
    </head>
    <body onload="setupZoom()">
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
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <br/>
                            <div class="cadre">
                                <br/>
                                <div class="info">Vous devez être identifié pour pouvoir accèder à cette page.</div>
                                <br/>
                                <div>
                                    <a href="./deposer-annonce-1.html" rel="nofollow" title="S'IDENTIFIER">S'IDENTIFIER</a>
                                </div>
                                <br/>
                            </div>
                            <br/>
                        </c:when>
                        <c:when test="${requestScope.info==2}">
                            <br/>
                            <div class="cadre">
                                <br/>
                                <div class="info">Annonce inconnue !</div>
                                <br/>
                            </div>
                            <br/>
                        </c:when>
                    </c:choose>
                </c:if>
                        <c:if test="${requestScope.photo!=null}">
                            <c:set var="pho" value="${requestScope.photo}" scope="page"></c:set>
                            <h1><c:out value="${pho.titre}"></c:out> - Photo de l'annonce</h1>
                            <div class="cadre">
                            <p>Vous pouvez enregistrer une photo pour votre annonce, ceci est facultatif mais peut donner plus d'importance à votre annonce, vous pouvez mettre une photo de vous ou le logo de votre entreprise.</p>
                            </div>
                            <h2>Photo enregistrée :</h2>
                            <c:choose>
                                <c:when test="${empty pho.extension}">
                                    <div class="cadre">
                                        <div class="info">Aucune photo encore enregistrée.</div>
                                    </div>
                                    <br/>
                                </c:when>
                                <c:when test="${not empty pho.extension}">
                                    <div class="cadre2">
                                    <div class="cadrePhoto">
                                        <div class="mini">
                                        <a href="<c:out value="${pho.urlPhoto}"></c:out>" zoom="1" title="<c:out value="${pho.titre}"></c:out>"><img src="<c:out value="${pho.urlMini}"></c:out>" width="<c:out value="${pho.largeur}"></c:out>" height="<c:out value="${pho.hauteur}"></c:out>" alt="miniature"/>
                                        </a>
                                        </div>
                                    </div>
                                    </div>
                                        <br/>
                                </c:when>
                            </c:choose>
                            <div class="cadre">
                            <div class="info">Pour enregistrer une photo depuis votre ordinateur, utilisez le formulaire ci-dessous :</div>
                            </div>
                                    <br/>
                                    <div id="form">
                                        <c:if test="${not empty pho.errorMsg}">
                                            <div class="erreur">
                                                <div>Erreur(s) :</div>
                                                <br/>
                                                ${pho.errorMsg}
                                            </div>
                                            <br/>
                                        </c:if>
                                            <form action="./deposer-annonce-photo.html#form" method="POST" enctype="multipart/form-data">
                                                <fieldset>
                                                    <legend>Upload d'image</legend>
                                                    <label for="fichier">Fichier de votre image :</label>
                                                    <input type="file" name="fichier" id="fichier" />
                                                    <br/>
                                                    <input type="submit" value="Valider" name="kermit" />
                                                    <br/>
                                                </fieldset>
                                            </form>
                                    </div>
                            <br/>
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
