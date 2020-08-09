//const axios = require('axios');

exports.calcCardEndPoint = function (subPath) {
    return `/api/card${subPath}`;
};

exports.calcMessageEndPoint = function (subPath) {
    return `/api/message${subPath}`;
};

exports.establishSse = function () {
    const endPointUrl = exports.calcMessageEndPoint('/jie-long/emitter');
    return new EventSource(endPointUrl);
};