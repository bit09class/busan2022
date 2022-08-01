const jsonexport = require('jsonexport');

jsonexport({lang: 'Node.js', module: 'jsonexport'}, {rowDelimiter: '|'}, function(err, csv){
    if (err) return console.error(err);
    console.log(csv);
});
const fs = require('fs');

const reader = fs.createReadStream('package.json');
const writer = fs.createWriteStream('outPack.csv');

reader.pipe(jsonexport()).pipe(writer);