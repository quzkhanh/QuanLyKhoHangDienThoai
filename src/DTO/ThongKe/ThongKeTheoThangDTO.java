/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.ThongKe;

/**
 *
 * @author robot
 */
public class ThongKeTheoThangDTO {
    private int thang;
    private int chiphi;
    private int doanhthu;
    private int loinhuan;
    
    public ThongKeTheoThangDTO(){
        
    }
    
    public ThongKeTheoThangDTO(int thang, int chiphi, int doanhthu, int loinhuan){
        this.thang = thang;
        this.chiphi = chiphi;
        this.doanhthu = doanhthu;
        this.loinhuan = loinhuan;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getChiphi() {
        return chiphi;
    }

    public void setChiphi(int chiphi) {
        this.chiphi = chiphi;
    }

    public int getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(int doanhthu) {
        this.doanhthu = doanhthu;
    }

    public int getLoinhuan() {
        return loinhuan;
    }

    public void setLoinhuan(int loinhuan) {
        this.loinhuan = loinhuan;
    }

    @Override
    public int hashCode() {
        int hash = 7; // Khởi tạo giá trị hash ban đầu với số nguyên tố 7
        hash = 59 * hash + this.thang;     // Nhân với 59 (số nguyên tố) rồi cộng thuộc tính thang
        hash = 59 * hash + this.chiphi;    // Tiếp tục nhân với 59 và cộng thuộc tính chiphi
        hash = 59 * hash + this.doanhthu;  // Tương tự với doanhthu
        hash = 59 * hash + this.loinhuan;  // Và cuối cùng là loinhuan
        return hash; // Trả về giá trị hash cuối cùng
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ThongKeTheoThangDTO other = (ThongKeTheoThangDTO) obj;
        if (this.thang != other.thang) {
            return false;
        }
        if (this.chiphi != other.chiphi) {
            return false;
        }
        if (this.doanhthu != other.doanhthu) {
            return false;
        }
        return this.loinhuan == other.loinhuan;
    }

    @Override
    public String toString() {
        return "ThongKeTheoThangDTO{" + "thang=" + thang + ", chiphi=" + chiphi + ", doanhthu=" + doanhthu + ", loinhuan=" + loinhuan + '}';
    }
    
}
