package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;

public class AccountDAO {

    private DBContext dbContext;
    private List<Account> accounts = new ArrayList<>();

    public AccountDAO() {
        dbContext = new DBContext();
    }

    // Cập nhật thông tin người dùng
    public void updateUser(int userId, String username, String fullName, String phoneNumber, String email, String address) {
        String sql = "UPDATE Users SET UserName=?, FullName=?, PhoneNumber=?, Email=?, Address=? WHERE UserID=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, phoneNumber);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setInt(6, userId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Error while updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy người dùng theo ID
    public Account getUserById(int userId) {
        Account user = null;
        String sql = "SELECT * FROM Users WHERE UserID=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Account();
                user.setUserID(rs.getInt("UserID"));
                user.setFullName(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("PasswordHash"));
                user.setCreatedAt(rs.getTimestamp("CreatedAt"));
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving user: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    // Kiểm tra tài khoản bằng email
    public Account checkAccountByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("PasswordHash"),
                        rs.getTimestamp("CreatedAt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật OTP
    public boolean updateOtp(String email, String otp) {
        String query = "UPDATE Users SET code = ? WHERE Email = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, otp);
            ps.setString(2, email);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("OTP successfully updated.");
                return true;
            } else {
                System.out.println("No rows updated. Email might not exist in the database.");
            }
        } catch (Exception e) {
            System.out.println("Error updating OTP: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Lấy OTP theo email
    public String getOtpByEmail(String email) {
        String query = "SELECT code FROM Users WHERE Email = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("code");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving OTP: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Tạo tài khoản mới
    public boolean createAccount(Account account) {
        String query = "INSERT INTO Users (UserName, Pass, FullName, PhoneNumber, Email, Address, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFullName());
            ps.setString(4, account.getPhoneNumber());
            ps.setString(5, account.getEmail());
            ps.setString(6, account.getAddress());
            ps.setInt(7, account.getRole());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin tài khoản
    public boolean updateAccount(Account account) {
        String query = "UPDATE Users SET UserName = ?, Pass = ?, FullName = ?, PhoneNumber = ?, Email = ?, Address = ? WHERE UserId = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFullName());
            ps.setString(4, account.getPhoneNumber());
            ps.setString(5, account.getEmail());
            ps.setString(6, account.getAddress());
            ps.setInt(7, account.getUserID());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Danh sách tài khoản
    public List<Account> getAccountList() {
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Pass"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("AvtImg"),
                        rs.getInt("ShopID"),
                        rs.getInt("Role"),
                        rs.getBoolean("Status")
                );
                accountList.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }

    // Xóa tài khoản theo ID
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    public void savePasswordResetToken(String email, String token) {
        String queryUserID = "SELECT UserID FROM Users WHERE Email = ?";
        String insertToken = "INSERT INTO password_reset_tokens (UserID, token, expiration_time) VALUES (?, ?, DATEADD(HOUR, 24, GETDATE()))";

        try (Connection conn = dbContext.getConnection(); PreparedStatement psUserID = conn.prepareStatement(queryUserID); PreparedStatement psInsertToken = conn.prepareStatement(insertToken)) {

            // Lấy UserID từ email
            psUserID.setString(1, email);
            ResultSet rs = psUserID.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("UserID");

                // Lưu token vào database
                psInsertToken.setInt(1, userID);
                psInsertToken.setString(2, token);
                psInsertToken.executeUpdate();
            } else {
                System.out.println("Email không tồn tại trong hệ thống.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu token: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isValidToken(String token) {
        String query = "SELECT * FROM password_reset_tokens WHERE token = ? AND expiration_time > GETDATE()";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // Token hợp lệ nếu có kết quả trả về
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra token: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void updatePassword(String token, String newPassword) {
        String queryUserID = "SELECT UserID FROM password_reset_tokens WHERE token = ?";
        String updatePassword = "UPDATE Users SET Pass = ? WHERE UserID = ?";
        String deleteToken = "DELETE FROM password_reset_tokens WHERE token = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement psUserID = conn.prepareStatement(queryUserID); PreparedStatement psUpdatePassword = conn.prepareStatement(updatePassword); PreparedStatement psDeleteToken = conn.prepareStatement(deleteToken)) {

            // Lấy UserID từ token
            psUserID.setString(1, token);
            ResultSet rs = psUserID.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("UserID");

                // Cập nhật mật khẩu mới (mã hóa nếu cần)
                psUpdatePassword.setString(1, newPassword); // Cân nhắc mã hóa mật khẩu
                psUpdatePassword.setInt(2, userID);
                psUpdatePassword.executeUpdate();

                // Xóa token sau khi đã sử dụng
                psDeleteToken.setString(1, token);
                psDeleteToken.executeUpdate();
            } else {
                System.out.println("Token không hợp lệ.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật mật khẩu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // change password
    public boolean changePasswordByUserID(Account a) {
        boolean flag = false;
        String sql = "UPDATE Users SET Pass=? WHERE UserID=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getPassword());
            ps.setInt(2, a.getUserID());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean changeAvatarByUserID(Account a) {
        boolean flag = false;
        String sql = "UPDATE Users SET AvtImg=? WHERE UserID=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getAvtImg());
            ps.setInt(2, a.getUserID());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // Lấy tên đầy đủ của người dùng theo UserID
    public String getFullNameByUserId(int userId) {
        String fullName = null;
        String sql = "SELECT FullName FROM Users WHERE UserID = ?";

        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fullName = rs.getString("FullName");
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving full name: " + e.getMessage());
            e.printStackTrace();
        }
        return fullName;
    }

    // Get the avatar image URL of the user based on the UserID
    public String getAvatarByUserId(int userID) throws Exception {
        String avatarImg = null;
        String query = "SELECT AvtImg FROM [Users] WHERE UserID = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    avatarImg = rs.getString("AvtImg");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avatarImg;
    }

    public boolean createShopAccount(Account account) {
        String query = "INSERT INTO Users (UserName, Pass, FullName, PhoneNumber, Email, Address, ShopID, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFullName());
            ps.setString(4, account.getPhoneNumber());
            ps.setString(5, account.getEmail());
            ps.setString(6, account.getAddress());

            ps.setInt(7, account.getShopID());
            ps.setInt(8, account.getRole());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUserStatus(String userID, int status) {
        String sql = "UPDATE Users SET Status = ? WHERE UserID = ?";
        try (Connection connection = new DBContext().getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, status);
            statement.setString(2, userID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getShopIDByUserID(int userId) {
        String sql = "SELECT ShopID FROM Users WHERE UserID=?";
        Integer shopID = null;
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    shopID = rs.getInt("ShopID");
                } else {
                    System.out.println("No shop found for the given UserID.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving ShopID: " + e.getMessage());
            e.printStackTrace();
        }
        return shopID;
    }

    // getShopOwnerByShopID
    public Account getShopOwnerByShopID(int shopID) {
        Account user = null;
        String sql = "SELECT * FROM Users WHERE ShopID=?";
        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, shopID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Account();
                user.setUserID(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setFullName(rs.getString("FullName"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setAddress(rs.getString("Address"));
                user.setAvtImg(rs.getString("AvtImg"));
                user.setShopID(rs.getInt("ShopID"));
                user.setRole(rs.getInt("Role"));
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving user: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public String getEmailByUserID(int userID) {
        String email = null;
        String sql = "SELECT Email FROM Users WHERE UserID = ?";

        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString("Email");
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving email: " + e.getMessage());
            e.printStackTrace();
        }
        return email;
    }
    
        public String getEmailByShopID(int shopId) {
        String email = null;
        String sql = "SELECT Email FROM Users WHERE ShopID = ?";

        try (Connection con = dbContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, shopId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString("Email");
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving email: " + e.getMessage());
            e.printStackTrace();
        }
        return email;
    }


}
