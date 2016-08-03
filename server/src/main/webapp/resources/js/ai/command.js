function send() {
    var message = new Object();
    message.info = encodeURIComponent(document.getElementById('text').value);
    message.key = ai.appKey;
    message.loc = encodeURIComponent(ai.loc);
    message.id = ai.id;
    url = "command?message=" + JSON.stringify(message);
    
    document.getElementById('text').value = "";

    try {
        xmlhttp = window.XMLHttpRequest ? new XMLHttpRequest()
                : new ActiveXObject("Microsoft.XMLHTTP");
    } catch (e) {
    }

    xmlhttp.onreadystatechange = function() {
        if ((xmlhttp.readyState == 4) && (xmlhttp.status == 200)) {
            console.log(xmlhttp.responseText);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);
}