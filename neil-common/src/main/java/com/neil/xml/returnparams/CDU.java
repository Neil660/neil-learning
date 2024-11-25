package com.neil.xml.returnparams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes;

/*
 * @Classname CDU
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class CDU extends DU {
    private static String fileID = "";
    private ArrayList f_list = new ArrayList();

    protected ArrayList List() {
        return this.f_list;
    }

    public int DUType() {
        return 1;
    }

    public int EncodedLength() {
        int len = 3 * this.intSize();

        for(int i = 0; i < this.List().size(); ++i) {
            DU du = (DU)this.List().get(i);
            len += this.intSize() + du.EncodedLength();
        }

        return len;
    }

    public CDU(DUInputStream s) throws IOException {
        this.DULabel(s.decodeInt());
        int size = s.decodeInt();

        int i;
        for(i = 0; i < size; ++i) {
            int var4 = s.decodeInt();
        }

        for(i = 0; i < size; ++i) {
            DU du = s.decode();
            if (du == null) {
                return;
            }

            this.List().add(du);
        }

    }

    public CDU() {
    }

    public CDU(int aLabel) {
        super(aLabel);
    }

    public void add(DU du) {
        this.List().add(du);
    }

    public DU get(int i) {
        return (DU)this.List().get(i);
    }

    public ADU getADU(int i) {
        return (ADU)this.List().get(i);
    }

    public CDU getCDU(int i) {
        return (CDU)this.List().get(i);
    }

    public int size() {
        return this.List().size();
    }

    public String data(int i) {
        ADU adu = this.getADU(i);
        return adu != null ? adu.Data() : null;
    }

    public int intData(int i) {
        ADU adu = this.getADU(i);
        return adu != null ? adu.intData() : -1;
    }

    public void addADU(String s) {
        ADU adu = new ADU(s);
        this.add(adu);
    }

    public void addADU(int i) {
        ADU adu = new ADU(i);
        this.add(adu);
    }

    public void addADUTuple(String s1, String s2) {
        CDU cdu = new CDU();
        cdu.add(new ADU(s1));
        cdu.add(new ADU(s2));
        this.add(cdu);
    }

    protected void encodeDU(DUOutputStream s) throws IOException {
        try {
            s.encodeInt(1);
            s.encodeInt(this.DULabel());
            s.encodeInt(this.List().size());

            int i;
            DU du;
            for(i = 0; i < this.List().size(); ++i) {
                du = (DU)this.List().get(i);
                s.encodeInt(du.EncodedLength());
            }

            for(i = 0; i < this.List().size(); ++i) {
                du = (DU)this.List().get(i);
                du.encodeDU(s);
            }

        } catch (IOException var4) {
            throw var4;
        }
    }

    public Attributes toAttributes(int offset) {
        Attributes attr = new Attributes();

        for(int i = offset; i < this.size(); ++i) {
            String name = this.getCDU(i).data(0);
            String value = this.getCDU(i).data(1);
            if (name.startsWith("@")) {
                name = name.substring(1);
            }

            attr.putValue(name, value);
        }

        return attr;
    }

    public void addAttributes(Attributes attr) {
        Object[] names = attr.keySet().toArray();
        Object[] vals = attr.values().toArray();

        for(int i = 0; i < names.length; ++i) {
            String name = "@";
            name.concat(names[i].toString());
            this.addADUTuple(name, vals[i].toString());
        }

    }

    public String toString() {
        String desc = "{CDU: ";
        DU du = null;

        for(int i = 0; i < this.List().size(); ++i) {
            du = (DU)this.List().get(i);
            desc = desc + du.toString() + "\n";
        }

        desc = desc + "}";
        return desc;
    }
}
