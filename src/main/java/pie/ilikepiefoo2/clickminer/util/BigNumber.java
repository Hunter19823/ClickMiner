package pie.ilikepiefoo2.clickminer.util;

import net.minecraft.nbt.CompoundNBT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO Documentation
public class BigNumber {
    private static final Logger LOGGER = LogManager.getLogger();
    private double value;
    private int exponent;

    public BigNumber(double value, int exponent)
    {
        this.value = value;
        this.exponent = exponent;
        shrink();
    }
    public BigNumber(double value)
    {
        this.value = value;
        this.exponent = 0;
        shrink();
    }
    public BigNumber(BigNumber copy)
    {
        this.value = copy.value;
        this.exponent = copy.exponent;
        shrink();
    }
    public BigNumber add(BigNumber that)
    {
        int diff = this.exponent - that.exponent;
        if (diff < -1) {
            this.value = that.value;
            this.exponent = that.exponent;
        } else {
            if(diff <= 1) {
                BigNumber max = Max(this,that);
                BigNumber min = Min(this,that);
                this.value = max.value + min.getValue() / Math.pow(1000, Math.abs(diff));
                this.exponent = max.exponent;
            }
            /*
            if(that.exponent < 0 == this.exponent < 0){
                if (diff <= 1) {
                    this.value += that.value / Math.pow(1000, diff);
                }
            }else{
                this.value = Math.max(this.value, that.value) + Math.min(this.value, that.value)/1000;
                this.exponent = Math.max(this.exponent,that.exponent);
            }
             */
        }
        shrink();
        return this;
    }
    public BigNumber add(double that)
    {
        return this.add(new BigNumber(that));
    }
    public BigNumber plus(BigNumber that)
    {
        return new BigNumber(this).add(that);
    }

    public boolean canSubtract(BigNumber that)
    {
        int diff = this.exponent - that.exponent;

        if(diff<0){
            return false;
        }else if(diff <= 0){
            return this.value < this.value/(Math.pow(1000,diff));
        }
        return true;
    }

    public BigNumber subtract(BigNumber that)
    {
        int diff = this.exponent - that.exponent;
        if(diff <= 1){
            double temp = that.value /(Math.pow(1000,diff));
            if (this.value < temp)
                return null;
            this.value -= temp;
        }
        shrink();
        return this;
    }
    public BigNumber subtract(double that)
    {
        return this.subtract(new BigNumber(that));
    }
    public BigNumber minus(BigNumber that)
    {
        return new BigNumber(this).subtract(that);
    }

    public BigNumber multiply(BigNumber that)
    {
        this.exponent = this.exponent + that.exponent;
        this.value *= that.value;
        shrink();
        return this;
    }
    public BigNumber multiply(double that)
    {
        this.multiply(new BigNumber(that,0));
        shrink();
        return this;
    }
    public BigNumber times(BigNumber that)
    {
        return new BigNumber(this).multiply(that);
    }

    public BigNumber divide(BigNumber that)
    {
        if(that.value == 0)
            return null;
        this.exponent = this.exponent-that.exponent;
        this.value = this.value/that.value;
        shrink();
        return this;
    }
    public BigNumber divide(double that)
    {
        this.divide(new BigNumber(that,0));
        shrink();
        return this;
    }
    public BigNumber over(BigNumber that)
    {
        return new BigNumber(this).over(that);
    }

    public void clear()
    {
        this.value = 0.0;
        this.exponent = 0;
    }

