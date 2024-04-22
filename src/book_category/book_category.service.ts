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
}
