import { Body, Controller, Get, Param, Put } from '@nestjs/common';
import { HistoryService } from './history.service';
import { HistoryDTO } from 'src/dto/history.dto';

@Controller('history')
export class HistoryController {
    constructor(private historyService: HistoryService) {}

    @Get('/getbyuserid/:id')
    async getHistoriesByUserId(@Param('id') userId: string) {
        return await this.historyService.getHistoriesByUserId(userId);
    }

    @Get('/gettoptenbyuserid/:id')
    async getTopTenHistoriesByUserId(@Param('id') userId: string) {
        return await this.historyService.getTopTenHistoriesByUserId(userId);
    }

    @Put('/updateHistory')
    async updateHistory(@Body() data: HistoryDTO) {
        return await this.historyService.UpdateHistory(data);
    }
}
