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
// rif1.on('line', function(line) {
//     console.log('from f1:', line);

//     if(line != null) {
//         const parts = line.split(' ');

//         if(parts.length == 2) {
//             const k = parts[0];
//             const v = parts[1];
//             data1[k] = Number(v);
//         }
//     }
// });

// let fileProcessed = 0;
// function merge(label, data) {
//     return () => {
//         fileProcessed++;
//         console.log('recieved close for', label, data);
//         if(fileProcessed === 2) {
//             console.log('got em');
//         }
//     }
// }

function curryMerge(expected, cb) {
    const allData = [];
    const merged = {};
    return (data) => {
        allData.push(data);
        if(allData.length === expected) {
            for(let i=0; i<allData.length; i++) {
                const d = allData[i];
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
            console.log(merged);
            if (typeof cb == 'function') {
                cb(merged);
            }
        }
    };
}

function mergeAllData(allData) {
    const merged = {};
    for(let i=0; i<allData.length; i++) {
        const d = allData[i];
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

// () => {
//     console.log('recieved close 1', data1);
// }

const dir = '../../src/test/resources/';
const mergeResult = curryMerge(2, (result) => {
    console.log('merged result', result);
});

const filesToBeProcessed = ['sample1.txt', 'sample2.txt'];
const parsedData = [];
let processedCnt = 0;
filesToBeProcessed.forEach((f, idx) => {
    console.log(idx, f);
    const rlff = readLineFromFile(`${dir}/${f}`);
    const data = {};
    parsedData.push(data);
    rlff.on('line', extractTo(f, data));
    rlff.on('close', () => { 
        processedCnt++;
        if (processedCnt === filesToBeProcessed.length) {
            console.log(mergeAllData(parsedData));
        }
    });
});


// const f1 = 'sample1.txt';
// const data1 = {};
// const rif1 = readLineFromFile(`${dir}/${f1}`);
// rif1.on('line', extractTo(f1, data1));
// rif1.on('close', () => {mergeResult(data1)});


// const f2 = 'sample2.txt';
// const data2 = {};
// const rif2 = readLineFromFile(`${dir}/${f2}`);
// rif2.on('line', extractTo(f2, data2));
// rif2.on('close', () => {mergeResult(data2)});

