import { Module } from '@nestjs/common';
import { UserModule } from './user/user.module';
import { BookModule } from './book/book.module';
import { BookCategoryModule } from './book_category/book_category.module';
import { PrismaModule } from './prisma/prisma.module';
import { FavoriteModule } from './favorite/favorite.module';
import { HistoryModule } from './history/history.module';


@Module({
  imports: [
    UserModule,
    BookModule,
    PrismaModule,
    BookCategoryModule,
    FavoriteModule,
    HistoryModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
