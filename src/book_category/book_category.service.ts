import { Injectable } from '@nestjs/common';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class BookCategoryService {
    constructor(private prisma: PrismaService) {}
    
    async findCategoryByBookId(id: string) {
        try {
            const res = await this.prisma.book_category.findMany({
                where: {
                    book_id: Number(id)
                }
            });
            if(res.length > 0 || res) {
                const ans = res.map((item) => {
                    return {
                        book_id: item.book_id
                    }
                });
                return ans;
            }
            else return {status: 400, message: 'No category found'};
        }
        catch (error) {
            throw new Error(error);
        }
    }
    async findBookByListCategoryId(list: string) {
        try {
            const data = list.split(',').map(item => parseInt(item));
            const res = await this.prisma.book_category.findMany({
                where: {
                    category_id: {
                        in: data
                    }
                }
            });
            if(res.length > 0 || res) {
                return res;
            }
            else return {status: 400, message: 'No book found'};
        }
        catch (error) {
            throw new Error(error);
        }
    }
}
