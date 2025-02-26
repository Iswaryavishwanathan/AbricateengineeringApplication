package com.example.abricateengineering.entity;
import java.time.LocalDateTime;

import com.example.abricateengineering.DAO.DataRecordDAO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// @Table(name = "LOG", schema = "dbo")
@Table(name = "dbo")


public class DataRecord extends DataRecordDAO{
    @Id
    @Column(name = "DateTime")
    private LocalDateTime dateTime;

    @Column(name = "FORMULA")
    private String formula;

    @Column(name = "T_01")
    private Float t01;

    @Column(name = "T_02")
    private Float t02;

    @Column(name = "T_03")
    private Float t03;

    @Column(name = "T_04")
    private Float t04;

    @Column(name = "T_05")
    private Float t05;

    @Column(name = "T_06")
    private Float t06;

    @Column(name = "T_07")
    private Float t07;

    @Column(name = "T_08")
    private Float t08;

    @Column(name = "T_09")
    private Float t09;

    @Column(name = "T_10")
    private Float t10;

    @Column(name = "T_11")
    private Float t11;

    @Column(name = "T_12")
    private Float t12;

    @Column(name = "T_13")
    private Float t13;

    @Column(name = "T_14")
    private Float t14;

    @Column(name = "T_15")
    private Float t15;

    @Column(name = "T_16")
    private Float t16;

    @Column(name = "T_17")
    private Float t17;

    @Column(name = "T_18")
    private Float t18;

    @Column(name = "T_19")
    private Float t19;

    @Column(name = "T_20")
    private Float t20;

    @Column(name = "A_01")
    private Float a01;

    @Column(name = "A_02")
    private Float a02;

    @Column(name = "A_03")
    private Float a03;

    @Column(name = "A_04")
    private Float a04;

    @Column(name = "A_05")
    private Float a05;

    @Column(name = "A_06")
    private Float a06;

    @Column(name = "A_07")
    private Float a07;

    @Column(name = "A_08")
    private Float a08;

    @Column(name = "A_09")
    private Float a09;

    @Column(name = "A_10")
    private Float a10;

    @Column(name = "A_11")
    private Float a11;

    @Column(name = "A_12")
    private Float a12;

    @Column(name = "A_13")
    private Float a13;

    @Column(name = "A_14")
    private Float a14;

    @Column(name = "A_15")
    private Float a15;

    @Column(name = "A_16")
    private Float a16;

    @Column(name = "A_17")
    private Float a17;

    @Column(name = "A_18")
    private Float a18;

    @Column(name = "A_19")
    private Float a19;

    @Column(name = "A_20")
    private Float a20;

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

    public Float getT01() {
        return t01;
    }

    public void setT01(Float t01) {
        this.t01 = t01;
    }

    public Float getT02() {
        return t02;
    }

    public void setT02(Float t02) {
        this.t02 = t02;
    }

    public Float getT03() {
        return t03;
    }

    public void setT03(Float t03) {
        this.t03 = t03;
    }

    public Float getT04() {
        return t04;
    }

    public void setT04(Float t04) {
        this.t04 = t04;
    }

    public Float getT05() {
        return t05;
    }

    public void setT05(Float t05) {
        this.t05 = t05;
    }

    public Float getT06() {
        return t06;
    }

    public void setT06(Float t06) {
        this.t06 = t06;
    }

    public Float getT07() {
        return t07;
    }

    public void setT07(Float t07) {
        this.t07 = t07;
    }

    public Float getT08() {
        return t08;
    }

    public void setT08(Float t08) {
        this.t08 = t08;
    }

    public Float getT09() {
        return t09;
    }

    public void setT09(Float t09) {
        this.t09 = t09;
    }

    public Float getT10() {
        return t10;
    }

    public void setT10(Float t10) {
        this.t10 = t10;
    }

    public Float getT11() {
        return t11;
    }

    public void setT11(Float t11) {
        this.t11 = t11;
    }

    public Float getT12() {
        return t12;
    }

    public void setT12(Float t12) {
        this.t12 = t12;
    }

    public Float getT13() {
        return t13;
    }

    public void setT13(Float t13) {
        this.t13 = t13;
    }

    public Float getT14() {
        return t14;
    }

    public void setT14(Float t14) {
        this.t14 = t14;
    }

    public Float getT15() {
        return t15;
    }

    public void setT15(Float t15) {
        this.t15 = t15;
    }

    public Float getT16() {
        return t16;
    }

    public void setT16(Float t16) {
        this.t16 = t16;
    }

    public Float getT17() {
        return t17;
    }

