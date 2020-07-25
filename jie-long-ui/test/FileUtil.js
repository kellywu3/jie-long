const readline = require('readline');
const fs = require('fs');

exports.readLineFromFile = (file) => {
    const readInterface = readline.createInterface({
        input: fs.createReadStream(file),
        output: null,
        console: false
    });

    return readInterface;
};

exports.extractTo = (label, data) => {
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
};

// exports.dummyValue = 'hello';

// exports.dummyFunction = function abc(def) {
//     console.log('from abc:', def);
// };

// const x = {
//     readLineFromFile() {

//     },
//     dummyValue: 'hello',
//     dummyFunction: (def) => {
//         console.log('from abc:', def);
//     }
// };

// module.exports = {};
// export = module.exports;


// exports = x;
// module.exports = x;

// module.exports = parse(the content of the file);
// exports = module.exports;

// exports = x;