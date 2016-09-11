<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
<c:if test="${requestScope.mbr!=null}">
    <c:set var="mb" value="${requestScope.mbr}" scope="page"></c:set>
<div class="connexion">
    <div>-CONNEXION-</div>
    <div id="formCnx">
        <c:if test="${not empty mbr.errorMsg}">
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                ${mb.errorMsg}
            </div>
        </c:if>
            <form action="#formCnx" method="POST">
                <p>
                <label for="pseudo">PSEUDONYME :</label>
                <br/>
                <input type="text" name="pseudo" id="pseudo" value="<c:out value="${mb.pseudo}"></c:out>" size="10" maxlength="30" />
                <br/>
                <label for="motDePasse">MOT DE PASSE :</label>
                <br/>
                <input type="password" name="motDePasse" id="motDePasse" value="" size="10" maxlength="15" />
                <br/>
                <input type="submit" value="Connexion" name="kermitCnx" />
                </p>
            </form>
    </div>
                <c:if test="${sessionScope.idMembre==null||sessionScope.pseudo==null}">
                    <div>Statut &rarr; Déconnecté</div>
                </c:if>
                <c:if test="${sessionScope.idMembre!=null&&sessionScope.pseudo!=null}">
                    <div>Connecté &rarr; <c:out value="${sessionScope.pseudo}"></c:out></div>
                </c:if>
                    <br/>
                    <div>
                        <a href="<c:url value="./mdp-oublie.html"></c:url>" rel="nofollow" title="MOT DE PASSE OUBLIÉ">MOT DE PASSE OUBLIÉ</a>
                    </div>
                    <br/>
</div>
</c:if>
    <div class="logo" onclick="javascript:window.location.href='./';"></div>
</header>
<nav>
    <ul class="menu">
        <li>
            <a href="./" rel="nofollow" title="ACCUEIL">ACCUEIL</a>
        </li>
        <li>
            <a href="./annonces.html" title="TOUTES LES ANNONCES">TOUTES LES ANNONCES</a>
        </li>
        <li>
            <a href="./deposer-annonce-1.html" rel="nofollow" title="DÉPOSER UNE ANNONCE">DÉPOSER UNE ANNONCE</a>
        </li>
        <li><a href="./inscription.html" rel="nofollow" title="INSCRIPTION">INSCRIPTION</a></li>
        <li>
            <a href="./mon-compte.html" rel="nofollow" title="MON COMPTE">MON COMPTE</a>
        </li>
        <li>
            <a href="./messagerie.html" rel="nofollow" title="MA MESSAGERIE">MA MESSAGERIE</a>
        </li>
    </ul>
</nav>