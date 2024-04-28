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
  @Get('/image/:name')
  async getImageByName(@Param('name') name: string, @Res() res: Response) {
    return this.bookService.getImageBookByName(name, res);
  }

  @Get('/image/:name/:chapter')
  async getChapterByName(
    @Param('name') name: string,
    @Param('chapter') chapter: string,
    @Res() res: Response,
  ) {
    return this.bookService.getApiNumberChapter(name, chapter, res);
  }
  // @Get('/image/:imagePath/:chapter/:page')
  // async getImage(
  //   @Param('imagePath') imagePath: String,
  //   @Param('chapter') chapter: String,
  //   @Param('page') page: String,
  //   @Res() res: Response,
  // ) {
  //   return this.bookService.getImage(imagePath, chapter, page, res);
  // }
  @Get('/getbyid/:id')
  async getBookById(@Param('id') id: string) {
    return this.bookService.getBookById(id);
  }
}
