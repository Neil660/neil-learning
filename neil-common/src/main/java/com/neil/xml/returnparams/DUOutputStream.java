package com.neil.xml.returnparams;

import java.io.IOException;
import java.io.OutputStream;

/*
 * @Classname DUOutputStream
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class DUOutputStream {
    private static String fileID = "@(#) $Id: DUOutputStream.java,v 1.2 2000/02/10 19:27:00 jdeviss Exp $";
    private OutputStream os;

    public DUOutputStream(OutputStream s) {
        this.os = s;
    }

    protected void encodeInt(int i) throws IOException {
        try {
            if (DU.protocol == 0) {
                String str;
                for(str = (new Integer(i)).toString(); str.length() < 5; str = '0' + str) {
                }

                this.encodeString(str);
            } else {
                this.os.write(i >>> 24 & 255);
                this.os.write(i >>> 16 & 255);
                this.os.write(i >>> 8 & 255);
                this.os.write(i >>> 0 & 255);
            }

        } catch (IOException var3) {
            throw var3;
        }
    }

    protected void encodeString(String s) throws IOException {
        try {
            byte[] bytes = s.getBytes();
            this.os.write(bytes);
            this.os.write(0);
        } catch (IOException var3) {
            throw var3;
        }
    }
}
