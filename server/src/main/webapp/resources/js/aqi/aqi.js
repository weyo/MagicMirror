var aqi = {
    city : config.aqi.city,
    apiBase : 'http://www.pm25.in/api/querys/only_aqi.json?city=',
    apiStation : '&stations=',
    apiToken : '&token=',
    appKey : config.aqi.AppKey,
    aqiInfoLocation : '.aqi-info',
    aqiLocation : '.aqi-value',
    aqiQualityLocation : '.aqi-quality',
    updateInterval : config.aqi.interval,
    fadeInterval : config.aqi.fadeInterval,
    intervalId : null
}

aqi.updateData = function(data) {
        var _aqis = eval(data);
        var _currentAqi = _aqis[0];
        var _aqiValue = _currentAqi.aqi;
        var _aqiQuality = _currentAqi.quality;
        var _aqiPrimaryPollutant = _currentAqi.primary_pollutant;

        $(aqi.aqiInfoLocation).updateWithText('空气质量指数', aqi.fadeInterval);
        $(aqi.aqiLocation).updateWithText(_aqiValue, aqi.fadeInterval);
        $(aqi.aqiQualityLocation).updateWithText(_aqiQuality,
                aqi.fadeInterval);
};

/**
 * Retrieves the current aqi from the PM25.in API
 */
aqi.updateCurrentAqi = function(callback) {
    url = "aqi?url=" + encodeURIComponent(aqi.apiBase + aqi.city + aqi.apiStation + 'no'
                    + aqi.apiToken + aqi.appKey);

    try {
        xmlhttp = window.XMLHttpRequest ? new XMLHttpRequest()
                : new ActiveXObject("Microsoft.XMLHTTP");
    } catch (e) {
    }

    xmlhttp.onreadystatechange = function() {
        if ((xmlhttp.readyState == 4) && (xmlhttp.status == 200)) {
            callback(xmlhttp.responseText);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);

}

aqi.init = function() {
    this.updateCurrentAqi(this.updateData);

    this.intervalId = setInterval(function() {
        this.updateCurrentAqi(this.updateData);
    }.bind(this), this.updateInterval);

}