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
                try {
                    const listRes = []
                    res.forEach(element => {
                        listRes.push(element.book_id)
                    });
                    const handleRes = await this.prisma.book.findMany({
                        where: {
                            book_id : {
                                in : listRes
                            }
                        }
                    })
                    if(handleRes.length > 0 || handleRes) {
                        return handleRes;
                    } else return { status: 400, message: 'No favorite found' };
                } catch (error) {
                    throw new Error(error);
                }
            } else return { status: 400, message: 'No favorite found' };
        } catch (error) {
            throw new Error(error);
        }
    }
}
