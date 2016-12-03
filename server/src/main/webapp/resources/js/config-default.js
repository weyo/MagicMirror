var config = {
    lang: 'zh-cn',
    time: {
        timeFormat: 24, // 时间格式
    },
    weather: {
        params: {
            q: 'Shanghai', // 城市
            units: 'metric',
            //lang: 'zh-cn',
            APPID: '***' // 在 OpenWeatherMap 网站上注册的 APPID
        },
        interval: 60000,
        fadeInterval: 1000
    },
    aqi: {
        city: 'shanghai', // 城市
        AppKey: '***', // 在 PM25.in 网站上注册的 AppKey
        interval: 300000,
        fadeInterval: 1000
    },
    ai: {
        AppKey: '***', // 在 tuling123.com 网站上注册的 Key
        loc: '上海市', // 城市名，中文
        id: 12345678, // 会话 ID，任意设置一个数字
        fadeInterval: 500,
        cleanInterval: 300000 // 最后一次接收到新的对话请求之后的等待清理对话列表时间
    },
    compliments: {
        interval: 30000,
        fadeInterval: 4000,
        timeParams: {
            morning: 3,
            afternoon: 12,
            evening: 18
        },
        morning: [
            '喜欢清晨的阳光啊',
            '昨晚睡得好吗',
            '啊哈，又没吃早饭吧'
        ],
        afternoon: [
            '休息，休息一下',
            '喝杯下午茶吧',
            '今天晚上吃什么'
        ],
        evening: [
            '太阳下山啦',
            '要不要来点夜宵',
            '晚安'
        ]
    },
    calendar: {
        maximumEntries: 10, // 可以显示的最大日程数量
		url: "https://p01-calendarws.icloud.com/ca/subscribe/1/n6x7Farxpt7m9S8bHg1TGArSj7J6kanm_2KEoJPL5YIAk3y70FpRo4GyWwO-6QfHSY5mXtHcRGVxYZUf7U3HPDOTG5x0qYnno1Zr_VuKH2M"
    },
    news: {
        //feed: 'http://news.baidu.com/n?cmd=1&class=finannews&tn=rss'
		feed: 'http://rss.sina.com.cn/roll/finance/hot_roll.xml',
    }
}

config.doGet = function(url) {
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

config.getParams = function(callback) {
    var aiParams = new Object();
    aiParams.key = ai.appKey;
    aiParams.loc = encodeURIComponent(ai.loc);
    aiParams.id = ai.id;
    url = "init?ai=" + JSON.stringify(aiParams);
    
    callback(url);
}

config.init = function() {
    this.getParams(this.doGet);
}
