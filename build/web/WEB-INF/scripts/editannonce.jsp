<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Emploi services - Éditer une annonce</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Emploi services - Toutes les annonces GRATUITES d'emplois services et emplois à domicile."/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="./GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="./CSS/style.css" />
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
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
           <p></p>
            <c:catch var="ex">
                <h1>Éditer une annonce</h1>
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <div class="cadre">
                                <div class="info">Vous devez être connecté pour pouvoir éditer votre annonce.</div>
                                <p></p>
                                <div>
                                    <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                                </div>
                            </div>
                            <p></p>
                        </c:when>
                        <c:when test="${requestScope.info==2}">
                            <div class="cadre">
                                <div class="info">Annonce inconnue !</div>
                            </div>
                            <p></p>
                        </c:when>
                    </c:choose>
                </c:if>
                        <c:if test="${requestScope.annonce!=null}">
                            <c:set var="an" value="${requestScope.annonce}" scope="page"></c:set>
                            <div class="cadre">
                                <p>Pour modifier votre annonce, utilisez le formulaire ci-dessous.</p>
                            </div>
                            <p></p>
                            <div id="form">
                                <c:if test="${not empty an.errorMsg}">
                                    <div class="erreur">
                                        <div>Erreur(s) :</div>
                                        <p></p>
                                        ${an.errorMsg}
                                    </div>
                                    <p></p>
                                </c:if>
                                <c:set var="label1" value="" scope="page"></c:set>
                                <c:set var="label2" value="" scope="page"></c:set>
                                <c:choose>
                                    <c:when test="${an.type==1}">
                                        <c:set var="label1" value="souhaité" scope="page"></c:set>
                                    </c:when>
                                    <c:when test="${an.type==2}">
                                        <c:set var="label1" value="proposé" scope="page"></c:set>
                                    </c:when>
                                </c:choose>
                                    <form action="./edit-annonce.html#form" method="POST">
                                        <input type="hidden" name="idAnnonce" value="<c:out value="${an.id}"></c:out>" />
                                        <fieldset>
                                            <legend>Éditer mon annonce</legend>
                                            <div class="info"><c:out value="${an.dateDesc}"></c:out></div>
                                            <br/>
                                            <div class="info"><c:out value="${an.typeDesc}"></c:out></div>
                                            <br/>
                                            <label for="idCategorie">Catégorie de l'emploi :</label>
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
                                                        <p></p>
                                                        <label for="tarifHoraire">Salaire&nbsp;<c:out value="${label1}"></c:out>&nbsp;de l'heure :</label>
                                                        <input type="text" name="tarifHoraire" id="tarifHoraire" value="<c:out value="${an.tarifHoraire}"></c:out>" size="6" maxlength="10" />
                                                        <span>&nbsp;&euro;</span>
                                                        <p></p>
                                                        <label for="titre">Titre de votre annonce :</label>
                                                        <p></p>
                                                        <input type="text" name="titre" id="titre" value="<c:out value="${an.titre}"></c:out>" size="30" maxlength="80" />
                                                        <p></p>
                                                        <label for="description">Description de votre annonce :</label>
                                                        <p></p>
                                                        <textarea name="description" id="description" rows="4" cols="20">${an.description}</textarea>
                                                        <p></p>
                                                        <div class="captcha"></div>
                                                        <div class="captchaDroite">
                                                            <label for="captcha">&rarr;COPIEZ LE CODE SVP&rarr;</label>
                                                            <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                                        </div>
                                                        <p></p>
                                                        <input type="submit" value="Valider" name="kermit" />
                                                        <p></p>
                                        </fieldset>
                                    </form>
                            </div>
<script type="text/javascript">
	CKEDITOR.replace( 'description' );
</script>
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
