import { Injectable } from '@nestjs/common';
import { OAuth2Client } from 'google-auth-library';

@Injectable()
export class AuthService {
  private client: OAuth2Client;

  constructor() {
    this.client = new OAuth2Client('460709302924-58gsfjd2o6tab6vnods8b27mm1i3dbs7.apps.googleusercontent.com'); // YOUR_GOOGLE_CLIENT_ID
  }

  async verifyIdToken(idToken: string) {
    const ticket = await this.client.verifyIdToken({
      idToken,
      audience: '460709302924-58gsfjd2o6tab6vnods8b27mm1i3dbs7.apps.googleusercontent.com', // YOUR_GOOGLE_CLIENT_ID
    });
    const payload = ticket.getPayload();
    return payload;
  }

  async login(idToken: string) {
    const payload = await this.verifyIdToken(idToken);
    if (payload) {
      console.log(payload);
      // Xử lý logic xác thực người dùng (ví dụ: tìm kiếm hoặc tạo người dùng trong cơ sở dữ liệu)
      const { sub, email, name, picture } = payload;
      const user = {
        googleId: sub,
        email : email,
        name : name,
        picture : picture,
      };
      // Giả sử bạn có một hàm `findOrCreateUser` để tìm kiếm hoặc tạo người dùng
      return this.findOrCreateUser(user);
    }
    throw new Error('Invalid token');
  }

  private async findOrCreateUser(user) {
    
    // Implement logic tìm kiếm hoặc tạo người dùng trong cơ sở dữ liệu của bạn
    return user; // Giả sử trả về người dùng đã được xác thực hoặc mới tạo
  }
}
