import { Injectable } from '@nestjs/common';
import { Prisma } from '@prisma/client';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class HistoryService {
    constructor(private prisma: PrismaService) {}

    async GetHistoryById(id: number) {
        try {
            const res = await this.prisma.history.findUnique({
                where: {
                    id: id
                }
            });
            if(res) {
                return res;
            }else  {
                return {message: 'No history found'};
            } 
        } catch (error) {
            throw new Error(error);
        }
    }

    async getTopTenHistoriesByUserId(userId: number) {
        try {
            const res = await this.prisma.history.findMany({
                where: {
                    user_id: userId
                },
                take: 10
            });
            if(res) {
                return res;
            }else  {
                return {message: 'No history found'};
            } 
        } catch (error) {
            throw new Error(error);
        }
    }

    async AddHistory(data : Prisma.historyUncheckedCreateInput) {
        try {
            const res = await this.prisma.history.create({
                data
            });
            if(res) {
                return res;
            }else  {
                return {message: 'Failed to add history'};
            } 
        } catch (error) {
            throw new Error(error);
        }
    }
}
