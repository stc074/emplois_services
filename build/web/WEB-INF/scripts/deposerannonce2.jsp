<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Emploi services - Déposer une annonce - Contenu</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Emploi services - Toutes les annonces GRATUITES d'emplois services et emplois à domicile."/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="./GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="./CSS/style.css" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
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
            <c:catch var="ex">
                <h1>Déposer une annonce - Contenu</h1>
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <div class="cadre">
                                <br/>
                                <div class="info">Vous devez vous identier avant de pouvoir déposer une annonce.</div>
                                <br/>
                                <div>
                                    <a href="./deposer-annonce-1.html" rel="nofollow" title="S'INDENTIFIER">S'IDENTIFIER</a>
                                </div>
                                <br/>
                            </div>
                        </c:when>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.annonce!=null}">
                    <c:set var="an" value="${requestScope.annonce}" scope="page"></c:set>
                    <div id="form">
                        <c:if test="${not empty an.errorMsg}">
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <br/>
                                ${an.errorMsg}
                            </div>
                            <br/>
                        </c:if>
                            <fieldset>
                                <legend>Contenu de mon annonce</legend>
                                <form action="./deposer-annonce-2.html#form" method="POST">
                                    <p>
                                        <span>Type de l'annonce :</span>
                                        <input type="radio" name="type" id="type1" value="1"<c:if test="${an.type==1}"> checked="checked"</c:if> onclick="javascript:window.location.href='./deposer-annonce-2-1.html#form';" />
                                        <label for="type1">Demande d'emploi&nbsp;</label>
                                        <input type="radio" name="type" id="type2" value="2"<c:if test="${an.type==2}"> checked="checked"</c:if> onclick="javascript:window.location.href='./deposer-annonce-2-2.html#form';" />
                                        <label for="type2">Offre d'emploi&nbsp;</label>
                                        <br/>
                                        <c:if test="${an.type==1||an.type==2}">
                                            <c:choose>
                                                <c:when test="${an.type==1}">
                                                    <c:set var="label1" value="demande d'emploi" scope="page"></c:set>
                                                    <c:set var="label2" value="souhaité" scope="page"></c:set>
                                                </c:when>
                                                <c:when test="${an.type==2}">
                                                    <c:set var="label1" value="offre d'emploi" scope="page"></c:set>
                                                    <c:set var="label2" value="proposé" scope="page"></c:set>
                                                </c:when>
                                            </c:choose>
                                            <label for="idCategorie">Catégorie de votre&nbsp;<c:out value="${label1}"></c:out>&nbsp;:</label>
                                            <select name="idCategorie" id="idCategorie" onchange="javascript:changeCategorie(this.value);">
                                                <option value="0"<c:if test="${an.idCategorie==0}"> selected="selected"</c:if>>Choisissez</option>
                                                <c:if test="${requestScope.categories!=null}">
                                                    <c:set var="cats" value="${requestScope.categories}" scope="page"></c:set>
                                                    <c:if test="${cats.nb>0}">
                                                        <c:forEach var="i" begin="0" end="${cats.nb-1}" step="1">
                                                            <option value="<c:out value="${cats.ids[i]}"></c:out>"<c:if test="${an.idCategorie==cats.ids[i]}"> selected="selected"</c:if>><c:out value="${cats.categories[i]}"></c:out></option>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:if>
                                            </select>
                                                <br/>
                                        <div id="idsSousCategories">
                                            <label for="idSousCategorie">Sous-Catégorie de l'emploi :</label>
                                            <select name="idSousCategorie" id="idSousCategorie">
                                                <option value="0"<c:if test="${an.idSousCategorie==0}"> selected="selected"</c:if>>Choisissez</option>
                                                <c:if test="${requestScope.sousCategories!=null}">
                                                    <c:set var="sousCats" value="${requestScope.sousCategories}" scope="page"></c:set>
                                                    <c:if test="${sousCats.nb>0}">
                                                        <c:forEach var="i" begin="0" end="${sousCats.nb-1}" step="1">
                                                            <option value="<c:out value="${sousCats.ids[i]}"></c:out>"<c:if test="${an.idSousCategorie==sousCats.ids[i]}"> selected="selected"</c:if>><c:out value="${sousCats.sousCategories[i]}"></c:out></option>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:if>
                                            </select>
                                        </div>
                                        <label for="tarifHoraire">Salaire net&nbsp;<c:out value="${label2}"></c:out>&nbsp;de l'heure :</label>
                                        <input type="text" name="tarifHoraire" id="tarifHoraire" value="${an.tarifHoraire}" size="6" maxlength="10" />
                                        <span>&nbsp;&euro;.</span>
                                        <br/>
                                        <label for="titre">Titre de votre annonce :</label>
                                        <br/>
                                        <input type="text" name="titre" id="titre" value="<c:out value="${an.titre}"></c:out>" size="30" maxlength="80" />
                                        <br/>
                                        <label for="description">Description plus longue de votre annonce :</label>
                                        <br/>
                                        <textarea name="description" id="description" rows="4" cols="20">${an.description}</textarea>
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
<script type="text/javascript">
	CKEDITOR.replace( 'description' );
</script>                                        </c:if>
                                </form>
                            </fieldset>
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
