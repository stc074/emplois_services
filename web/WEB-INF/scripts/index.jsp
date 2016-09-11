<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Emplois services -  Services à domicile</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Toutes les annonces d'emplois services ou à de services domicile près de chez vous, offres et demandes sur toute la France."/>
<link rel="icon" type="image/png" href="./GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="./CSS/style.css" />
<meta name="google-site-verification" content="IYERrMbKLGqKoPJOE6JLYja4rEelLKd7E8li1F6XyeQ" />
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
    </head>
    <body>
<div id="fb-root"></div>
<script>
    (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/fr_FR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
        <%@include file="./haut.jsp" %>
        <section>
            <c:catch var="ex">
                <h1>Emplois services - Services à domicile</h1>
                <p>Bienvenue sur ce site totalement gratuit qui vous aidera à trouver un job !</p>
                <p>Que vous soyez à la recherche d'un petit boulot, ou d'une personne pour effectuer un travail, ce site est fait pour vous.<br/>
                    Déposez gratuitement une annonce en 2 minutes <a href="./deposer-annonce-1.html" title="DÉPOSER UNE ANNONCE">EN CLIQUANT ICI</a>.</p>
                <div class="colonneGauche">
                    <nav>
                    <c:if test="${requestScope.listeRegions!=null}">
                        <c:set var="listeR" value="${requestScope.listeRegions}" scope="page"></c:set>
                        <c:if test="${listeR.nbRegions>0}">
                            <ul class="menuListe">
                                <li>Régions</li>
                                <c:forEach var="i" begin="0" end="${listeR.nbRegions-1}" step="1">
                                    <li class="item" onclick="javascript:window.location.href='<c:out value="${listeR.urls[i]}"></c:out>';">
                                        <a href="<c:out value="${listeR.urls[i]}"></c:out>" title="<c:out value="Emploi région ${listeR.regions[i]}"></c:out>"><c:out value="${listeR.regions[i]}"></c:out></a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </c:if>
                    <c:if test="${requestScope.categories!=null}">
                        <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                        <c:if test="${cats.nb>0}">
                            <ul class="menuListe">
                                <li>Catégories</li>
                                <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                    <li class="item" onclick="javascript:window.location.href='<c:out value="${cats.urls[i]}"></c:out>';">
                                        <a href="<c:out value="${cats.urls[i]}"></c:out>" title="Emplois de la catégorie : <c:out value="${cats.categories[i]}"></c:out>"><c:out value="${cats.categories[i]}"></c:out></a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </c:if>
                    </nav>
                </div>
                <div class="colonneDroite">
                <c:if test="${requestScope.annonces!=null}">
                    <c:set var="ans" value="${requestScope.annonces}" scope="page"></c:set>
                    <h2>Les dernières annonces</h2>
                    <p></p>
           <ul class="reseauxSoc">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
               <li>
                   <div class="fb-like" data-send="true" data-layout="button_count" data-width="450" data-show-faces="true"></div>
               </li>
           </ul>
                                            <p></p>
                                                                <div class="encartPub">
<script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
                                               </div>
                                    <p></p>                    
                    <c:choose>
                        <c:when test="${ans.nb==0}">
                        <div class="cadre">
                            <div class="info">Aucune annonce enregistrée.</div>
                        </div>
                        </c:when>
                        <c:when test="${ans.nb>0}">
                            <c:forEach var="i" begin="0" end="${ans.nb-1}" step="1">
                                    <div class="cadreAnnonce" onclick="javascript:window.location.href='<c:out value="${ans.urls[i]}"></c:out>';">
                                        <div class="gauche">
                                            ${ans.imgs[i]}
                                        </div>
                                        <div class="droite">
                                            <h1>
                                                <a href="${ans.urls[i]}" title="<c:out value="${ans.titres[i]}"></c:out>"><c:out value="${ans.titres[i]}"></c:out></a>
                                            </h1>
                                                        <p>
                                                        ${ans.lignes1[i]}
                                                        <br/>
                                                        ${ans.lignes2[i]}
                                                        <br/>
                                                        ${ans.lignes3[i]}
                                                        <br/><br/>
                                                        ${ans.lignes4[i]}
                                                        <br/>
                                                        </p>
                                        </div>
                                    </div>
                                            <p></p>
                            </c:forEach>
                                            <p></p>
                                            <div class="lienBas">
                                                <a href="./annonces.html" title="TOUTES LES ANNONCES">TOUTES LES ANNONCES</a>
                                            </div>
                                            <p></p>
                                    <p></p>
                        </c:when>
                    </c:choose>
                </c:if>
                </div>
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
