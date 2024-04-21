import { Injectable } from '@nestjs/common';
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
}