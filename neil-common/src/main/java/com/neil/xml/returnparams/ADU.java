package com.neil.xml.returnparams;

import java.io.IOException;

/*
 * @Classname ADU
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class ADU extends DU {
    private static String fileID = "@(#) $Id$";
    private String f_data;

    public String Data() {
        return this.f_data;
    }

    public void Data(String s) {
        this.f_data = s == null ? "" : s;
    }

    public int intData() {
        return Integer.parseInt(this.Data());
    }

    public void intData(int i) {
        this.Data((new Integer(i)).toString());
    }

    public int EncodedLength() {
        return 3 * this.intSize() + this.Data().length() + 1;
    }

    public int DUType() {
        return 0;
    }

    public ADU(DUInputStream s) throws IOException {
        try {
            this.DULabel(s.decodeInt());
            int len = s.decodeInt();
            this.Data(s.readString(len));
        }
        catch (IOException var3) {
            throw var3;
        }
    }

    public ADU(String s, int aLabel) {
        super(aLabel);
        this.Data(s);
    }

    public ADU(String s) {
        super(0);
        this.Data(s);
    }

    public ADU(int i) {
        super(0);
        this.intData(i);
    }

    public ADU(ADU anADU) {
        super(anADU);
        this.Data(anADU.Data());
    }

    protected void encodeDU(DUOutputStream s) throws IOException {
        try {
            s.encodeInt(0);
            s.encodeInt(this.DULabel());
            s.encodeInt(this.Data().length() + 1);
            s.encodeString(this.Data());
        }
        catch (IOException var3) {
            throw var3;
        }
    }

    public String toString() {
        String desc = "\tADU: [data-" + this.Data() + "]";
        return desc;
    }
}
