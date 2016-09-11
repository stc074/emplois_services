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
                <h1>Emploi services - Inscription</h1>
                <c:if test="${requestScope.membre!=null}">
                    <c:set var="mem" value="${requestScope.membre}" scope="page"></c:set>
                    <c:choose>
                        <c:when test="${mem.test==0}">
                <div class="cadre">
                    <div class="info">Pour vous inscrire, rien de plus simple, utilisez le formulaire ci-dessous :</div>
                </div>
                <br/>
                <div id="form">
                    <c:if test="${not empty mem.errorMsg}">
                        <div class="erreur">
                            <div>Erreur(s) :</div>
                            <br/>
                            ${mem.errorMsg}
                        </div>
                    </c:if>
                    <fieldset>
                        <legend>Formulaire d'inscription</legend>
                        <form action="./inscription.html#form" method="POST">
                            <p>
                                <label for="pseudo">Veuillez choisir un PSEUDONYME [caractères alphanumériques] :</label>
                                <br/>
                                <input type="text" name="pseudo" id="pseudo" value="<c:out value="${mem.pseudo}"></c:out>" size="20" maxlength="30" />
                                <br/>
                                <label for="email">Saisissez votre ADRESSE EMAIL :</label>
                                <br/>
                                <input type="text" name="email" id="email" value="<c:out value="${mem.email}"></c:out>" size="40" maxlength="100" />
                                <br/>
                                <label for="motDePasse">Choisissez un MOT DE PASSE [Caractères alphanumériques] :</label>
                                <br/>
                                <input type="password" name="motDePasse" id="motDePasse" value="" size="15" maxlength="15" />
                                <br/>
                                <label for="motDePasse2">Confirmation du MOT DE PASSE :</label>
                                <br/>
                                <input type="password" name="motDePasse2" id="motDePasse2" value="" size="15" maxlength="15" />
                                <br/>
                            <div>Localisation :</div>
                            <label for="idRegion">Votre RÉGION :</label>
                            <select name="idRegion" id="idRegion" onchange="javascript:changeRegion(this.value);">
                                <option value="0"<c:if test="${mem.idRegion=='0'}"> selected="selected"</c:if>>Choisissez</option>
                                <c:if test="${requestScope.listeRegions!=null}">
                                    <c:set var="listeR" value="${requestScope.listeRegions}" scope="page"></c:set>
                                    <c:if test="${listeR.nbRegions>0}">
                                        <c:forEach var="i" begin="0" end="${listeR.nbRegions-1}" step="1">
                                            <option value="<c:out value="${listeR.idsRegion[i]}"></c:out>"<c:if test="${mem.idRegion==listeR.idsRegion[i]}"> selected="selected"</c:if>><c:out value="${listeR.regions[i]}"></c:out></option>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </select>
                                <div id="idDepartements">
                                    <label for="idDepartement">Votre DÉPARTEMENT :</label>
                                    <select name="idDepartement" id="idDepartement" onchange="javascript:changeDepartement(this.value);">
                                        <option value="0"<c:if test="${mem.idDepartement=='0'}"> selected="selected"</c:if>>Choisissez</option>
                                        <c:if test="${requestScope.listeDepartements!=null}">
                                            <c:set var="listeD" value="${requestScope.listeDepartements}" scope="page"></c:set>
                                            <c:if test="${listeD.nbDepartements>0}">
                                                <c:forEach var="i" begin="0" end="${listeD.nbDepartements-1}" step="1">
                                                    <option value="<c:out value="${listeD.ids[i]}"></c:out>"<c:if test="${mem.idDepartement==listeD.ids[i]}"> selected="selected"</c:if>><c:out value="${listeD.departements[i]}"></c:out></option>
                                                </c:forEach>
                                            </c:if>
                                        </c:if>
                                    </select>
                                        <br/>
                                        <div id="idCommunes">
                                            <label for="idCommune">Votre COMMUNE :</label>
                                            <select name="idCommune" id="idCommune">
                                                <option value="0"<c:if test="${mem.idCommune==0}"> selected="selected"</c:if>>Choisissez</option>
                                                <c:if test="${requestScope.listeCommunes!=null}">
                                                    <c:set var="listeC" value="${requestScope.listeCommunes}" scope="page"></c:set>
                                                    <c:if test="${listeC.nbCommunes>0}">
                                                        <c:forEach var="i" begin="0" end="${listeC.nbCommunes-1}" step="1">
                                                            <option value="<c:out value="${listeC.ids[i]}"></c:out>"<c:if test="${mem.idCommune==listeC.ids[i]}"> selected="selected"</c:if>><c:out value="${listeC.communes[i]}"></c:out></option>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:if>
                                            </select>
                                        </div>
                                </div>
                                                <br/>
                                                <div class="captcha"></div>
                                                <div class="captchaDroite">
                                                    <label for="captcha">&rarr;COPIEZ LE CODE SVP&rarr;</label>
                                                    <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                                </div>
                                                <br/>
                                                <br/>
                                                <input type="radio" name="cgu" id="cgu" value="1" checked="checked" />
                                                <label for="cgu">En validant ce formulaire, je déclare avoir prix connaissances des conditions d'utilisations de ce site et les accepter.</label>
                                                <br/>
                                                <br/>
                                        <input type="submit" value="Valider" name="kermit" />
                                        <br/>
                            </p>
                        </form>
                    </fieldset>
                </div>
                        </c:when>
                <c:when test="${mem.test==1}">
                    <div class="cadre">
                        <br/>
                        <div class="info">Vous êtes désormais inscrit !</div>
                        <br/>
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
