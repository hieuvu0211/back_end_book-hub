import { Controller, Get, Param } from '@nestjs/common';
import { BookService } from './book.service';

@Controller('book')
export class BookController {
    constructor(private bookService: BookService) {}

    @Get('/getall')
    async getAllBooks() {
        return this.bookService.getAllBooks();
    }

    @Get(':id')
    async getBookById(@Param('id') id: string) {
        return this.bookService.getBookById(id);
    }
}