    private void shrink()
    {
        if(this.value == 0.0){
            this.exponent = 0;
            return;
        }
        while(this.value >= 1000 || (this.value < 1 && this.value != 0)) {
            if (this.value >= 1000) {
                this.value /= 1000.0;
                this.exponent += 1;
            }
            if (this.value < 1) {
                this.value *= 1000.0;
                this.exponent -= 1;
            }
        }
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public int getExponent()
    {
        return exponent;
    }

    public void setExponent(int exponent)
    {
        this.exponent = exponent;
    }

    public boolean equals(BigNumber that)
    {
        return this.value == (that.value) && this.exponent == (that.exponent);
    }

    public boolean equals(double that)
    {
        return this.equals(new BigNumber(that));
    }

    @Override
    public String toString()
    {
        //System.out.println(String.format("%3.3f * 1000^%d",this.value,this.exponent));

        if(exponent <= 2 && exponent > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = this.exponent; i >= 0; i--) {
                if (i == 0) {
                    builder.insert(0, String.format("%,6.0f",this.value*1000));
                }
                if (i > 1) {
                    builder.append(",000");
                }
            }
            //return this.trueValue + "* 1000^"+this.exponent;
            return builder.toString();
        }else if(this.exponent == 0){
            return String.format("%3.3f",this.value);
        } else if(exponent == -1) {
            return String.format("%1.3f",this.value/1000);
            //return this.trueValue + "* 1000^"+this.exponent;
        } else{
            Suffix suffix = Suffix.get(this.exponent);
            if(suffix == null){
                return (Math.round(this.value)+"."+Math.round(this.value *1000)%1000) + " * 10^"+(this.exponent)*3;
            }else{
                if(this.exponent<0)
                    return (new StringBuilder().append(Math.round(this.value *1000000)%1000000).reverse().toString()) + " "+ suffix.prefix;
                return (Math.round(this.value)+"."+Math.round(this.value *1000)%1000) + " "+ suffix.prefix;
            }
        }
    }

    public CompoundNBT toNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("value",this.value);
        nbt.putInt("exponent",this.exponent);
        return nbt;
    }
    public static BigNumber fromNBT(CompoundNBT compoundNBT)
    {
        return new BigNumber(compoundNBT.getDouble("value"),compoundNBT.getInt("exponent"));
    }

    public static BigNumber Max(BigNumber n1, BigNumber n2)
    {
        if(n1.exponent == n2.exponent)
            return n1.getValue()>n2.getValue() ? n1 : n2;
        return n1.getExponent()>n2.getExponent() ? n1 : n2;
    }
    public static BigNumber Min(BigNumber n1, BigNumber n2)
    {
        if(n1.exponent == n2.exponent)
            return n1.getValue()>n2.getValue() ? n2 : n1;
        return n1.getExponent()>n2.getExponent() ? n2 : n1;
    }
    // 123.456 * 1000 ^ 1 = 123,456
    // 123.456 * 1000 ^ -1 = 0.123456

    public static void main(String[] args)
    {
        for (int i = 0; i < 25; i++) {
            BigNumber n1 = new BigNumber(Math.random()*1000, (i-12));
            BigNumber n2;
            for(int j=-2;j<2;j++) {
                n2 = new BigNumber(Math.random() * 1000, (i - 12)-j);

                LOGGER.info(n1 + " + " + n2 + " = " + n1.plus(n2));
            }
            /*
            n2 = new BigNumber(Math.random()*1000, (i-12));

            System.out.println(n1+" + "+n2+" = "+n1.subtract(n2));

            n2 = new BigNumber(Math.random()*1000, (i-12));

            System.out.println(n1+" * "+n2+" = "+n1.multiply(n2));

            n2 = new BigNumber(Math.random()*1000, (i-12));

            System.out.println(n1+" / "+n2+" = "+n1.divide(n2));

             */
        }
    }


    public enum Suffix
    {
        // TODO Support More Suffixes
        // TODO Support Negative Suffixes
        THOUSAND(2,"Thousand"),
        MILLION(3,"Million"),
        BILLION(4,"Billion"),
        TRILLION(5,"Trillion"),
        QUADRILLION(6,"Quadrillion"),
        QUINTILLION(7,"Quintillion"),
        SEXTILLION(8,"Sextillion"),
        SEPTILLION(9,"Septillion"),
        OCTILLION(10,"Octillion"),
        NONILLION(11,"Nonillion"),
        DECILLION(12,"Decillion"),
        UNDECILLION(13,"Undecillion"),
        DUODECILLION(14,"Duodecillion"),
        TREDECILLION(15,"Tredecillion"),
        QUATTUORDECILLION(16,"Quattuordecillion"),
        QUINDECILLION(17,"Quindecillion"),
        SEXDECILLION(18,"Sexdecillion"),
        SEPTENDECILLION(19,"Septendecillion"),
        OCTODECILLION(20,"Octodecillion"),
        NOVEMDECILLION(21,"Novemdecillion"),
        VIGINTILLION(22,"Vigintillion");


        public int thousands;
        public String prefix;
        Suffix(int thousands, String prefix){
            this.thousands = thousands;
            this.prefix = prefix;
        }
        public static Suffix get(int thousands){
            thousands -= 1;
            if (thousands >= 0 && thousands < Suffix.values().length) {
                return Suffix.values()[thousands];
            }else{
                return null;
            }
        }
    }
}
