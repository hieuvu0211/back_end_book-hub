import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { join } from 'path';
import { ServeStaticModule } from '@nestjs/serve-static';
import { NestExpressApplication } from '@nestjs/platform-express';
async function bootstrap() {
  const app = await NestFactory.create<NestExpressApplication>(AppModule); // Use NestExpressApplication as the type for app

  app.enableCors();
  app.useStaticAssets(join(__dirname, '..', 'public'));
  await app.listen(8080);
}
bootstrap();
