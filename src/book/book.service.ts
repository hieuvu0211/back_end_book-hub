import { Injectable } from '@nestjs/common';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class BookService {
    constructor(private prisma: PrismaService) {}

    async getAllBooks() {
        try {
            const res = await this.prisma.book.findMany();
            if(res.length > 0 || res) {
                return res;
            }
            else return {status: 400, message: 'No book found'};
        } catch (error) {
            throw new Error(error); 
        }
    }

    async getBookById(id: string) {
        try {
            const res = await this.prisma.book.findUnique({
                where: {
                    book_id: Number(id)
                }
            });
            if(res) {
                return res;
            }
            else {
                return {status: 400, message: 'No book found'};
            }
        } catch (error) {
            throw new Error(error);
        }
    }
}
