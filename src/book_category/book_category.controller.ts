import { Controller, Get, Param } from '@nestjs/common';
import { BookCategoryService } from './book_category.service';

@Controller('book-category')
export class BookCategoryController {
    constructor(private bookcate: BookCategoryService) {}

    

    @Get(':id')
    async getCategoryByBookId(@Param('id') id: string) {
        return this.bookcate.findCategoryByBookId(id);
    }
}
