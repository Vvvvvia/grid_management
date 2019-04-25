function timestampToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    // h = date.getHours() + ':';
    // m = date.getMinutes() + ':';
    // s = date.getSeconds();
    return Y+M+D;
}

function dateDays(start,end) {

    var d = end-start;
    var x = Math.floor(d/(24*3600*1000));

    // console.log(start+' '+ end+ ' '+ (x+1));

    return x+1;
}
