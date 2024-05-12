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

    @Delete('/deletefavorite/:userid/:bookid')
    async DeleteFavorite(@Param('userid') userid: string, @Param('userid') bookid: string) {
        return await this.favoriteService.deleteFavorite(userid, bookid);
    }

    @Post('/checkfavorite')
    async CheckFavorite(@Body() data) {
        return this.favoriteService.CheckFavoriteByUserIdAndBookId(data);
    }
}
