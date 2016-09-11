<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Emploi services - Déposer une annonce - Identification</title>
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
            <p></p>
<div>
<a name="fb_share" type="button_count" share_url="http://www.emploiservices.net">Cliquez pour partager ce site sur Facebook !!!</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div>
            <p></p>
           <g:plusone></g:plusone>
           <p></p>
            <c:catch var="ex">
                <h1>Déposer une annonce - Identification</h1>
                <div class="cadre">
                    <p>Vous devez vous identifier pour pouvoir déposer une annonce, soit vous êtes connecté, soit vous vous connectez, ou si vous n'avez pas de compte, inscrivez vous.</p>
                </div>
                <br/>
                <c:if test="${sessionScope.idMembre!=null&&sessionScope.pseudo!=null}">
                    <div class="cadre">
                        <div>Membre déja connecté &rarr; <c:out value="${sessionScope.pseudo}"></c:out>&nbsp;&rarr;&nbsp;<a href="./deposer-annonce-2.html" rel="nofollow" title="CLIQUEZ POUR CONTINUER">C'EST MOI</a></div>
                    </div>
                    <br/>
                </c:if>
                <c:if test="${requestScope.membre1!=nul}">
                    <c:set var="mem1" value="${requestScope.membre1}" scope="page"></c:set>
                    <div id="form1">
                        <c:if test="${not empty mem1.errorMsg}">
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <br/>
                                ${mem1.errorMsg}
                            </div>
                            <br/>
                        </c:if>
                            <fieldset>
                                <legend>Je possède un compte, je me connecte</legend>
                                <form action="./deposer-annonce-1.html#form1" method="POST">
                                    <p>
                                        <label for="pseudo">Mon PSEUDONYME :</label>
                                        <br/>
                                        <input type="text" name="pseudo" id="pseudo" value="<c:out value="${mem1.pseudo}"></c:out>" size="15" maxlength="30" />
                                        <br/>
                                        <label for="motDePasse">Mon MOT DE PASSE :</label>
                                        <br/>
                                        <input type="password" name="motDePasse" id="motDePasse" value="" size="15" maxlength="15" />
                                        <br/>
                                        <input type="submit" value="Valider" name="kermit1" />
                                        <br/>
                                    </p>
                                </form>
                            </fieldset>
                    </div>
                                        <br/>
                </c:if>
                <c:if test="${requestScope.membre2!=null}">
                    <c:set var="mem2" value="${requestScope.membre2}" scope="page"></c:set>
                    <div id="form2">
                        <c:if test="${not empty mem2.errorMsg}">
                            <div class="erreur">
                                <div>Erreur(s) :</div>
                                <br/>
                                ${mem2.errorMsg}
                            </div>
                            <br/>
                        </c:if>
                            <fieldset>
                                <legend>Je n'ai pas de compte, je m'inscris</legend>
                                <form action="./deposer-annonce-1.html#form2" method="POST">
                                    <p>
                                    <label for="pseudo">Je choisis un PSEUDONYME [Caractères alphanumériques] :</label>
                                    <br/>
                                    <input type="text" name="pseudo" id="pseudo" value="<c:out value="${mem2.pseudo}"></c:out>" size="15" maxlength="30" />
                                    <br/>
                                    <label for="email">Je saisis mon ADRESSE EMAIL :</label>
                                    <br/>
                                    <input type="text" name="email" id="email" value="<c:out value="${mem2.email}"></c:out>" size="30" maxlength="100" />
                                    <br/>
                                    <label for="motDePasse">Je choisis un MOT DE PASSE [Caractères alphanumériques] :</label>
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
                                        <option value="0"<c:if test="${mem2.idRegion=='0'}"> selected="selected"</c:if>>Choisissez</option>
                                        <c:if test="${requestScope.listeRegions!=null}">
                                            <c:set var="listeR" value="${requestScope.listeRegions}" scope="page"></c:set>
                                            <c:if test="${listeR.nbRegions>0}">
                                                <c:forEach var="i" begin="0" end="${listeR.nbRegions-1}" step="1">
                                                    <option value="<c:out value="${listeR.idsRegion[i]}"></c:out>"<c:if test="${mem2.idRegion==listeR.idsRegion[i]}"> selected="selected"</c:if>><c:out value="${listeR.regions[i]}"></c:out></option>
                                                </c:forEach>
                                            </c:if>
                                        </c:if>
                                    </select>
                                        <br/>
                                        <div id="idDepartements">
                                        <label for="idDepartement">Votre DÉPARTEMENT :</label>
                                        <select name="idDepartement" id="idDepartement" onchange="javascript:changeDepartement(this.value);">
                                            <option value="0"<c:if test="${mem2.idDepartement=='0'}"> selected="selected"</c:if>>Choisissez</option>
                                            <c:if test="${requestScope.listeDepartements!=null}">
                                                <c:set var="listeD" value="${requestScope.listeDepartements}" scope="page"></c:set>
                                                <c:if test="${listeD.nbDepartements>0}">
                                                    <c:forEach var="i" begin="0" end="${listeD.nbDepartements-1}" step="1">
                                                        <option value="<c:out value="${listeD.ids[i]}"></c:out>"<c:if test="${mem2.idDepartement==listeD.ids[i]}"> selected="selected"</c:if>><c:out value="${listeD.departements[i]}"></c:out></option>
                                                    </c:forEach>
                                                </c:if>
                                            </c:if>
                                        </select>
                                        <br/>
                                        <div id="idCommunes">
                                            <label for="idCommune">Votre COMMUNE :</label>
                                            <select name="idCommune" id="idCommune">
                                                <option value="0"<c:if test="${mem2.idCommune==0}"> selected="selected"</c:if>>Choisissez</option>
                                                <c:if test="${requestScope.listeCommunes!=null}">
                                                    <c:set var="listeC" value="${requestScope.listeCommunes}" scope="page"></c:set>
                                                    <c:if test="${listeC.nbCommunes>0}">
                                                        <c:forEach var="i" begin="0" end="${listeC.nbCommunes-1}" step="1">
                                                            <option value="<c:out value="${listeC.ids[i]}"></c:out>"<c:if test="${mem2.idCommune==listeC.ids[i]}"> selected="selected"</c:if>><c:out value="${listeC.communes[i]}"></c:out></option>
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
                                        <input type="radio" name="cgus" id="cgus" value="1" checked="checked" />
                                        <label for="cgus">En validant ce formulaire, je déclare avoir pris connaissance des conditions générales d'utilisation de ce site et les accepter.</label>
                                        <br/>
                                        <input type="submit" value="Valider" name="kermit2" />
                                        <br/>
                                    </p>
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
