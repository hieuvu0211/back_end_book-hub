import { Controller, Get, Param, Res } from '@nestjs/common';
import { BookService } from './book.service';
import { Response } from 'express';
@Controller('book')
export class BookController {
  constructor(private bookService: BookService) {}

  @Get('/getall')
  async getAllBooks() {
    return this.bookService.getAllBooks();
  }
  @Get('/image/:idBook')
  async getImageByName(@Param('idBook') idBook: string, @Res() res: Response) {
    return this.bookService.getImageBookByName(idBook, res);
  }

  @Get('/image/:idBook/:idChapter')
  async getChapterByName(
    @Param('idBook') idBook: string,
    @Param('idChapter') idChapter: string,
    @Res() res: Response,
  ) {
    return this.bookService.getApiNumberChapter(idBook, idChapter, res);
  }
  @Get('/getbyid/:idBook')
  async getBookById(@Param('idBook') idBook: string) {
    return this.bookService.getBookById(idBook);
  }

  @Get('/search/:name')
  async searchBookByName(@Param('name') name: string) {
    return this.bookService.SearchBookByName(name);
  }

  @Get('/getFavorite')
  async GetTopTenFavorite() {
    return this.bookService.GetTopTenBookByNumberOfLike();
  }
}
