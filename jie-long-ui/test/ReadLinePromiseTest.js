const readline = require('readline');
const fs = require('fs');

function readLineFromFile(file) {
    const readInterface = readline.createInterface({
        input: fs.createReadStream(file),
        output: null,
        console: false
    });

    return readInterface;
}

function extractTo(label, data) {
    return (line) => {
        console.log('from', label, line);
        if(line != null) {
            const parts = line.split(' ');

            if(parts.length == 2) {
                const k = parts[0];
                const v = parts[1];
                data[k] = Number(v);
            }
        }
    };
}

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
        const rif = readLineFromFile(`${dir}/${file}`);
        const data = {};
        rif.on('line', extractTo(file, data));

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
