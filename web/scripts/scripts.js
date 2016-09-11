function displayMap(address) {
    var map = new GMap2(document.getElementById("map_canvas"));
    map.setCenter(new GLatLng(49.41483, 2.817895), 10);
    map.addControl(new GSmallZoomControl());
    var geocoder = new GClientGeocoder();
    if (geocoder) {
        geocoder.getLatLng(address, function(point) {
            if (!point) { /* Si les coordonnées n'ont pas été trouvés */
                alert("Impossible de localiser l'adresse :\n\n" + address);
            } else {
                /* /* Les coordonnées ont été trouvés */
                /* Centrer la carte sur le point */
                map.setCenter(point, 9);
                /* Création d'un marqueur */
                var marker = new GMarker(point);
                /* Afficher le marqueur */
                map.addOverlay(marker);
            }
        });
    }
}
function signalerAbus(idAnnonce) {
    option=2;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./signaler-abus-"+idAnnonce+".html");
    }    
}
function changeCategorie(idCategorie) {
    option=3;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./change-categorie-"+idCategorie+".html");
    }    
}
function changeRegion(idRegion) {
    option=0;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./change-region-"+idRegion+".html");
    }    
}
function changeDepartement(idDepartement) {
    option=1;
    xhr=null;
    xhr=getXMLHttpRequest();
    if(xhr!=null) {
        sendReq(xhr, "./change-departement-"+idDepartement+".html");
    }    
}
function sendReq(xhr, file) {
    xhr.onreadystatechange=traiteReponse;
    xhr.open("GET", file, true);
    xhr.send(null);
}
function traiteReponse() {
    if(xhr.readyState==4&&(xhr.status==200||xhr.status==0)) {
        switch(option) {
            case 0:
                document.getElementById("idDepartements").innerHTML=xhr.responseText;
                break;
            case 1:
                document.getElementById("idCommunes").innerHTML=xhr.responseText;
                break;
            case 2:
                alert(xhr.responseText);
                break;
            case 3:
                document.getElementById("idsSousCategories").innerHTML=xhr.responseText;
                break;
        }
    }
}
function getXMLHttpRequest() {
    var xhr=null;
    if(window.XMLHttpRequest||window.ActiveXObject) {
        if(window.ActiveXObject) {
            try {
                xhr=new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e) {
                xhr=new ActibeXObject("Microsoft.XMLHTTP");
            }
        } else {
            xhr=new XMLHttpRequest();
        }
    } else {
        return null;
    }
    return xhr;
}
