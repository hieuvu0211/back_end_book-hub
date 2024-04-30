import { Controller, Get, Param } from '@nestjs/common';
import { FavoriteService } from './favorite.service';

@Controller('favorite')
export class FavoriteController {
    constructor(private favoriteService: FavoriteService) {}

    @Get('/getbyuserid/:id')
    async getFavoriteByUserId(@Param('id') id: string) {
        return this.favoriteService.getFavoriteByUserId(id);
    }
}
