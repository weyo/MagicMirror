var ai = {
	aiLocation: '.ai',
    sayingLocation: '.saying',
    url: 'localhost:8080/server/websocket',
	fadeInterval: config.ai.fadeInterval,
    cleanInterval: config.ai.cleanInterval,
    appKey: config.ai.AppKey,
    loc: config.ai.loc,
    id: config.ai.id,
    mmAvatar: 'lmm.jpg',
    defAvatar: 'avatar.png',
    sayingTime: new Date(),
    intervalId : null
}

ai.saying = function (data) {
    
    var dt_head = '<dt><image class="avatar ';
    var dt_mid1 = '" src="resources/images/';
    var dt_mid2 = '" /><div class="words ';
    var dt_mid3 = '">';
    var dt_tail = '</div><br class="clear" /></dt>';
    var avatar = ai.mmAvatar;
    var words = data.slice(2);
    var loc = 'fleft';
    
    var line = "";
    /*
     * 接收数据解析：
     * 0 —— AI
     * 1 —— 用户
     * 2 —— AI 返回超链接
     */
    switch (data[0]) {
        case '1':
            avatar = ai.defAvatar;
            loc = 'fright';
        case '0':
            line = dt_head + loc + dt_mid1 + avatar + dt_mid2 + loc + dt_mid3 + words + dt_tail;
            break;
        case '2':
            sn = words.indexOf('|');
            line = dt_head + loc + dt_mid1 + avatar + dt_mid2 + loc + dt_mid3 + words.slice(0, sn) + dt_tail;
            $(ai.sayingLocation).append(line);
            ai.scrollWords();
            realUrl = words.slice(sn + 1);
            // 临时修复返回的去哪儿网链接异常问题
            if (realUrl.indexOf("touch.qunar.com/h5/flight/flightlist?bd_source=chongdong&") > 0) {
                realUrl.replace("bd_source=chongdong&", "");
            }
            line = '<dt><IFRAME name="XXX" frameborder=0 width=390px height=480px src="' + realUrl + '"></IFRAME></dt>';
            break;
        default:
            console.log('不支持的数据:' + data);
            break;
    }
    
    $(ai.sayingLocation).append(line);
    ai.scrollWords();
    ai.sayingTime = new Date();
}

ai.scrollWords = function() {
    var height = $(ai.sayingLocation).outerHeight();
    var offset = 795 - height;
    if (offset < 0) {
        $(ai.sayingLocation).animate({
            marginTop: offset + 'px',
        }, ai.fadeInterval);
    }
}

ai.clearWords = function () {
    var nowTime = new Date();
    if (nowTime - ai.sayingTime > ai.cleanInterval) {
        $(ai.sayingLocation).empty();
        $(ai.sayingLocation).css({"marginTop": "0"});
    }
}

ai.init = function () {
    
    new websocket(this.url, this.saying);
    
    this.intervalId = setInterval(function() {
        this.clearWords();
    }.bind(this), this.cleanInterval);
    
}