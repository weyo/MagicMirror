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
    compliments: {
        interval: 30000,
        fadeInterval: 4000,
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
