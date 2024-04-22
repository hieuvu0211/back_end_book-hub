import { Module } from '@nestjs/common';
import { BookCategoryService } from './book_category.service';
import { BookCategoryController } from './book_category.controller';

@Module({
  providers: [BookCategoryService],
  controllers: [BookCategoryController]
})
export class BookCategoryModule {}
