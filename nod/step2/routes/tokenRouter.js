var express = require('express');

// import bcrypt from 'bcryptjs';
// import jwt from 'jsonwebtoken';
// import config from "../../config/index";
// const {JWT_SECRET} = config;
// import User from '../../models/user'

const router = express.Router();

// 회원가입
router.post('/',async(req,res)=>{
    // console.log(req);
    // const {name, email, password} = req.body;

    // if(!name || !email || !password){
    //     return res.status(400).json({msg:"모든 필드를 채워주세요."});
    // }

    // User.findOne({email}).then((user)=>{
    //     if(user) return res.status(400).json({msg:"이미 가입된 이메일이 존재합니다."})
    //     const newUser = new User({
    //         name, email, password
    //     })
        
    //      // bcrypt : Blowfish를 기반으로 만들어진 "단방향 암호화 해싱함수" 사용
    //     // 공격자가 암호를 유추할 수 없도록, 평문 데이터에 의미 없는 데이터를 뿌려 넣는데, 이것을 salt라고 한다.
    //     // 해시-단방향 암호화 기법으로 해시함수(해시 알고리즘)를 이용하여 고정된 길이의 암호화된 문자열로 바꿔버리는 것
    //     bcrypt.genSalt(10,(err,salt)=>{
    //         bcrypt.hash(newUser.password, salt, (err,hash)=>{
    //             if(err) throw err;
    //             newUser.password = hash;
    //             newUser.save().then((user)=>{
    //                 jwt.sign(
    //                     {id: user.id},
    //                     JWT_SECRET,
    //                     {expiresIn: 3600}, // 3600초
    //                     (err,token)=>{
    //                         if(err) throw err;
    //                         res.json({
    //                             token,
    //                             user:{
    //                                 id:user.id,
    //                                 name:user.name,
    //                                 email:user.email
    //                             }
    //                         })
    //                     }
    //                 )
    //             })
    //         })
    //     })
    // });
})

export default router;