import { Injectable } from '@nestjs/common';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class FavoriteService {
    constructor(private prisma: PrismaService) {}

    async getFavoriteByUserId(userId: string) {
        try {
            const res = await this.prisma.favorite.findMany({
                where: {
                    user_id: Number(userId),
                },
            });
            if (res.length > 0 || res) {
                return res;
            } else return { status: 400, message: 'No favorite found' };
        } catch (error) {
            throw new Error(error);
        }
    }
}
