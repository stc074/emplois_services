<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:catch var="ex">
    <c:set var="tagTitle" value="Emploi service - Emploi à domicile - Annonces d'emploi" scope="page"></c:set>
    <c:set var="tagDescription" value="Emploi services - Toutes les annonces d'emplois service et d'emplois à domicile dans votre région." scope="page"></c:set>
    <c:set var="ans" value="${null}" scope="page"></c:set>
    <c:if test="${requestScope.annonces!=null}">
        <c:set var="ans" value="${requestScope.annonces}" scope="page"></c:set>
        <c:set var="tagTitle" value="${ans.tagTitle}" scope="page"></c:set>
        <c:set var="tagDescription" value="${ans.tagDescription}" scope="page"></c:set>
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
            <c:if test="${ans!=null}">
                <header>
           <div class="cadre">
           <p>Sur cette page vous trouverez toutes les annonces, utilisez le formulaire pour affiner votre recherche.</p>
           </div>
           <p></p>
           <nav>
           <div id="form">
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
           <c:if test="${not empty ans.errorMsg}">
               <div class="erreur">
                   <div>Erreur(s) :</div>
                   <p></p>
                   ${ans.errorMsg}
               </div>
               <p></p>
           </c:if>
               <form action="./annonces1.html#form" method="POST">
                   <fieldset>
                       <legend>Recherche</legend>
                       <p>
                       <label for="motsCles">Recherche :</label>
                       <input type="text" name="motsCles" id="motsCles" value="${ans.motsCles}" size="40" maxlength="300" />
                       <br/>
                       <span>Type d'annonce :</span>
                       <input type="radio" name="type" id="type1" value="1"<c:if test="${ans.type==1}"> checked="checked"</c:if> onclick="javascript:window.location.href='./annonces-1-1.html#form';" />
                       <label for="type1">Demandes d'emploi&nbsp;</label>
                       <input type="radio" name="type" id="type2" value="2"<c:if test="${ans.type==2}"> checked="checked"</c:if> onclick="javascript:window.location.href='./annonces-1-2.html#form';" />
                       <label for="type2">Offres d'emploi&nbsp;</label>
                       <br/>
                       <label for="idCategorie">Catégorie de l'<strong>emploi</strong> :</label>
                       <select name="idCategorie" id="idCategorie" onchange="javascript:window.location.href='./annonces-2-'+this.value+'.html#form';">
                           <option value="0"<c:if test="${ans.idCategorie==0}"> selected="selected"</c:if>>Choisissez</option>
                           <c:if test="${requestScope.categories!=null}">
                               <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                               <c:if test="${cats.nb>0}">
                                   <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                       <option value="<c:out value="${cats.ids[i]}"></c:out>"<c:if test="${ans.idCategorie==cats.ids[i]}"> selected="selected"</c:if>><c:out value="${cats.categories[i]}"></c:out></option>
                                   </c:forEach>
                               </c:if>
                           </c:if>
                       </select>
                           <label for="idSousCategorie">Sous-catégorie de l'<strong>emploi</strong> :</label>
                           <select name="idSousCategorie" id="idSousCategorie" onchange="javascript:window.location.href='./annonces-3-'+this.value+'.html#form'">
                               <option value="0"<c:if test="${ans.idSousCategorie==0}"> selected="selected"</c:if>>Choisissez</option>
                               <c:if test="${requestScope.sousCategories!=null}">
                                   <c:set var="sousCats" value="${requestScope.sousCategories}" scope="page"></c:set>
                                   <c:if test="${sousCats.nb>0}">
                                       <c:forEach var="i" begin="0" end="${sousCats.nb-1}" step="1">
                                           <option value="<c:out value="${sousCats.ids[i]}"></c:out>"<c:if test="${ans.idSousCategorie==sousCats.ids[i]}"> selected="selected"</c:if>><c:out value="${sousCats.sousCategories[i]}"></c:out></option>
                                       </c:forEach>
                                   </c:if>
                               </c:if>
                           </select>
                           <br/>
                           <span>Tarif horaire net :</span>
                           <label for="tarifHoraireMin">Minimum :</label>
                           <input type="text" name="tarifHoraireMin" id="tarifHoraireMin" value="${ans.tarifHoraireMin}" size="6" maxlength="10" />
                           <span>&euro;&nbsp;</span>
                           <label for="tarifHoraireMax">Maximum :</label>
                           <input type="text" name="tarifHoraireMax" id="tarifHoraireMax" value="${ans.tarifHoraireMax}" size="6" maxlength="10" />
                           <span>&euro;</span>
                           <br/>
                           <div>Localisation de l'<strong>emploi</strong> :</div>
                           <label for="idRegion">Région :</label>
                           <select name="idRegion" id="idRegion" onchange="javascript:window.location.href='./annonces-4-'+this.value+'.html#form';">
                               <option value="0"<c:if test="${ans.idRegion=='0'}"> selected="selected"</c:if>>Choisissez</option>
                               <c:if test="${requestScope.listeRegions!=null}">
                                   <c:set var="listeR" value="${requestScope.listeRegions}" scope="page"></c:set>
                                   <c:if test="${listeR.nbRegions>0}">
                                       <c:forEach var="i" begin="0" end="${listeR.nbRegions-1}" step="1">
                                           <option value="<c:out value="${listeR.idsRegion[i]}"></c:out>"<c:if test="${ans.idRegion==listeR.idsRegion[i]}"> selected="selected"</c:if>><c:out value="${listeR.regions[i]}"></c:out></option>
                                       </c:forEach>
                                   </c:if>
                               </c:if>
                           </select>
                               <label for="idDepartement">Département :</label>
                               <select name="idDepartement" id="idDepartement" onchange="javascript:window.location.href='./annonces-5-'+this.value+'.html#form';">
                                   <option value="0"<c:if test="${ans.idDepartement=='0'}"> selected="selected"</c:if>>Choisissez</option>
                                   <c:if test="${requestScope.listeDepartements!=null}">
                                       <c:set var="listeD" value="${requestScope.listeDepartements}" scope="page"></c:set>
                                       <c:if test="${listeD.nbDepartements>0}">
                                           <c:forEach var="i" begin="0" end="${listeD.nbDepartements-1}" step="1">
                                               <option value="<c:out value="${listeD.ids[i]}"></c:out>"<c:if test="${ans.idDepartement==listeD.ids[i]}"> selected="selected"</c:if>><c:out value="${listeD.departements[i]}"></c:out></option>
                                           </c:forEach>
                                       </c:if>
                                   </c:if>
                               </select>
                                   <label for="idCommune">Commune :</label>
                                   <select name="idCommune" id="idCommune" onchange="javascript:window.location.href='./annonces-6-'+this.value+'.html#form';">
                                       <option value="0"<c:if test="${ans.idCommune==0}"> selected="selected"</c:if>>Choisissez</option>
                                       <c:if test="${requestScope.listeCommunes!=null}">
                                           <c:set var="listeC" value="${requestScope.listeCommunes}" scope="page"></c:set>
                                           <c:if test="${listeC.nbCommunes>0}">
                                               <c:forEach var="i" begin="0" end="${listeC.nbCommunes-1}" step="1">
                                                   <option value="<c:out value="${listeC.ids[i]}"></c:out>"<c:if test="${ans.idCommune==listeC.ids[i]}"> selected="selected"</c:if>><c:out value="${listeC.communes[i]}"></c:out></option>
                                               </c:forEach>
                                           </c:if>
                                       </c:if>
                                   </select>
                                       <br/>
                       <input type="submit" value="Valider" name="kermit" />
                       <input type="submit" value="Vider le formulaire" name="reset" />
                       <br/>
                       </p>
                   </fieldset>
               </form>
           </div>
           </nav>
                </header>
                                       <c:if test="${ans.nbAnnoncesPage==0}">
                                           <br/>
                                           <div class="cadre">
                                               <div class="info">Désolé, aucune annonce pour cette recherche.</div>
                                           </div>
                                           <br/>
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
                                       </c:if>
                                           <c:if test="${ans.nbAnnoncesPage>0}">
                                               <h2><c:out value="${ans.nbAnnonces}"></c:out> annonce(s) pour cette recherche</h2>
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
<br/>
<c:forEach var="i" begin="0" end="${ans.nbAnnoncesPage-1}" step="1">
    <div class="cadreAnnonce" onclick="javascript:window.location.href='${ans.urls[i]}';">
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
                        ${ans.lignes4[i]}
                        <br/><br/>
                        <c:out value="${ans.lignes3[i]}"></c:out>
                        <br/>
                        </p>
        </div>
    </div>
            <br/>
</c:forEach>
            <div class="pages">
                <span>Pages d'annonces :</span>
                <c:forEach var="i" begin="${ans.prem}" end="${ans.der}" step="1">
                    <c:choose>
                        <c:when test="${i==ans.page}">
                            <span>[<span class="clign"><c:out value="${i+1}"></c:out></span>]</span>
                        </c:when>
                        <c:otherwise>
                            <span>[<a href="./annonces-7-<c:out value="${i}"></c:out>.html#form" title="PAGE #<c:out value="${i+1}"></c:out>"><c:out value="${i+1}"></c:out></a>]</span>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <br/>
                                           </c:if>
            </c:if>
        </section>
        <%@include file="./footer.jsp" %>
    </body>
</html>
</c:catch>
<c:if test="${not empty ex}">
    <html>
        <head>
            <title>ERREUR</title>
        </head>
        <body>
            <section>
                <div class="erreur">
                    <div>ERREUR :</div>
                    <br/>
                    <div><c:out value="${ex.message}"></c:out>
                </div>
                <br/>
            </section>
        </body>
    </html>
</c:if>