    public void setT17(Float t17) {
        this.t17 = t17;
    }

public Float getT18() {
    return t18;
}

public void setT18(Float t18) {
    this.t18 = t18;
}

public Float getT19() {
    return t19;
}

public void setT19(Float t19) {
    this.t19 = t19;
}

public Float getT20() {
    return t20;
}

public void setT20(Float t20) {
    this.t20 = t20;
}

public Float getA01() {
    return a01;
}

public void setA01(Float a01) {
    this.a01 = a01;
}

public Float getA02() {
    return a02;
}

public void setA02(Float a02) {
    this.a02 = a02;
}

public Float getA03() {
    return a03;
}

public void setA03(Float a03) {
    this.a03 = a03;
}

public Float getA04() {
    return a04;
}

public void setA04(Float a04) {
    this.a04 = a04;
}

public Float getA05() {
    return a05;
}

public void setA05(Float a05) {
    this.a05 = a05;
}

public Float getA06() {
    return a06;
}

public void setA06(Float a06) {
    this.a06 = a06;
}

public Float getA07() {
    return a07;
}

public void setA07(Float a07) {
    this.a07 = a07;
}

public Float getA08() {
    return a08;
}

public void setA08(Float a08) {
    this.a08 = a08;
}

public Float getA09() {
    return a09;
}

public void setA09(Float a09) {
    this.a09 = a09;
}

public Float getA10() {
    return a10;
}

public void setA10(Float a10) {
    this.a10 = a10;
}

public Float getA11() {
    return a11;
}

public void setA11(Float a11) {
    this.a11 = a11;
}

public Float getA12() {
    return a12;
}

public void setA12(Float a12) {
    this.a12 = a12;
}

public Float getA13() {
    return a13;
}

public void setA13(Float a13) {
    this.a13 = a13;
}

public Float getA14() {
    return a14;
}

public void setA14(Float a14) {
    this.a14 = a14;
}

public Float getA15() {
    return a15;
}

public void setA15(Float a15) {
    this.a15 = a15;
}

public Float getA16() {
    return a16;
}

public void setA16(Float a16) {
    this.a16 = a16;
}

public Float getA17() {
    return a17;
}

public void setA17(Float a17) {
    this.a17 = a17;
}

public Float getA18() {
    return a18;
}

public void setA18(Float a18) {
    this.a18 = a18;
}

public Float getA19() {
    return a19;
}

public void setA19(Float a19) {
    this.a19 = a19;
}

public Float getA20() {
    return a20;
}

public void setA20(Float a20) {
    this.a20 = a20;
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

// Getters and setters for other fields...
 // Now add the getTValue method
 public Float getTValue(int index) {
    switch (index) {
        case 0: return t01;
        case 1: return t02;
        case 2: return t03;
        case 3: return t04;
        case 4: return t05;
        case 5: return t06;
        case 6: return t07;
        case 7: return t08;
        case 8: return t09;
        case 9: return t10;
        case 10: return t11;
        case 11: return t12;
        case 12: return t13;
        case 13: return t14;
        case 14: return t15;
        case 15: return t16;
        case 16: return t17;
        case 17: return t18;
        case 18: return t19;
        case 19: return t20;
        default: return null; // or throw an IllegalArgumentException
    }
}
// Getter for material names (n01, n02, ..., n24)
public String getNValue(int index) {
    switch (index) {
        case 0: return n01;
        case 1: return n02;
        case 2: return n03;
        case 3: return n04;
        case 4: return n05;
        case 5: return n06;
        case 6: return n07;
        case 7: return n08;
        case 8: return n09;
        case 9: return n10;
        case 10: return n11;
        case 11: return n12;
        case 12: return n13;
        case 13: return n14;
        case 14: return n15;
        case 15: return n16;
        case 16: return n17;
        case 17: return n18;
        case 18: return n19;
        case 19: return n20;
        default: return null; // Index out of bounds
    }
}
public Float getAValue(int index) {
    switch (index) {
        case 0: return a01;
        case 1: return a02;
        case 2: return a03;
        case 3: return a04;
        case 4: return a05;
        case 5: return a06;
        case 6: return a07;
        case 7: return a08;
        case 8: return a09;
        case 9: return a10;
        case 10: return a11;
        case 11: return a12;
        case 12: return a13;
        case 13: return a14;
        case 14: return a15;
        case 15: return a16;
        case 16: return a17;
        case 17: return a18;
        case 18: return a19;
        case 19: return a20;
        default: return null; // Index out of bounds
    }
}
}


