<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:catch var="ex">
    <c:set var="sousCat" value="${null}" scope="page"></c:set>
    <c:set var="tagTitle" value="Annonces d'emplois - Emplois services" scope="page"></c:set>
    <c:set var="tagDescription" value="Emplois services - Emplois à domicile - Toutes les annonces d'emploi en France." scope="page"></c:set>
    <c:if test="${requestScope.sousCategorie!=null}">
        <c:set var="sousCat" value="${requestScope.sousCategorie}" scope="page"></c:set>
        <c:set var="tagTitle" value="${sousCat.tagTitle}" scope="page"></c:set>
        <c:set var="tagDescription" value="${sousCat.tagDescription}" scope="page"></c:set>
    </c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><c:out value="${tagTitle}"></c:out></title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="<c:out value="${tagDescription}"></c:out>"/>
<link rel="icon" type="image/png" href="./GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="./CSS/style.css" />
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
           <c:if test="${requestScope.info!=null}">
               <c:choose>
                   <c:when test="${requestScope.info==1}">
                       <div class="cadre">
                           <div class="info">Sous-catégorie inconnue !</div>
                       </div>
                   </c:when>
               </c:choose>
           </c:if>
           <div class="colonneGauche">
               <c:if test="${sousCat!=null&&requestScope.sousCategories!=null}">
                   <c:set var="sousCats" value="${requestScope.sousCategories}" scope="page"></c:set>
                   <nav>
                       <ul class="menuListe">
                           <li class="retour" onclick="javascript:window.location.href='./';">
                               <a href="./" title="RETOUR">RETOUR</a>
                           </li>
                           <c:if test="${sousCats.nb>0}">
                               <c:forEach var="i" begin="0" end="${sousCats.nb-1}" step="1">
                                   <c:choose>
                                       <c:when test="${sousCat.id==sousCats.ids[i]}">
                                           <li class="actuel"><c:out value="${sousCats.sousCategories[i]}"></c:out></li>
                                       </c:when>
                                       <c:otherwise>
                                           <li class="item" onclick="javascript:window.location.href='<c:out value="${sousCats.urls[i]}"></c:out>'">
                                               <a href="<c:out value="${sousCats.urls[i]}"></c:out>" title="Annonce d'emploi - <c:out value="${sousCat.categorie}"></c:out> - <c:out value="${sousCats.sousCategories[i]}"></c:out>"><c:out value="${sousCats.sousCategories[i]}"></c:out></a>
                                           </li>
                                       </c:otherwise>
                                   </c:choose>
                               </c:forEach>
                           </c:if>
                       </ul>
                   </nav>
               </c:if>
           </div>
           <div class="colonneDroite">
               <c:if test="${sousCat!=null&&requestScope.annonces!=null}">
                   <c:set var="ans" value="${requestScope.annonces}" scope="page"></c:set>
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
                  <h2>Annonces d'<strong>emploi</strong> - <c:out value="${sousCat.categorie}"></c:out> - <c:out value="${sousCat.sousCategorie}"></c:out></h2>
                   <c:choose>
                       <c:when test="${ans.nbAnnoncesPage==0}">
                           <div class="cadre">
                               <div class="info">Désolé, aucune annonce dans cette catégorie.</div>
                           </div>
                           <p></p>
                                   <p></p>                    
                       </c:when>
                       <c:when test="${ans.nbAnnoncesPage>0}">
                           <div class="cadre">
                               <div class="info"><c:out value="${ans.nbAnnonces}"></c:out> annonce(s) pour cette catégorie.</div>
                           </div>
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
                                   <c:forEach var="i" begin="0" end="${ans.nbAnnoncesPage-1}" step="1">
                                     <div class="cadreAnnonce" onclick="javascript:window.location.href='<c:out value="${ans.urls[i]}"></c:out>';">
                                        <div class="gauche">${ans.imgs[i]}</div>
                                        <div class="droite">
                                            <h1>
                                                <a href="<c:out value="${ans.urls[i]}"></c:out>" title="<c:out value="${ans.titres[i]}"></c:out>"><c:out value="${ans.titres[i]}"></c:out></a>
                                            </h1>
                                            <p>
                                                ${ans.lignes1[i]}
                                                <br/>
                                                ${ans.lignes2[i]}
                                                <br/>
                                                ${ans.lignes3[i]}
                                                <br/>
                                                <br/>
                                                ${ans.lignes4[i]}
                                                <br/>
                                            </p>
                                        </div>
                                    </div>
                                                <p></p>
                                    </c:forEach>
                                                <p></p>
                                                <div class="pages">
                                                    <span>Pages d'annonces :</span>
                                                    <c:forEach var="i" begin="${ans.prem}" end="${ans.der}" step="1">
                                                        <c:choose>
                                                            <c:when test="${i==ans.page}">
                                                                <span>[<span class="clign"><c:out value="${i+1}"></c:out></span>]</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span>[<a href="./emplois-sous-categorie2-<c:out value="${sousCat.idCategorie}"></c:out>-<c:out value="${sousCat.id}"></c:out>-<c:out value="${i}"></c:out>-<c:out value="${sousCat.encodedSousCategorie}"></c:out>.html" title="PAGE #<c:out value="${i+1}"></c:out>"><c:out value="${i+1}"></c:out></a>]</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </div>
                                                <p></p>
                       </c:when>
                   </c:choose>
               </c:if>
           </div>
        </section>
        <%@include file="./footer.jsp" %>
    </body>
</html>
</c:catch>
<c:if test="${not empty ex}">
    <section>
        <div class="erreur">
            <div>ERREUR</div>
            <p></p>
            <div><c:out value="${ex.message}"></c:out></div>
        </div>
    </section>
</c:if>
