const util = require('./FileUtil');

function mergeAllResults(allResults) {
    const merged = {};
    for(let i=0; i<allResults.length; i++) {
        const d = allResults[i];
        for(let k in d) {
            const v = d[k];
            console.log(k, v);
            let arr = merged[k];
        
            if(arr == null) {
                arr = [];
                merged[k] = arr;
            }
            arr[i] = v;
        }
    }
    return merged;
}

const dir = '../../src/test/resources/';
const f1 = 'sample1.txt';
const f2 = 'sample2.txt';
const allResults = [];

const readFilePromise = (file) => {
    return new Promise((resolve) => {
        const rif = util.readLineFromFile(`${dir}/${file}`);
        const data = {};
        rif.on('line', util.extractTo(file, data));

        rif.on('close', () => {
            resolve(data);
        });

    });
};

readFilePromise(f1)
// .then(() => {
//     console.log('haha1 not promise');
//     return 'haha1';
// }).then(() => {
//     console.log('haha2 not promise');
//     return 'haha2';
// })
.then((result) => {
    console.log('promise resolved:', f1, result);
    allResults.push(result);
    return readFilePromise(f2);
}).then((result) => {
    console.log('promise resolved:', f2, result);
    allResults.push(result);
    return Promise.resolve(mergeAllResults(allResults));
}).then(result => {
    console.log('merged', result)
}).catch(err => {
    console.log('error:', err);
});
