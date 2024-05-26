import { Body, Controller, Get, Param, Put } from '@nestjs/common';
import { HistoryService } from './history.service';
import { HistoryDTO } from 'src/dto/history.dto';

@Controller('history')
export class HistoryController {
    constructor(private historyService: HistoryService) {}

    @Get('/getbyuserid/:idUser')
    async getHistoriesByUserId(@Param('idUser') userId: string) {
        return await this.historyService.getHistoriesByUserId(userId);
    }

    @Get('/gettoptenbyuserid/:idUser')
    async getTopTenHistoriesByUserId(@Param('idUser') userId: string) {
        return await this.historyService.getTopTenHistoriesByUserId(userId);
    }
    @Get('/check/:listId')
    async checkHistory(@Param('listId') listId: string) {
        return await this.historyService.checkHistory(listId);
    }

    @Put('/updateHistory')
    async updateHistory(@Body() data: HistoryDTO) {
        return await this.historyService.UpdateHistory(data);
    }
}
