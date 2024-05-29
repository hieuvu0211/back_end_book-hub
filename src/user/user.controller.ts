import {
  Body,
  Controller,
  Get,
  Param,
  ParseIntPipe,
  Post,
  UploadedFile,
  UseInterceptors,
} from '@nestjs/common';
import { UserService } from './user.service';
import { updatePasswordDto } from 'src/dto/changePassword.dto';
import { FileInterceptor } from '@nestjs/platform-express';
import { diskStorage } from 'multer';
import { extname, join } from 'path';
import * as fs from 'fs';
@Controller('user')
export class UserController {
  constructor(private userService: UserService) {}
  @Get('/getuserbyid/:id')
  getalluser(@Param('id') id : string) {
    return this.userService.findUserByid(id);
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

  @Post('ssoregister')
  SsoRegisterController(@Body() data) {
    return this.userService.RegisterWithSSO(data);
  }

  @Post('upload/:idUser')
  @UseInterceptors(FileInterceptor('image', {
    storage: diskStorage({
      destination: './public/userUpload',
      filename: async (req, file, cb) => {
        const iduser = req.params.idUser;
        const uniqueSuffix = iduser;
        const ext = extname(file.originalname);
        const filename = `${uniqueSuffix}${ext}`;

        const filePath = join(__dirname, '..', '..', 'public/userUpload');
        try {
          // Lấy danh sách các file trong thư mục
          const files = fs.readdir(filePath, (err, files) => {
            if (err) {
              console.error('Error accessing files:', err);
              return;
            }

            // Tìm và xóa các file có tên trùng (không phân biệt phần mở rộng)
            for (const file of files) {
              if (file.startsWith(iduser) && file !== filename) {
                fs.unlink(join(filePath, file.toString()), (err) => {
                  if (err) {
                    console.error('Error deleting file:', err);
                  }
                });
              }
            }
          });
        } catch (error) {
          console.error('Error accessing or deleting files:', error);
        }
        cb(null, filename);
      },
    })
  }))
  uploadFile(@Param('idUser') id: string, @UploadedFile() file: Express.Multer.File) {
    console.log("file = ", file, "id = ", id);
    return this.userService.handleFileUpload(id, file.filename);
  }
}
