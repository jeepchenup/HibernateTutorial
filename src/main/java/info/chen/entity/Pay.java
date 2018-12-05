package info.chen.entity;

public class Pay {
    private Integer monthPay;
    private Integer yearPay;
    private Integer vocationDays;

    public Pay() {
    }

    public Integer getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(Integer monthPay) {
        this.monthPay = monthPay;
    }

    public Integer getYearPay() {
        return yearPay;
    }

    public void setYearPay(Integer yearPay) {
        this.yearPay = yearPay;
    }

    public Integer getVocationDays() {
        return vocationDays;
    }

    public void setVocationDays(Integer vocationDays) {
        this.vocationDays = vocationDays;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "monthPay=" + monthPay +
                ", yearPay=" + yearPay +
                ", vocationDays=" + vocationDays +
                '}';
    }
}
