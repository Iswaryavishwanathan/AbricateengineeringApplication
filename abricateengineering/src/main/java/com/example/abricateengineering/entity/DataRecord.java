package com.example.abricateengineering.entity;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LOG", schema = "dbo")

public class DataRecord {
    @Id
    @Column(name = "DateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "FORMULA")
    private String formula;

    @Column(name = "T_01")
    private Integer t01;

    @Column(name = "T_02")
    private Integer t02;

    @Column(name = "T_03")
    private Integer t03;

    @Column(name = "T_04")
    private Integer t04;

    @Column(name = "T_05")
    private Integer t05;

    @Column(name = "T_06")
    private Integer t06;

    @Column(name = "T_07")
    private Integer t07;

    @Column(name = "T_08")
    private Integer t08;

    @Column(name = "T_09")
    private Integer t09;

    @Column(name = "T_10")
    private Integer t10;

    @Column(name = "T_11")
    private Integer t11;

    @Column(name = "T_12")
    private Integer t12;

    @Column(name = "T_13")
    private Integer t13;

    @Column(name = "T_14")
    private Integer t14;

    @Column(name = "T_15")
    private Integer t15;

    @Column(name = "T_16")
    private Integer t16;

    @Column(name = "T_17")
    private Integer t17;

    @Column(name = "T_18")
    private Integer t18;

    @Column(name = "T_19")
    private Integer t19;

    @Column(name = "T_20")
    private Integer t20;

    @Column(name = "T_21")
    private Integer t21;

    @Column(name = "T_22")
    private Integer t22;

    @Column(name = "T_23")
    private Integer t23;

    @Column(name = "T_24")
    private Integer t24;

    @Column(name = "A_01")
    private Integer a01;

    @Column(name = "A_02")
    private Integer a02;

    @Column(name = "A_03")
    private Integer a03;

    @Column(name = "A_04")
    private Integer a04;

    @Column(name = "A_05")
    private Integer a05;

    @Column(name = "A_06")
    private Integer a06;

    @Column(name = "A_07")
    private Integer a07;

    @Column(name = "A_08")
    private Integer a08;

    @Column(name = "A_09")
    private Integer a09;

    @Column(name = "A_10")
    private Integer a10;

    @Column(name = "A_11")
    private Integer a11;

    @Column(name = "A_12")
    private Integer a12;

    @Column(name = "A_13")
    private Integer a13;

    @Column(name = "A_14")
    private Integer a14;

    @Column(name = "A_15")
    private Integer a15;

    @Column(name = "A_16")
    private Integer a16;

    @Column(name = "A_17")
    private Integer a17;

    @Column(name = "A_18")
    private Integer a18;

    @Column(name = "A_19")
    private Integer a19;

    @Column(name = "A_20")
    private Integer a20;

    @Column(name = "A_21")
    private Integer a21;

    @Column(name = "A_22")
    private Integer a22;

    @Column(name = "A_23")
    private Integer a23;

    @Column(name = "A_24")
    private Integer a24;

    @Column(name = "N_01")
    private String n01;

    @Column(name = "N_02")
    private String n02;

    @Column(name = "N_03")
    private String n03;

    @Column(name = "N_04")
    private String n04;

    @Column(name = "N_05")
    private String n05;

    @Column(name = "N_06")
    private String n06;

    @Column(name = "N_07")
    private String n07;

    @Column(name = "N_08")
    private String n08;

    @Column(name = "N_09")
    private String n09;

    @Column(name = "N_10")
    private String n10;

    @Column(name = "N_11")
    private String n11;

    @Column(name = "N_12")
    private String n12;

    @Column(name = "N_13")
    private String n13;

    @Column(name = "N_14")
    private String n14;

    @Column(name = "N_15")
    private String n15;

    @Column(name = "N_16")
    private String n16;

    @Column(name = "N_17")
    private String n17;

    @Column(name = "N_18")
    private String n18;

    @Column(name = "N_19")
    private String n19;

    @Column(name = "N_20")
    private String n20;

    @Column(name = "N_21")
    private String n21;

    @Column(name = "N_22")
    private String n22;

    @Column(name = "N_23")
    private String n23;

    @Column(name = "N_24")
    private String n24;

    
    


     // Getters and setters...
     public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getT01() {
        return t01;
    }

    public void setT01(Integer t01) {
        this.t01 = t01;
    }

    public Integer getT02() {
        return t02;
    }

    public void setT02(Integer t02) {
        this.t02 = t02;
    }

    public Integer getT03() {
        return t03;
    }

    public void setT03(Integer t03) {
        this.t03 = t03;
    }

    public Integer getT04() {
        return t04;
    }

    public void setT04(Integer t04) {
        this.t04 = t04;
    }

    public Integer getT05() {
        return t05;
    }

    public void setT05(Integer t05) {
        this.t05 = t05;
    }

    public Integer getT06() {
        return t06;
    }

    public void setT06(Integer t06) {
        this.t06 = t06;
    }

    public Integer getT07() {
        return t07;
    }

    public void setT07(Integer t07) {
        this.t07 = t07;
    }

    public Integer getT08() {
        return t08;
    }

    public void setT08(Integer t08) {
        this.t08 = t08;
    }

    public Integer getT09() {
        return t09;
    }

    public void setT09(Integer t09) {
        this.t09 = t09;
    }

    public Integer getT10() {
        return t10;
    }

    public void setT10(Integer t10) {
        this.t10 = t10;
    }

    public Integer getT11() {
        return t11;
    }

    public void setT11(Integer t11) {
        this.t11 = t11;
    }

    public Integer getT12() {
        return t12;
    }

    public void setT12(Integer t12) {
        this.t12 = t12;
    }

    public Integer getT13() {
        return t13;
    }

    public void setT13(Integer t13) {
        this.t13 = t13;
    }

    public Integer getT14() {
        return t14;
    }

    public void setT14(Integer t14) {
        this.t14 = t14;
    }

    public Integer getT15() {
        return t15;
    }

    public void setT15(Integer t15) {
        this.t15 = t15;
    }

    public Integer getT16() {
        return t16;
    }

    public void setT16(Integer t16) {
        this.t16 = t16;
    }

    public Integer getT17() {
        return t17;
    }

    public void setT17(Integer t17) {
        this.t17 = t17;
    }
// Continued from previous response...

public Integer getT18() {
    return t18;
}

public void setT18(Integer t18) {
    this.t18 = t18;
}

public Integer getT19() {
    return t19;
}

public void setT19(Integer t19) {
    this.t19 = t19;
}

public Integer getT20() {
    return t20;
}

public void setT20(Integer t20) {
    this.t20 = t20;
}

public Integer getT21() {
    return t21;
}

public void setT21(Integer t21) {
    this.t21 = t21;
}

public Integer getT22() {
    return t22;
}

public void setT22(Integer t22) {
    this.t22 = t22;
}

public Integer getT23() {
    return t23;
}

public void setT23(Integer t23) {
    this.t23 = t23;
}

public Integer getT24() {
    return t24;
}

public void setT24(Integer t24) {
    this.t24 = t24;
}

public Integer getA01() {
    return a01;
}

public void setA01(Integer a01) {
    this.a01 = a01;
}

public Integer getA02() {
    return a02;
}

public void setA02(Integer a02) {
    this.a02 = a02;
}

public Integer getA03() {
    return a03;
}

public void setA03(Integer a03) {
    this.a03 = a03;
}

public Integer getA04() {
    return a04;
}

public void setA04(Integer a04) {
    this.a04 = a04;
}

public Integer getA05() {
    return a05;
}

public void setA05(Integer a05) {
    this.a05 = a05;
}

public Integer getA06() {
    return a06;
}

public void setA06(Integer a06) {
    this.a06 = a06;
}

public Integer getA07() {
    return a07;
}

public void setA07(Integer a07) {
    this.a07 = a07;
}

public Integer getA08() {
    return a08;
}

public void setA08(Integer a08) {
    this.a08 = a08;
}

public Integer getA09() {
    return a09;
}

public void setA09(Integer a09) {
    this.a09 = a09;
}

public Integer getA10() {
    return a10;
}

public void setA10(Integer a10) {
    this.a10 = a10;
}

public Integer getA11() {
    return a11;
}

public void setA11(Integer a11) {
    this.a11 = a11;
}

public Integer getA12() {
    return a12;
}

public void setA12(Integer a12) {
    this.a12 = a12;
}

public Integer getA13() {
    return a13;
}

public void setA13(Integer a13) {
    this.a13 = a13;
}

public Integer getA14() {
    return a14;
}

public void setA14(Integer a14) {
    this.a14 = a14;
}

public Integer getA15() {
    return a15;
}

public void setA15(Integer a15) {
    this.a15 = a15;
}

public Integer getA16() {
    return a16;
}

public void setA16(Integer a16) {
    this.a16 = a16;
}

public Integer getA17() {
    return a17;
}

public void setA17(Integer a17) {
    this.a17 = a17;
}

public Integer getA18() {
    return a18;
}

public void setA18(Integer a18) {
    this.a18 = a18;
}

public Integer getA19() {
    return a19;
}

public void setA19(Integer a19) {
    this.a19 = a19;
}

public Integer getA20() {
    return a20;
}

public void setA20(Integer a20) {
    this.a20 = a20;
}

public Integer getA21() {
    return a21;
}

public void setA21(Integer a21) {
    this.a21 = a21;
}

public Integer getA22() {
    return a22;
}

public void setA22(Integer a22) {
    this.a22 = a22;
}

public Integer getA23() {
    return a23;
}

public void setA23(Integer a23) {
    this.a23 = a23;
}

public Integer getA24() {
    return a24;
}

public void setA24(Integer a24) {
    this.a24 = a24;
}

public String getN01() {
    return n01;
}

public void setN01(String n01) {
    this.n01 = n01;
}

public String getN02() {
    return n02;
}

public void setN02(String n02) {
    this.n02 = n02;
}

public String getN03() {
    return n03;
}

public void setN03(String n03) {
    this.n03 = n03;
}

public String getN04() {
    return n04;
}

public void setN04(String n04) {
    this.n04 = n04;
}

public String getN05() {
    return n05;
}

public void setN05(String n05) {
    this.n05 = n05;
}

public String getN06() {
    return n06;
}

public void setN06(String n06) {
    this.n06 = n06;
}

public String getN07() {
    return n07;
}

public void setN07(String n07) {
    this.n07 = n07;
}

public String getN08() {
    return n08;
}

public void setN08(String n08) {
    this.n08 = n08;
}

public String getN09() {
    return n09;
}

public void setN09(String n09) {
    this.n09 = n09;
}

public String getN10() {
    return n10;
}

public void setN10(String n10) {
    this.n10 = n10;
}

public String getN11() {
    return n11;
}

public void setN11(String n11) {
    this.n11 = n11;
}

public String getN12() {
    return n12;
}

public void setN12(String n12) {
    this.n12 = n12;
}

public String getN13() {
    return n13;
}

public void setN13(String n13) {
    this.n13 = n13;
}

public String getN14() {
    return n14;
}

public void setN14(String n14) {
    this.n14 = n14;
}

public String getN15() {
    return n15;
}

public void setN15(String n15) {
    this.n15 = n15;
}

public String getN16() {
    return n16;
}

public void setN16(String n16) {
    this.n16 = n16;
}

public String getN17() {
    return n17;
}

public void setN17(String n17) {
    this.n17 = n17;
}

public String getN18() {
    return n18;
}

public void setN18(String n18) {
    this.n18 = n18;
}

public String getN19() {
    return n19;
}

public void setN19(String n19) {
    this.n19 = n19;
}

public String getN20() {
    return n20;
}

public void setN20(String n20) {
    this.n20 = n20;
}

public String getN21() {
    return n21;
}

public void setN21(String n21) {
    this.n21 = n21;
}

public String getN22() {
    return n22;
}

public void setN22(String n22) {
    this.n22 = n22;
}

public String getN23() {
    return n23;
}

public void setN23(String n23) {
    this.n23 = n23;
}

public String getN24() {
    return n24;
}

public void setN24(String n24) {
    this.n24 = n24;
}

// Getters and setters for other fields...
}


