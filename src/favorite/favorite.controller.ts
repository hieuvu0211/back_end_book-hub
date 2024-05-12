import { Body, Controller, Delete, Get, Param, Post } from '@nestjs/common';
import { FavoriteService } from './favorite.service';

@Controller('favorite')
export class FavoriteController {
    constructor(private favoriteService: FavoriteService) {}

    @Get('/getbyuserid/:id')
    async getFavoriteByUserId(@Param('id') id: string) {
        return this.favoriteService.getFavoriteByUserId(id);
    }

    @Post('/addfavorite')
    async AddFavorite(@Body() data) {
        return this.favoriteService.AddFavorite(data);
    }

    @Delete('/deletefavorite')
    async DeleteFavorite(@Body() data) {
        return this.favoriteService.DeleteFavorite(data);
    }
}
