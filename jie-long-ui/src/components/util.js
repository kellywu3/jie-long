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

const CARD_RANKS = 'A23456789TJQK';

exports.rankToNum = (rank) => {
    const r = rank.toUpperCase();
    switch(rank) {
        case 'A':
            return 0;
        case 'T':
            return 9;
        case 'J': 
            return 10;
        case 'Q':
            return 11;
        case 'K':
            return 12;
        default:
            return r - '1';
    }
};

exports.numToRank = (num) => {
    CARD_RANKS.charAt(num);
}

