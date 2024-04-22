import {
  Body,
  Controller,
  Get,
  HttpCode,
  HttpStatus,
  Post,
} from '@nestjs/common';
import { UserService } from './user.service';
import { updatePasswordDto } from 'src/dto/changePassword.dto';
@Controller('user')
export class UserController {
  constructor(private userService: UserService) {}
  @Get('/getall')
  getalluser() {
    return this.userService.findAll();
  }
  
  @Post('/login')
  LoginController(@Body() data) {
    return this.userService.Login(data);
  }

  @Post('register')
  RegisterController(@Body() data) {
    return this.userService.Register(data);
  }

  @Post('updatepassword')
  UpdatePasswordController(@Body() data: updatePasswordDto) {
    return this.userService.UpdatePassword(data);
  }
}
