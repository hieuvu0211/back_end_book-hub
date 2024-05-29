import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
import { updatePasswordDto } from 'src/dto/changePassword.dto';
import { SsoDTO } from 'src/dto/sso.dto';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class UserService {
    constructor(private prisma: PrismaService) {}
    async findUserByid(id : string) {
        const res = await this.prisma.user.findFirst({
            where: {
                user_id: parseInt(id)
            }
        });
        if(res) return res;
        else return {status: 400, message: 'No user found'};
        
    }

    async Login(data: Prisma.userCreateInput) {
        try {
            if(data.password == "@") {
                throw new Error('User not found');
            }
            const res = await this.prisma.user.findUnique({
                where: {
                    username: data.username,
                    password: data.password
                }
            });
            if(res) {
                console.log("login = ", res)
                return res;
            }
            else throw new Error('User not found');
        } catch (error) {
            throw new Error(error);
        }
    }

    async Register(data: Prisma.userCreateInput) {
        try {
            const res = await this.prisma.user.create({
                data: data
            });
            if(res) return res;
            else return {status: 400, message: 'User not created'};
        } catch (error) {
            throw new Error(error);  
        }
    }

    async UpdatePassword(data: updatePasswordDto) {
        try {
            const res = await this.prisma.user.findUnique({
                where: {
                    username: data.username,
                    password: data.oldPassword,
                }
            });
            if(res) {
                const update = await this.prisma.user.update({
                    where: {
                        username: data.username
                    },
                    data: {
                        password: data.newPassword
                    }
                });
                if(update) return update;
                else return {status: 400, message: 'Password not updated'};
            }
            else return {status: 400, message: 'Password not updated'};
        } catch (error) {
            throw new Error(error);  
        }
    }

    async RegisterWithSSO(data: SsoDTO) {
        console.log("data = ", data)
        try {
            const res = await this.prisma.user.findFirst({
                where: {
                    uid: data.uid
                }
            });
            if(res) {
                //get update information 
                const ans = await this.prisma.user.update({
                    where: {
                        user_id: res.user_id
                    },
                    data: {
                        username: data.username,
                        imgurl: data.imgurl
                    }
                });
                if(ans) return ans;
                return res
            }
            else {
                const user = await this.prisma.user.create({
                    data: {
                        uid: data.uid,
                        username: data.username ? data.username : data.email,
                        imgurl: data.imgurl ? data.imgurl : null,
                        password: "@"
                    }
                });
                if(user) return user;
                else throw new Error('User not created');
            }
        } catch (error) {
            throw new Error(error);
        }
    }

    //handle user upload file type image
    async handleFileUpload(id: string, filename: string) {
        //update image url to db
        try {
            const res = await this.prisma.user.update({
                where: {
                    user_id: parseInt(id)
                },
                data: {
                    imgurl: `http://10.0.2.2:8080/userUpload/${filename}`
                }
            });
            if(res) return res;
            else throw new Error('Image not updated');
        }catch(error) {
            throw new Error("Error updating image url");
        }
    }
    
}