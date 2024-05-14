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

    @Delete('/deletefavorite/:id')
    async DeleteFavorite(@Param('id') id: string) {
        return await this.favoriteService.deleteFavorite(id);
    }

    @Get('/checkfavorite/:id')
    async CheckFavorite(@Param('id') id: string) {
        return this.favoriteService.CheckFavoriteByUserIdAndBookId(id);
    }
}
