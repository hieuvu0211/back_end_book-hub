import { Controller } from '@nestjs/common';
import { HistoryService } from './history.service';

@Controller('history')
export class HistoryController {
    constructor(historyService: HistoryService) {}
}
