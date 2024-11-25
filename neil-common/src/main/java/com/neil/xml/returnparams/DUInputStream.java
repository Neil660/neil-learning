package com.neil.xml.returnparams;

import java.io.IOException;
import java.io.InputStream;

/*
 * @Classname DUInputStream
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class DUInputStream {
    private static String fileID = "@(#) $Id: DUInputStream.java,v 1.2 2000/02/10 19:27:00 jdeviss Exp $";
    private InputStream is;
    private boolean fresh;

    public DUInputStream(InputStream s) {
        this.is = s;
        this.fresh = true;
    }

    protected String readString(int len) throws IOException {
        this.fresh = false;
        int offset = 0;
        int ntoread = len - 1;

        byte[] bytes;
        int nread;
        for (bytes = new byte[len - 1]; ntoread > 0; offset += nread) {
            nread = this.is.read(bytes, offset, ntoread);
            if (nread < 0) {
                break;
            }

            ntoread -= nread;
        }

        String s = new String(bytes);
        this.is.read();
        return s;
    }

    protected int decodeInt() throws IOException {
        this.fresh = false;
        int i;
        if (DU.protocol == 0) {
            try {
                i = Integer.parseInt(this.readString(5));
            }
            catch (NumberFormatException var6) {
                i = -1;
            }
        }
        else {
            int ch1 = this.is.read();
            if (ch1 < 0) {
                return -1;
            }

            int ch2 = this.is.read();
            if (ch2 < 0) {
                return -1;
            }

            int ch3 = this.is.read();
            if (ch3 < 0) {
                return -1;
            }

            int ch4 = this.is.read();
            if (ch4 < 0) {
                return -1;
            }

            i = (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
        }

        return i;
    }

    public DU decode() throws IOException {
        boolean wasfresh = this.fresh;
        int type;
        if (this.fresh) {
            type = this.decodeInt();
            if (type < 0) {
                return null;
            }
        }

        type = this.decodeInt();
        Object du;
        switch (type) {
            case 0:
                du = new ADU(this);
                break;
            case 1:
                du = new CDU(this);
                break;
            default:
                du = null;
        }

        this.fresh = wasfresh;
        return (DU) du;
    }
}
