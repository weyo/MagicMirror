var aqi = {
    city: config.aqi.city,
    apiBase: 'http://www.pm25.in/api/querys/only_aqi.json?city=',
    apiStation: '&stations=',
    apiToken: '&token=',
    appKey: config.aqi.AppKey,
    aqiLocation: '.aqi',
    updateInterval: config.aqi.interval,
    fadeInterval: config.aqi.fadeInterval,
}

/**
 * Retrieves the current aqi from the PM25.in API
 */
aqi.updateCurrentAqi = function () {

	$.ajax({
		type: 'GET',
		url: aqi.apiBase + aqi.city + aqi.apiStation + 'no' + aqi.apiToken + aqi.appKey,
		dataType: 'json',
		success: function (data) {
            var _aqis = eval(data);
            var _currentAqi = aqis[0];
            var _aqiValue = _currentAqi.aqi;
            var _aqiQuality = _currentAqi.quality;
            var _aqiPrimaryPollutant = _currentAqi.primary_pollutant;

			list = $('<ul/>').css({'list-style-type': 'none', 'margin': '0px', 'padding': '0px'});
            list.append($('<li/>').html("空气质量指数：" + _aqiValue));
            list.append($('<li/>').html("空气质量：" + _aqiQuality));
            list.append($('<li/>').html("首要污染物：" + _aqiPrimaryPollutant));

			$(this.aqiLocation).updateWithText(list, this.fadeInterval);

		}.bind(this),
		error: function () {

		}
	});

}

aqi.init = function () {

	this.intervalId = setInterval(function () {
		this.updateCurrentAqi();
	}.bind(this), this.updateInterval);

}