import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
import { HistoryDTO } from 'src/dto/index.dto';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class HistoryService {
    constructor(private prisma: PrismaService) {}

    async getHistoriesByUserId(userId: string) {
        let returnData = [];
        try {
            const res = await this.prisma.history.findMany({
                where: {
                    user_id: parseInt(userId)
                },
                orderBy : {
                    start_date : 'desc'
                }
            });
            if(res) {
                //get book details from book_id
                for(let i=0; i<res.length; i++) {
                    const book = await this.prisma.book.findUnique({
                        where: {
                            book_id: res[i].book_id
                        }
                    });
                    if(book) {
                        returnData.push({
                            user_id: res[i].user_id,
                            book_id: res[i].book_id,
                            book_name: book.book_name,
                            start_date: res[i].start_date,
                            last_read_page: res[i].last_read_page,
                            status: res[i].status
                        });
                    }
                }
                return returnData;
            }else  {
                return {message: 'No history found'};
            } 
        } catch (error) {
            throw new Error(error);
        }
    }

    async getTopTenHistoriesByUserId(userId: string) {
        let returnData = [];
        try {
            const res = await this.prisma.history.findMany({
                where: {
                    user_id: parseInt(userId)
                },
                orderBy : {
                    start_date : 'desc'
                },
                take: 10
            });
            if(res) {
                //get book details from book_id
                for(let i=0; i<res.length; i++) {
                    const book = await this.prisma.book.findUnique({
                        where: {
                            book_id: res[i].book_id
                        }
                    });
                    if(book) {
                        returnData.push({
                            user_id: res[i].user_id,
                            book_id: res[i].book_id,
                            book_name: book.book_name,
                            start_date: res[i].start_date,
                            last_read_page: res[i].last_read_page,
                            status: res[i].status
                        });
                    }
                }
                return returnData;
            }else  {
                return {message: 'No history found'};
            } 
        } catch (error) {
            throw new Error(error);
        }
    }

    async UpdateHistory(data : HistoryDTO) {
        try {
            //check if history exists
            const check = await this.prisma.history.findMany({
                where:{
                    AND : [
                        {user_id : data.user_id},
                        {book_id : data.book_id}
                    ]
                }
            });
            if(check.length > 0) {
                //update history
                const res = await this.prisma.history.update({
                    where: {
                        id: check[0].id
                    },
                    data: {
                        start_date: data.start_date,
                        last_read_page: data.last_read_page,
                        status: data.status
                    }
                });
                if(res) {
                    return true;
                }else {
                    return false;
                }
            }else {
                //add history
                const res = await this.prisma.history.create({
                    data: {
                        user_id: data.user_id,
                        book_id: data.book_id,
                        start_date: data.start_date,
                        last_read_page: data.last_read_page,
                        status: data.status
                    }
                });
                if(res) {
                    return true;
                }else {
                    return false;
                }
            }
        } catch (error) {
            throw new Error(error);
        }
    }
}
