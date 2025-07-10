import bcrypt

# Danh sách tài khoản dạng (manv, tendangnhap, matkhau_goc, manhomquyen, trangthai, otp)
users = [
    (1, 'admin', 'admin123', 2, 1, None),
    (2, 'quzkhanh', 'khanh123.', 1, 1, None),
]

# Bắt đầu tạo script INSERT
print("INSERT INTO `taikhoan` (`manv`, `matkhau`, `manhomquyen`, `tendangnhap`, `trangthai`, `otp`) VALUES")

values = []
for user in users:
    manv, tendangnhap, plain_pwd, manhomquyen, trangthai, otp = user
    # Hash mật khẩu bằng bcrypt
    hashed_pwd = bcrypt.hashpw(plain_pwd.encode('utf-8'), bcrypt.gensalt(rounds=12)).decode('utf-8')
    # Format giá trị SQL
    otp_sql = f"'{otp}'" if otp else "NULL"
    values.append(f"({manv}, '{hashed_pwd}', {manhomquyen}, '{tendangnhap}', {trangthai}, {otp_sql})")

# In toàn bộ dòng INSERT
print(",\n".join(values) + ";")
