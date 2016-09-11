<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:catch var="ex">
    <c:set var="an" value="${null}" scope="page"></c:set>
    <c:set var="tagTitle" value="Emploi services - Annonces d'emploi - Empoli à domicile" scope="page"></c:set>
    <c:set var="tagDescription" value="Emploi services - Toutes les annonces d'emploi service en France" scope="page"></c:set>
    <c:if test="${requestScope.annonce!=null}">
        <c:set var="an" value="${requestScope.annonce}" scope="page"></c:set>
        <c:set var="tagTitle" value="${an.tagTitle}" scope="page"></c:set>
        <c:set var="tagDescription" value="${an.tagDescription}" scope="page"></c:set>
    </c:if>
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
<script src="./js-global/FancyZoom.js" type="text/javascript"></script>
<script src="./js-global/FancyZoomHTML.js" type="text/javascript"></script>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true&amp;key=ABQIAAAAv8cUw2UoxLrfnWsj7xeGNBR4OFpeS2o7ZgK2Abt46dww93CBlBR6pOwwtN0ltb1p1bLPRMi9xHc8sw" type="text/javascript"></script>
<script type="text/javascript" src="./scripts/scripts.js"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
    </head>
    <body onload="setupZoom()">
        <%@include file="./haut.jsp" %>
        <section>
           <c:if test="${requestScope.info!=null}">
                <c:choose>
                    <c:when test="${requestScope.info==1}">
                        <div class="cadre">
                            <div class="info">Annonce inconnue !</div>
                        </div>
                    </c:when>
                </c:choose>
            </c:if>
            <c:if test="${an!=null}">
                <div class="cadre2" id="annonce">
                    <p></p>
           <ul class="reseauxSoc2">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
           </ul>
           <p></p>
                    <c:set var="uriRetour" value="./annonces1.html#form" scope="page"></c:set>
                    <c:if test="${sessionScope.uriRetour!=null}">
                        <c:set var="uriRetour" value="${sessionScope.uriRetour}" scope="page"></c:set>
                    </c:if>
                    <div>
                        <a href="${uriRetour}" title="RETOUR À LA LISTE">RETOUR À LA LISTE</a>
                    </div>
                    <p></p>
                    <p></p>
                    <div>
                        <a href="#annonce" title="SIGNALER UN ABUS" rel="nofollow" onclick="javascript:signalerAbus(<c:out value="${an.id}"></c:out>);">SIGNALER UN ABUS</a>
                    </div>
                </div>
                    <p></p>
                    <div class="encartPub2">
<script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
                    </div>
                    <p></p>                            
                    <div class="cadre2">
                        <p></p>
                        <h1><c:out value="${an.titre}"></c:out></h1>
                        <div><c:out value="${an.ligne1}"></c:out></div>
                        <div>${an.ligne2}</div>
                        <p></p>
                        <div>${an.ligne3}</div>
                        <div><c:out value="${an.ligne4}"></c:out></div>
                        <p></p>
                    </div>
                        <p></p>
                        <div class="cadre2">
                            <h2>Description de cette <strong>annonce d'emploi</strong></h2>
                            ${an.description}
                        </div>
                        <p></p>
                    <div class="cadre2">
                        <h2>Photo et localisation</h2>
                        <div class="colonne">
                            <c:choose>
                                <c:when test="${empty an.imgCode}">
                                    <div class="info">Pas de photo disponible</div>
                                </c:when>
                                <c:when test="${not empty an.imgCode}">
                                    <div class="cadrePhoto">
                                        <div class="mini">
                                            <a href="${an.imgCode}" title="<c:out value="${an.pseudo}"></c:out>" rel="nofollow" zoom="1">${an.imgCodeMini1}</a>
                                        </div>
                                        <p></p>
                                        <div class="info">Cliquez dessus pour voir en taille réelle.</div>
                                        <p></p>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="colonne" id="map_canvas"></div>
                        <script type="text/javascript">
                            displayMap('<c:out value="${an.adresse}"></c:out>');
                        </script>
                    </div>
                        <p></p>
                        <div class="cadre2">
                            <h2>Contactez <c:out value="${an.pseudo}"></c:out></h2>
                            <div>Pour contacter <c:out value="${an.pseudo}"></c:out> (L'auteur de cette <strong>annonce d'emploi</strong>) <a href="./contact-annonceur-<c:out value="${an.id}"></c:out>.html" title="Contacter <c:out value="${an.pseudo}"></c:out>" rel="nofollow">CLIQUEZ ICI</a></div>
                            <p></p>
                        </div>
                            <p></p>
            </c:if>
        </section>
        <%@include file="./footer.jsp" %>
    </body>
</html>
</c:catch>
<c:if test="${not empty ex}">
    <html>
        <head>
            <title>ERREUR !</title>
        </head>
        <body>
            <section>
                <div class="erreur">
                    <div>ERREUR :</div>
                    <br/>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </section>
        </body>
    </html>
</c:if>
