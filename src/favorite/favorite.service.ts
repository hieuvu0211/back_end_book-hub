import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
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

    async AddFavorite(data : Prisma.favoriteUncheckedCreateInput) {
        try {
            const check = await this.prisma.favorite.findMany({
                where:{
                    user_id : data.user_id,
                    book_id : data.book_id
                }
            })
            if(check.length > 0) {
                return { status: 400, message: 'Book already in favorite' }
            }else {
                try {
                    const res = await this.prisma.favorite.create({
                        data
                    })
                    if(res) {
                        return res
                    } else return { status: 400, message: 'Failed to add favorite' }
                } catch (error) {
                    throw new Error(error)
                }
            }
            
        } catch (error) {
            throw new Error(error)
        }
    }

    async DeleteFavorite(data : Prisma.favoriteUncheckedCreateInput) {
        try {
            const res = await this.prisma.favorite.deleteMany({
                where: {
                    user_id : data.user_id,
                    book_id : data.book_id
                }
            })
            if(res) {
                return res
            } else return { status: 400, message: 'Failed to delete favorite' }
        } catch (error) {
            throw new Error(error)
        }
    }
}
