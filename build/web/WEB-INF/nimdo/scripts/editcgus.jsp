<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Empoi services - ASMINISTRATION - Éditer les cgus</title>
<meta charset="UTF-8" />
<meta name="generator" content="NETBEANS 7.0.1"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Emploi services - Toutes les annonces GRATUITES d'emplois services et emplois à domicile."/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<link rel="icon" type="image/png" href="../GFXs/favicon.png"/>
<link rel="stylesheet" type="text/css" href="../CSS/style.css" />
<script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
<%@include file="./../../scripts/analytics.jsp" %>
    </head>
    <body>
        <%@include file="./haut.jsp" %>
        <section>
            <c:catch var="ex">
                <h1>Éditer les cgus</h1>
                <c:if test="${requestScope.cgus!=null}">
                    <c:set var="cgs" value="${requestScope.cgus}" scope="page"></c:set>
                    <div class="cadre">
                        <div class="info">Dernières modifications, <c:out value="${cgs.dateTxt}"></c:out>.</div>
                    </div>
                    <p></p>
                    <div id="form">
                        <c:if test="${not empty cgs.errorMsg}">
                            <div class="erreur">
                                <div>ERREUR(S) :</div>
                                <p></p>
                                ${cgs.errorMsg}
                            </div>
                            <p></p>
                        </c:if>
                            <form action="./edit-cgus.html#form" method="POST">
                                <fieldset>
                                    <legend>CGUS</legend>
                                    <p>
                                    <label for="contenu">Contenu des cgus :</label>
                                    <br/>
                                    <textarea name="contenu" id="contenu" rows="4" cols="20">${cgs.contenu}</textarea>
                                    <br/>
                                    <input type="submit" value="Valider" name="kermit" />
                                    <br/>
                                    </p>
                                </fieldset>
                            </form>
<script type="text/javascript">
	CKEDITOR.replace( 'contenu' );
</script>
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
    </body>
</html>
