const fs = require('fs');
// import fs from "fs";

// const reader = fs.readFile("package.json", "utf8", (err, data) => {
//     if (err) {
//       console.error(err);
//     } else {
//       console.log(data);
//     }
//   });

try {
    const stats = fs.statSync("package.json");
    console.log({
      size: stats.size,
      mtime: stats.mtime,
      isFile: stats.isFile(),
    });
  } catch (err) {
    console.error(err);
  }