import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
import { updatePasswordDto } from 'src/dto/changePassword.dto';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class UserService {
    constructor(private prisma: PrismaService) {}
    async findAll() {
        try {
            const res = await this.prisma.user.findMany();
            if(res.length > 0 || res) {
                return res;
            }else   {
                return {status: 400, message: 'No user found'};
            }
        } catch (error) {
            throw new Error(error); 
        }
        return "ok"
    }

    async Login(data: Prisma.userCreateInput) {
        try {
            const res = await this.prisma.user.findUnique({
                where: {
                    username: data.username,
                    password: data.password
                }
            });
            if(res) {
                return res;
            }
            else {
                return {status: 400, message: 'No user found'};
            }
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
                    password: data.oldPassword
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
}