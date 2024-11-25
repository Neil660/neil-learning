package com.neil.xml.returnparams;

import java.io.IOException;
import java.io.OutputStream;

/*
 * @Classname DU
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public abstract class DU {
    private static String fileID = "@(#) $Id: DU.java,v 1.2 2000/02/10 19:26:59 jdeviss Exp $";
    public static final int T_ADU = 0;
    public static final int T_CDU = 1;
    public static final int ASCII = 0;
    public static final int BINARY = 1;
    public static int protocol = 1;
    private int f_label;

    public abstract int DUType();

    public abstract int EncodedLength();

    protected abstract void encodeDU(DUOutputStream var1) throws IOException;

    public void DULabel(int aLabel) {
        this.f_label = aLabel;
    }

    public int DULabel() {
        return this.f_label;
    }

    protected DU(int aLabel) {
        this.f_label = aLabel;
    }

    protected DU(DU aDU) {
        this.f_label = aDU.DULabel();
    }

    protected DU() {
        this.f_label = 0;
    }

    protected int intSize() {
        return protocol == 1 ? 4 : 5;
    }

    public final void encode(OutputStream s) throws IOException {
        try {
            DUOutputStream os = new DUOutputStream(s);
            os.encodeInt(this.EncodedLength());
            this.encodeDU(os);
        }
        catch (IOException var3) {
            throw var3;
        }
    }
}
