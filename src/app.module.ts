import { Module } from '@nestjs/common';
import { UserModule } from './user/user.module';
import { BookModule } from './book/book.module';
import { PrismaModule } from './prisma/prisma.module';


@Module({
  imports: [
    UserModule,
    BookModule,
    PrismaModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
