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
                <h1>Messages envoyés</h1>
                <c:if test="${requestScope.info!=null}">
                    <c:choose>
                        <c:when test="${requestScope.info==1}">
                            <div class="cadre">
                                <p></p>
                                <div class="info">Vous devez être connecté pour pouvoir voir vos messages.</div>
                                <p></p>
                                <div>
                                    <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                                </div>
                                <p></p>
                            </div>
                        </c:when>
                    </c:choose>
                </c:if>
                <c:if test="${requestScope.messages!=null}">
                    <c:set var="msgs" value="${requestScope.messages}" scope="page"></c:set>
                    <c:choose>
                        <c:when test="${msgs.nb==0}">
                            <div class="cadre">
                                <div class="info">Aucun message envoyé dans votre boite.</div>
                            </div>
                        </c:when>
                        <c:when test="${msgs.nb>0}">
                            <div class="cadre">
                                <div class="info"><c:out value="${msgs.nb}"></c:out> message(s) envoyé(s) dans votre boite :</div>
                            </div>
                            <p></p>
                            <c:forEach var="i" begin="0" end="${msgs.nb-1}" step="1">
                                <div class="cadre">
                                    <p>
                                        <span><a href="./message-envoye-<c:out value="${msgs.ids[i]}"></c:out>.html" rel="nofollow" title="<c:out value="${msgs.titres[i]}"></c:out>"><c:out value="${msgs.titres[i]}"></c:out></a></span>
                                        ${msgs.comments[i]}
                                        <span>&nbsp;&nbsp;&rArr;<a href="./efface-envoye-<c:out value="${msgs.ids[i]}"></c:out>.html" rel="nofollow" title="ÉFFACER CE MESSAGE">ÉFFACER CE MESSAGE</a></span>
                                    </p>
                                </div>
                                    <p></p>
                            </c:forEach>
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
